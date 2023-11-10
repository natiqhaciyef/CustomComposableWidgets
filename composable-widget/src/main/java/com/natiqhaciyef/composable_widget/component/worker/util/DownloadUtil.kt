package com.natiqhaciyef.composable_widget.component.worker.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.natiqhaciyef.composable_widget.component.common.util.classes.MaterialModel
import com.natiqhaciyef.composable_widget.component.worker.FileDownloadWorker
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.UUID

fun getSavedFileUri(
    fileName: String,
    fileType: String,
    fileUrl: String,
    context: Context
): Uri? {
    val mimeType = when (fileType) {
        "PDF" -> "application/pdf"
        "DOCX" -> "application/docx"
        "PNG" -> "image/png"
        "MP4" -> "video/mp4"
        else -> "application/docx"
    } // different types of files will have different mime type

    if (mimeType.isEmpty()) return null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Download")
        }

        val resolver = context.contentResolver

        val uri = resolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            contentValues
        )

        return if (uri != null) {
            URL(fileUrl).openStream().use { input ->
                resolver.openOutputStream(uri).use { output ->
                    input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                }
            }
            uri
        } else {
            null
        }

    } else {

        val target = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        URL(fileUrl).openStream().use { input ->
            FileOutputStream(target).use { output ->
                input.copyTo(output)
            }
        }

        return target.toUri()
    }
}


fun startDownloadingFile(
    file: MaterialModel,
    success: (String) -> Unit,
    failed: (String) -> Unit,
    running: () -> Unit,
    context: Context
) {
    val data = Data.Builder()
    val workManager = WorkManager.getInstance(context)

    data.apply {
        putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, "${file.title} (${UUID.randomUUID()})")
        putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.url)
        putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, file.type)
    }


    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
//        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    workManager.enqueueUniqueWork(
        "oneFileDownloadWork_${System.currentTimeMillis()}",
        ExistingWorkPolicy.KEEP,
        fileDownloadWorker
    )


    workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
        .observeForever { info ->
            info?.let {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        success(
                            it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI)
                                ?: ""
                        )
                    }

                    WorkInfo.State.FAILED -> {
                        failed("Downloading failed!")
                    }

                    WorkInfo.State.RUNNING -> {
                        running()
                    }

                    else -> {
                        failed("Something went wrong")
                    }
                }
            }
        }
}

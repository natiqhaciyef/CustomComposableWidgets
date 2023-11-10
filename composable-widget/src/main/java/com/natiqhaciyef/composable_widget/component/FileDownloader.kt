package com.natiqhaciyef.composable_widget.component

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberImagePainter
import com.natiqhaciyef.composable_widget.component.common.util.classes.MaterialModel
import com.natiqhaciyef.composable_widget.component.worker.util.startDownloadingFile


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun ItemFile(
    file: MaterialModel,
    borderColor: Color,
    startDownload: (MaterialModel) -> Unit,
    openFile: (MaterialModel) -> Unit,
) {

    val permissionGrantBooleanForFile = remember { mutableStateOf(true) }

    val permissionLauncherForFile = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { mapBoolean ->
        var isGranted = false
        mapBoolean.forEach { (s, b) ->
            isGranted = b
        }
        permissionGrantBooleanForFile.value = isGranted
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .border(
                width = 1.5.dp, color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(color = Color.White)
            .clickable {
                permissionLauncherForFile.launch(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                )
                if (permissionGrantBooleanForFile.value) {
                    if (!file.isDownloading) {
                        if (file.downloadedUri.isNullOrEmpty()) {
                            startDownload(file)
                        } else {
                            openFile(file)
                        }
                    }
                }

            }
            .padding(16.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .padding(end = 15.dp, start = 5.dp, top = 5.dp, bottom = 5.dp)
                    .size(45.dp),
                painter = rememberImagePainter(file.image),
                contentDescription = "File type image"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            ) {
                Text(
                    text = file.title,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Row {
                    val description = if (file.isDownloading) {
                        "Downloading..."
                    } else {
                        if (file.downloadedUri.isNullOrEmpty()) "Tap to download the file" else "Tap to open file"
                    }
                    Text(
                        text = description,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }

            }

            if (file.isDownloading) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ItemFileBox(
    material: MutableState<MaterialModel>,
    borderColor: Color,
    returnMessage: MutableState<String> = mutableStateOf(""),
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        ItemFile(
            file = material.value,
            startDownload = {
                startDownloadingFile(
                    file = material.value,
                    success = {
                        material.value = material.value.copy().apply {
                            isDownloading = false
                            downloadedUri = it
                            println(it)
                        }

                        returnMessage.value = "File has been downloaded!"
                    },
                    failed = {
                        material.value = material.value.copy().apply {
                            isDownloading = false
                            downloadedUri = null
                            println(it)

                        }

                        returnMessage.value = "Something went wrong!"
                    },
                    running = {
                        material.value = material.value.copy().apply {
                            isDownloading = true
                        }
                        println(it)

                    },
                    context = context,
                )
            },
            openFile = {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(
                    it.downloadedUri?.toUri(),
                    "application/${material.value.type}"
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                try {
                    context.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    println(e.message)
                    println(e.cause)

                    // Snackbar
                    Toast.makeText(
                        context,
                        "Can't open file",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            borderColor = borderColor
        )
    }
}
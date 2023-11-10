package com.natiqhaciyef.composable_widget.component

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.lightspark.composeqr.DotShape
import com.lightspark.composeqr.QrCodeColors
import com.lightspark.composeqr.QrCodeView
import com.lightspark.composeqr.QrEncoder
import com.lightspark.composeqr.ZxingQrEncoder
import java.util.UUID


@Composable
fun ImagePickerTitle(
    modifier: Modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    modifierTitle: Modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    concept: String,
    image: MutableState<Uri?>,
    secondaryImageId: Int,
) {
    val context = LocalContext.current

    val isPermissionGranted = remember { mutableStateOf(false) }
    val permissionGrantBoolean = remember { mutableStateOf(true) }
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val intentFromResult = result.data
            if (intentFromResult != null) {
                image.value = intentFromResult.data
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent)
            permissionGrantBoolean.value = true
        } else {
            // Permission Denied: Do something
            permissionGrantBoolean.value = false
        }
    }

    Text(
        modifier = modifierTitle,
        text = concept,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.height(10.dp))
    ImagePicker(
        image = image,
        context = context,
        permissionLauncher = permissionLauncher,
        activityResultLauncher = activityResultLauncher,
        isPermissionGranted = isPermissionGranted,
        imageId = secondaryImageId
    )
}


@Composable
fun ImagePicker(
    size: Dp = 200.dp,
    image: MutableState<Uri?>,
    context: Context,
    imageId: Int,
    permissionLauncher: ActivityResultLauncher<String>,
    activityResultLauncher: ActivityResultLauncher<Intent>,
    isPermissionGranted: MutableState<Boolean>,
) {
    Image(
        painter = if (image.value != null) rememberImagePainter(image.value)
        else painterResource(id = imageId),
        contentDescription = "Image",
        modifier = Modifier
            .size(size)
            .clickable {
                imageSelector(
                    context = context,
                    permissionLauncher = permissionLauncher,
                    activityResultLauncher = activityResultLauncher,
                    isPermissionGranted = isPermissionGranted
                )
            },
        contentScale = if (image.value != null) ContentScale.Crop
        else ContentScale.Fit
    )
}

@Preview
@Composable
fun QRCodeGenerator(
    id: String = "${UUID.randomUUID()}",
    modifier: Modifier = Modifier.size(300.dp),
    backgroundColor: Color = Color.White,
    foregroundColor: Color = Color.Black,
    shape: DotShape = DotShape.Square
) {
    QrCodeView(
        data = id,
        modifier = modifier,
        colors = QrCodeColors(
            background = backgroundColor,
            foreground = foregroundColor
        ),
        dotShape = shape,
    )
}


fun imageSelector(
    context: Context,
    permissionLauncher: ActivityResultLauncher<String>,
    activityResultLauncher: ActivityResultLauncher<Intent>,
    isPermissionGranted: MutableState<Boolean>,
) {

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        isPermissionGranted.value = true
    } else {
        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    if (isPermissionGranted.value) {
        val intentToGallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncher.launch(intentToGallery)
    }
}
package com.natiqhaciyef.composable_widget.component.common.util.classes

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialModel(
    var id: String = "0",
    var image: String = "",
    var title: String,
    var type: String,
    var field: String,
    var subfield: List<String>,
    var url: String,
    var downloadedUri: String? = null,
    var isDownloading: Boolean = false,
) : Parcelable
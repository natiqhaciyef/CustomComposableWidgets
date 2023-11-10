package com.natiqhaciyef.composable_widget.component.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.natiqhaciyef.composable_widget.R

object Fonts {

    val opensans = FontFamily(
        Font(R.font.opensans_extra_bold),
        Font(R.font.opensans_bold),
        Font(R.font.opensans_bold_italic),
        Font(R.font.opensans_semibold),
        Font(R.font.opensans_semibold_italic),
        Font(R.font.opensans_medium),
        Font(R.font.opensans_medium_italic),
        Font(R.font.opensans_regular),
        Font(R.font.opensans_italic),
        Font(R.font.opensans_light),
        Font(R.font.opensans_light_italic),

        )

    val lobster = FontFamily(
        listOf(
            Font(R.font.lobster_regular)
        )
    )

    val sarabun = FontFamily(
        listOf(
            Font(R.font.sarabun_regular),
        )
    )

    val youngSerif = FontFamily(
        listOf(
            Font(R.font.youngserif_regular)
        )
    )
}
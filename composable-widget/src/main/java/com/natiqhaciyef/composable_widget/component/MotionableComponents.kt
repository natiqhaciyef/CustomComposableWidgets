package com.natiqhaciyef.composable_widget.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.TextField
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.utils.MiscUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@Composable
fun CustomDropDownTitleBox(
    selectedOption: MutableState<String>,
    title: String,
    nonSelectedOption: String = "Options",
    list: List<String>,
    textColor: Color,
    borderColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier
        .padding(start = 20.dp)
        .fillMaxWidth(),
    modifierTitle: Modifier = Modifier
        .padding(start = 20.dp)
        .fillMaxWidth(),
    fontSize: Int = 20,
) {
    Text(
        modifier = modifierTitle,
        text = title,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
    )
    Spacer(modifier = Modifier.height(10.dp))
    CustomDropDownMenu(
        title = nonSelectedOption,
        modifier = modifier,
        list = list,
        selectedOption = selectedOption,
        isEnabled = true,
        textColor = textColor,
        borderColor = borderColor,
        iconColor = iconColor,
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomDropDownMenu(
    title: String,
    modifier: Modifier,
    list: List<String>,
    selectedOption: MutableState<String>,
    isEnabled: Boolean = true,
    cornerRadius: Dp = 10.dp,
    borderSize: Dp = 1.dp,
    textColor: Color,
    borderColor: Color,
    iconColor: Color,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier
            .border(
                width = borderSize,
                color = borderColor,
                shape = RoundedCornerShape(cornerRadius)
            ),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(cornerRadius)),
            value = selectedOption.value,
            onValueChange = { },
            textStyle = TextStyle.Default.copy(
                color = textColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            readOnly = true,
            label = {
                Text(
                    text = title,
                    color = iconColor,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = textColor,
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },

            ) {
            list.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption.value = option
                        expanded = false
                    },
                    enabled = isEnabled
                ) {
                    Text(
                        text = option,
                        color = textColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@SuppressLint("RestrictedApi")
@ExperimentalPagerApi
@Composable
fun <T> CustomHorizontalViewPager(
    list: MutableList<T>,
    widgetStructureComponents: @Composable () -> Unit = {},
) {
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(key1 = Unit) {
        while (true) {
            Thread.yield()
            delay(4500)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier,
        count = list.size
    ) { page ->
        Card(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    MiscUtils
                        .lerp(
                            0.85f,
                            1f,
                            1f - pageOffset.coerceIn(0f, 1f)
                        )
                        .also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    alpha = MiscUtils.lerp(
                        0.5f,
                        1f,
                        1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            ViewPagerItemInside(widgetStructureComponents)
        }
    }
}

@Composable
fun ViewPagerItemInside(
    additionalWidgets: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center,
    ) {
        additionalWidgets()
    }
}


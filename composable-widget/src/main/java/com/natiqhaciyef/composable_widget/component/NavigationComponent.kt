package com.natiqhaciyef.composable_widget.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natiqhaciyef.composable_widget.component.common.util.classes.NavItemModel

@Composable
fun NavBar(
    selectedIndex: MutableState<Int>,
    list: MutableList<NavItemModel> = mutableListOf(
        NavItemModel(Icons.Default.Home, title = "Home"),
        NavItemModel(Icons.Default.AccountBalance, title = "Learn"),
        NavItemModel(Icons.Default.AccountCircle, title = "Profile"),
    ),
    navBarBackgroundColor: Color,
    selectedWidgetColor: Color,
    iconColor: Color,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(navBarBackgroundColor, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            list.forEachIndexed { index, icon ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(11f)
                        .clickable { selectedIndex.value = index },
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = if (selectedIndex.value == index)
                            Modifier.offset(y = (-8).dp)
                        else
                            Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    if (selectedIndex.value == index) selectedWidgetColor
                                    else Color.Transparent,
                                    shape = CircleShape
                                )
                                .size(45.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon.icon,
                                contentDescription = "content",
                                modifier = Modifier.size(25.dp),
                                tint = if (selectedIndex.value == index) Color.White else iconColor
                            )
                        }
                        AnimatedVisibility(visible = (selectedIndex.value == index)) {
                            Text(
                                text = icon.title,
                                modifier = Modifier,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
package com.natiqhaciyef.composable_widget.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natiqhaciyef.composable_widget.component.common.util.helpers.cardTypeToImageFinder
import com.natiqhaciyef.composable_widget.component.common.util.helpers.formatExpirationDate
import com.natiqhaciyef.composable_widget.component.common.util.helpers.formatOtherCardNumbers


@Composable
fun InputBoxTitle(
    modifier: Modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    modifierTitle: Modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    concept: String,
    input: MutableState<String>,
    isSingleLine: Boolean,
    type: KeyboardType = KeyboardType.Text,
    prefix: String = "",
    icon: ImageVector? = null,
    iconColor: Color,
    borderColor: Color,
    isBottomShadowActive: Boolean = true,
    isEnabled: Boolean = false,
    onClick: (String) -> Unit = { },
) {
    val focusManager = LocalFocusManager.current

    Text(
        modifier = modifierTitle,
        text = concept,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.height(10.dp))

    if (prefix.isNotEmpty()) {
        OutlinedTextField(
            modifier = modifier,
            value = input.value,
            onValueChange = {
                input.value = it
            },
            leadingIcon = {
                if (icon == null)
                    Icon(
                        modifier = Modifier.clickable { onClick(input.value) },
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = iconColor
                    )
                else
                    Icon(
                        modifier = Modifier.clickable { onClick(input.value) },
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = iconColor
                    )
            },
            shape = RoundedCornerShape(8.dp),
            enabled = isEnabled,
            singleLine = isSingleLine,
            readOnly = false,
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = type,
                imeAction = ImeAction.Next
            ),
            prefix = {
                if (prefix.isNotEmpty()) {
                    Text(
                        text = prefix,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                } else {
                    Text(
                        text = "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = borderColor,
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            placeholder = {
                Text(
                    text = concept,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        )
    } else {
        OutlinedTextField(
            modifier = modifier,
            value = input.value,
            onValueChange = {
                input.value = it
            },
            leadingIcon = {
                if (icon == null)
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = iconColor
                    )
                else
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = iconColor
                    )
            },
            shape = RoundedCornerShape(8.dp),
            enabled = isEnabled,
            singleLine = isSingleLine,
            readOnly = false,
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = type,
                imeAction = ImeAction.Next
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = borderColor,
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            placeholder = {
                Text(
                    text = concept,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        )
    }
    if (isBottomShadowActive)
        BottomShadow(padding = 23.dp)
}


@Composable
fun InputBoxTitlePassword(
    modifier: Modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    modifierTitle: Modifier = Modifier
        .padding(start = 20.dp)
        .fillMaxWidth(),
    concept: String,
    input: MutableState<String>,
    passVisibility: MutableState<Boolean>,
    isSingleLine: Boolean,
    textColor: Color,
    borderColor: Color,
    iconColor: Color,
    isBottomShadowActive: Boolean = true,
) {
    Text(
        modifier = modifierTitle,
        text = concept,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
    )
    Spacer(modifier = Modifier.height(10.dp))

    InputBoxPassword(
        concept = concept,
        input = input,
        passVisibility = passVisibility,
        isSingleLine = isSingleLine,
        isBottomShadowActive = isBottomShadowActive,
        textColor = textColor,
        borderColor = borderColor,
        iconColor = iconColor,
    )
}


@Composable
fun InputTitleForCardNumberBox(
    concept: String,
    input: MutableState<String>,
    fontSize: Int = 16,
    paymentMethod: MutableState<String>,
    textColor: Color,
    borderColor: Color,
    iconColor: Color,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        value = input.value,
        onValueChange = {
            if (input.value.length <= 16)
                input.value = it
        },
        enabled = true,
        singleLine = true,
        readOnly = false,
        shape = RoundedCornerShape(8.dp),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        textStyle = TextStyle.Default.copy(
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = textColor,
            focusedBorderColor = borderColor,
            cursorColor = textColor,
            unfocusedTextColor = textColor,
            focusedTextColor = textColor,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedLabelColor = iconColor,
            unfocusedLabelColor = iconColor,
        ),
        label = {
            Text(
                text = concept,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        },
        trailingIcon = {
            Image(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(35.dp),
                painter = painterResource(id = cardTypeToImageFinder(paymentMethod.value)),
                contentDescription = "Card type"
            )
        },
        visualTransformation = { number ->
            formatOtherCardNumbers(input.value)
        }
    )
}


@Composable
fun InputForCardDateAndCVVBox(
    expireDate: MutableState<String>,
    cvvCode: MutableState<String>,
    textColor: Color,
    borderColor: Color,
    iconColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .width(160.dp)
                .padding(end = 15.dp),
            value = expireDate.value,
            onValueChange = {
                if (expireDate.value.length <= 4)
                    expireDate.value = it
            },
            enabled = true,
            singleLine = true,
            readOnly = false,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textColor,
                focusedBorderColor = borderColor,
                cursorColor = textColor,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedPlaceholderColor = iconColor,
                unfocusedPlaceholderColor = iconColor,
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Date"
                )
            },
            label = {
                Text(
                    modifier = Modifier,
                    text = "xx/xx",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            },
            visualTransformation = { number ->
                formatExpirationDate(expireDate.value)
            },
        )

        OutlinedTextField(
            modifier = Modifier
                .width(160.dp)
                .padding(start = 15.dp),
            value = cvvCode.value,
            onValueChange = {
                if (cvvCode.value.length < 3)
                    cvvCode.value = it
            },
            enabled = true,
            singleLine = true,
            readOnly = false,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textColor,
                focusedBorderColor = borderColor,
                cursorColor = textColor,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedPlaceholderColor = iconColor,
                unfocusedPlaceholderColor = iconColor,
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Help,
                    contentDescription = "Question"
                )
            },
            label = {
                Text(
                    modifier = Modifier,
                    text = "CVV",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
        )
    }
}


@Composable
fun InputBox(
    modifier: Modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    concept: String,
    input: MutableState<String>,
    isSingleLine: Boolean,
    prefix: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    textColor: Color,
    borderColor: Color,
    iconColor: Color,
    tag: String? = null,
    isBottomShadowActive: Boolean = true,
    onClick: () -> Unit = { },
) {
    val focusManager = LocalFocusManager.current

    if (trailingIcon == null) {
        OutlinedTextField(
            modifier = modifier,
            value = input.value,
            onValueChange = {
                input.value = it
            },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = "Email",
                        tint = iconColor
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            enabled = true,
            singleLine = isSingleLine,
            readOnly = false,
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = keyboardOptions,
            prefix = {
                if (prefix.isNotEmpty()) {
                    Text(
                        text = prefix,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                } else {
                    Text(
                        text = "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedTextColor = textColor,
                focusedTextColor = textColor,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = borderColor,
                focusedTrailingIconColor = iconColor,
                unfocusedTrailingIconColor = iconColor,
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            placeholder = {
                if (tag == null) {
                    Text(
                        text = concept,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                } else {
                    Text(
                        text = tag,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
            }
        )
    } else {
        OutlinedTextField(
            modifier = modifier,
            value = input.value,
            onValueChange = {
                input.value = it
            },
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = "Email",
                        tint = iconColor
                    )
                }
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable { onClick() },
                    imageVector = trailingIcon,
                    contentDescription = "Email",
                    tint = iconColor
                )
            },
            shape = RoundedCornerShape(8.dp),
            enabled = true,
            singleLine = isSingleLine,
            readOnly = false,
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            keyboardOptions = keyboardOptions,
            prefix = {
                if (prefix.isNotEmpty()) {
                    Text(
                        text = prefix,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                } else {
                    Text(
                        text = "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = Color.Gray,
                cursorColor = borderColor,
                focusedTrailingIconColor = iconColor,
                unfocusedTrailingIconColor = iconColor,
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            ),
            placeholder = {
                if (tag == null) {
                    Text(
                        text = concept,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                } else {
                    Text(
                        text = tag,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }
            }
        )
    }
    if (isBottomShadowActive)
        BottomShadow(padding = 23.dp)

}


@Composable
fun InputBoxPassword(
    modifier: Modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    concept: String,
    input: MutableState<String>,
    passVisibility: MutableState<Boolean>,
    isSingleLine: Boolean,
    type: KeyboardType = KeyboardType.Text,
    prefix: String = "",
    textColor: Color,
    borderColor: Color,
    iconColor: Color,
    isBottomShadowActive: Boolean = true,
    leadingIcon: ImageVector? = null,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = input.value,
        onValueChange = {
            input.value = it
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon ?: Icons.Default.Lock,
                contentDescription = "Email",
                tint = iconColor
            )
        },
        shape = RoundedCornerShape(8.dp),
        enabled = true,
        singleLine = isSingleLine,
        readOnly = false,
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = type,
            imeAction = ImeAction.Next
        ),
        prefix = {
            if (prefix.isNotEmpty()) {
                Text(
                    text = prefix,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            } else {
                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }
        },
        visualTransformation = if (passVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .padding(end = 13.dp)
                    .size(25.dp)
                    .clickable {
                        passVisibility.value = !passVisibility.value
                    },
                imageVector = if (passVisibility.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                contentDescription = "Icon",
                tint = iconColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedTextColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = Color.Gray,
            cursorColor = textColor,
        ),
        textStyle = TextStyle.Default.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        ),
        placeholder = {
            Text(
                text = concept,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        }
    )

    if (isBottomShadowActive)
        BottomShadow(padding = 23.dp)

}


@Composable
fun BottomShadow(
    alpha: Float = 0.1f, height: Dp = 8.dp,
    padding: Dp = 0.dp,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = padding)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = alpha),
                        Color.Transparent,
                    )
                )
            )
    )
}


@Composable
fun TitleComponent(
    titleID: Int,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Text(
            text = stringResource(id = titleID),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterStart),
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun SubComponent(
    navigationId: () -> Unit = {},
    icon: ImageVector,
    iconSize: Dp,
    padding: Dp = 0.dp,
    textId: Int,
    iconColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable {
                navigationId()
            }
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 25.dp)
                .height(50.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Component",
                modifier = Modifier
                    .padding(start = padding)
                    .size(iconSize),
                tint = iconColor
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = stringResource(id = textId),
                fontSize = 17.sp,
                color = iconColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun SubComponent(
    navigationId: () -> Unit = {},
    iconId: Int,
    padding: Dp = 0.dp,
    iconSize: Dp,
    textId: Int,
    iconColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable {
                navigationId()
            }
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 25.dp)
                .height(50.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Component",
                modifier = Modifier
                    .padding(start = padding)
                    .size(iconSize),
                tint = iconColor
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = stringResource(id = textId),
                fontSize = 17.sp,
                color = iconColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
        }
    }
}

val colorMatrixGrayLayer = floatArrayOf(
    0.65f, 0f, 0f, 0f, 0f,
    0f, 0.65f, 0f, 0f, 0f,
    0f, 0f, 0.65f, 0f, 0f,
    0f, 0f, 0f, 1f, 0f
)

val colorMatrixLightGrayLayer = floatArrayOf(
    0.7f, 0f, 0f, 0f, 0f,
    0f, 0.7f, 0f, 0f, 0f,
    0f, 0f, 0.7f, 0f, 0f,
    0f, 0f, 0f, 1f, 0f
)

val colorMatrixDarkGrayLayer = floatArrayOf(
    0.5f, 0f, 0f, 0f, 0f,
    0f, 0.5f, 0f, 0f, 0f,
    0f, 0f, 0.5f, 0f, 0f,
    0f, 0f, 0f, 1f, 0f
)

val colorMatrixDarkLayer = floatArrayOf(
    0.35f, 0f, 0f, 0f, 0f,
    0f, 0.35f, 0f, 0f, 0f,
    0f, 0f, 0.35f, 0f, 0f,
    0f, 0f, 0f, 1f, 0f
)
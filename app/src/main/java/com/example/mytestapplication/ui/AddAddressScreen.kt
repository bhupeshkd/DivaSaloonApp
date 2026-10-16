package com.example.mytestapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytestapplication.data.api.dto.CreateAddressRequest
import com.example.mytestapplication.ui.theme.LocationAccent
import com.example.mytestapplication.ui.theme.LocationAccentSoft
import com.example.mytestapplication.ui.theme.LocationBody
import com.example.mytestapplication.ui.theme.LocationConfirm
import com.example.mytestapplication.ui.theme.LocationLabel
import com.example.mytestapplication.ui.theme.LocationSearchBg
import com.example.mytestapplication.ui.theme.LocationTitle
import com.example.mytestapplication.ui.theme.MyTestApplicationTheme
import com.example.mytestapplication.ui.theme.OtpWhite

private val labelOptions = listOf("Home", "Work", "Other")

@Composable
fun AddAddressScreen(
    modifier: Modifier = Modifier,
    isSaving: Boolean = false,
    makeDefaultByDefault: Boolean = false,
    onBackClick: () -> Unit = {},
    onSaveClick: (CreateAddressRequest) -> Unit = {},
) {
    var selectedLabel by remember { mutableStateOf(labelOptions.first()) }
    var customLabel by remember { mutableStateOf("") }
    var line1 by remember { mutableStateOf("") }
    var line2 by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var isDefault by remember { mutableStateOf(makeDefaultByDefault) }
    var formError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F4F2))
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(OtpWhite)
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(enabled = !isSaving, onClick = onBackClick)
            ) {
                Canvas(modifier = Modifier.size(18.dp)) {
                    val stroke = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                    )
                    val path = Path().apply {
                        moveTo(size.width * 0.58f, size.height * 0.18f)
                        lineTo(size.width * 0.28f, size.height * 0.5f)
                        lineTo(size.width * 0.58f, size.height * 0.82f)
                    }
                    drawPath(path, color = LocationTitle, style = stroke)
                }
            }
            Text(
                text = "Add Address",
                color = LocationTitle,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(40.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "LABEL",
                color = LocationLabel,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                labelOptions.forEachIndexed { index, option ->
                    val selected = selectedLabel == option
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) LocationAccentSoft else OtpWhite)
                            .border(
                                width = 1.dp,
                                color = if (selected) LocationAccent else Color(0xFFE6E2DC),
                                shape = RoundedCornerShape(12.dp),
                            )
                            .clickable(enabled = !isSaving) { selectedLabel = option }
                    ) {
                        Text(
                            text = option,
                            color = if (selected) LocationAccent else LocationBody,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    if (index < labelOptions.lastIndex) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            if (selectedLabel == "Other") {
                Spacer(modifier = Modifier.height(12.dp))
                AddressField(
                    label = "Custom label",
                    value = customLabel,
                    onValueChange = { customLabel = it },
                    placeholder = "e.g. Parents house",
                    enabled = !isSaving,
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            AddressField(
                label = "House / Street *",
                value = line1,
                onValueChange = { line1 = it },
                placeholder = "Flat, street, landmark",
                enabled = !isSaving,
                capitalization = KeyboardCapitalization.Sentences,
            )
            Spacer(modifier = Modifier.height(14.dp))
            AddressField(
                label = "Area / Locality",
                value = line2,
                onValueChange = { line2 = it },
                placeholder = "Optional",
                enabled = !isSaving,
                capitalization = KeyboardCapitalization.Sentences,
            )
            Spacer(modifier = Modifier.height(14.dp))
            AddressField(
                label = "City *",
                value = city,
                onValueChange = { city = it },
                placeholder = "City",
                enabled = !isSaving,
                capitalization = KeyboardCapitalization.Words,
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                AddressField(
                    label = "State",
                    value = state,
                    onValueChange = { state = it },
                    placeholder = "State",
                    enabled = !isSaving,
                    capitalization = KeyboardCapitalization.Words,
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(12.dp))
                AddressField(
                    label = "PIN code",
                    value = postalCode,
                    onValueChange = { postalCode = it.filter { ch -> ch.isDigit() }.take(10) },
                    placeholder = "PIN",
                    enabled = !isSaving,
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(OtpWhite)
                    .clickable(enabled = !isSaving) { isDefault = !isDefault }
                    .padding(horizontal = 14.dp, vertical = 14.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isDefault) LocationAccent else LocationSearchBg)
                        .border(
                            width = 1.dp,
                            color = if (isDefault) LocationAccent else Color(0xFFE6E2DC),
                            shape = RoundedCornerShape(6.dp),
                        )
                ) {
                    if (isDefault) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            val stroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                            val path = Path().apply {
                                moveTo(size.width * 0.15f, size.height * 0.52f)
                                lineTo(size.width * 0.4f, size.height * 0.75f)
                                lineTo(size.width * 0.85f, size.height * 0.25f)
                            }
                            drawPath(path, color = OtpWhite, style = stroke)
                        }
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Set as default address",
                        color = LocationTitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Used first when booking a service",
                        color = LocationBody,
                        fontSize = 12.sp
                    )
                }
            }

            if (formError != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = formError.orEmpty(),
                    color = Color(0xFFE05A5A),
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = {
                val label = when (selectedLabel) {
                    "Other" -> customLabel.trim().ifBlank { "Other" }
                    else -> selectedLabel
                }
                when {
                    line1.isBlank() -> formError = "Enter house / street address"
                    city.isBlank() -> formError = "Enter city"
                    else -> {
                        formError = null
                        onSaveClick(
                            CreateAddressRequest(
                                label = label,
                                line1 = line1.trim(),
                                line2 = line2.trim().ifBlank { null },
                                city = city.trim(),
                                state = state.trim().ifBlank { null },
                                postalCode = postalCode.trim().ifBlank { null },
                                country = "IN",
                                isDefault = isDefault,
                            )
                        )
                    }
                }
            },
            enabled = !isSaving,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LocationConfirm,
                contentColor = OtpWhite,
                disabledContainerColor = LocationConfirm.copy(alpha = 0.6f),
                disabledContentColor = OtpWhite,
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
        ) {
            if (isSaving) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = OtpWhite,
                    strokeWidth = 2.dp,
                )
            } else {
                Text(
                    text = "Save Address",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun AddressField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
) {
    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            color = LocationLabel,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            placeholder = {
                Text(text = placeholder, color = LocationBody.copy(alpha = 0.7f), fontSize = 14.sp)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = capitalization,
            ),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = OtpWhite,
                unfocusedContainerColor = OtpWhite,
                disabledContainerColor = OtpWhite,
                focusedBorderColor = LocationAccent,
                unfocusedBorderColor = Color(0xFFE6E2DC),
                focusedTextColor = LocationTitle,
                unfocusedTextColor = LocationTitle,
                cursorColor = LocationAccent,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddAddressScreenPreview() {
    MyTestApplicationTheme(dynamicColor = false) {
        AddAddressScreen()
    }
}

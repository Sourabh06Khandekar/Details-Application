package com.example.detailsapplication.ui.detailsForm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.detailsapplication.R
import com.example.detailsapplication.ui.INPUT_FIELD
import com.example.detailsapplication.ui.convertLongToTime
import com.example.detailsapplication.ui.detailsForm.viewmodel.DetailsScreenViewModel
import com.example.detailsapplication.ui.theme.Purple80
import com.example.detailsapplication.ui.theme.UnFocusedContainerColor

@Composable
fun DetailsFormScreen(
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel(),
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { HeaderText() }
        item { InputFields(detailsScreenViewModel) }
    }
}

@Composable
fun InputFields(
    detailsScreenViewModel: DetailsScreenViewModel
) {
    val userName by detailsScreenViewModel.userName.collectAsState()
    val userAddress by detailsScreenViewModel.userAddress.collectAsState()
    val userAge by detailsScreenViewModel.userAge.collectAsState()
    val isValidForm = detailsScreenViewModel.isValidateForm()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserInput(
            detailsScreenViewModel,
            userName,
            INPUT_FIELD.NAME,
        )
        UserInput(
            detailsScreenViewModel,
            userAddress,
            INPUT_FIELD.ADDRESS
        )
        UserInput(
            detailsScreenViewModel,
            userAge.toString(),
            INPUT_FIELD.AGE
        )
        UserDoB(detailsScreenViewModel)
        Row(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedButton(
                enabled = isValidForm,
                onClick = { detailsScreenViewModel.insertUserDetails() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple80,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(
                    text = "SUBMIT",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            ElevatedButton(
                onClick = { detailsScreenViewModel.resetValues() },
                colors =  ButtonDefaults.buttonColors(Purple80)
            ) {
                Text(
                    text = "ClearForm",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun UserDoB(detailsScreenViewModel: DetailsScreenViewModel) {
    var openDatePicker by remember {
        mutableStateOf(false)
    }
    val selectedDate = convertLongToTime(detailsScreenViewModel.initialDate.value)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .border(
                width = 1.dp,
                color = Color.Unspecified,
                shape = RoundedCornerShape(50.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { openDatePicker = true }
            .background(UnFocusedContainerColor)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = selectedDate.ifEmpty { "Select Your Birth date" })
        Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "calender")
    }


    if (openDatePicker) {
        DateSelector(
            detailsScreenViewModel = detailsScreenViewModel,
            onDateSelected = { selectedDate ->
                detailsScreenViewModel.setInitialDate(selectedDate)
                openDatePicker = false
            }
        ) {
            openDatePicker = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    detailsScreenViewModel: DetailsScreenViewModel,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    var initialDate = detailsScreenViewModel.initialDate.value
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDate)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                initialDate = datePickerState.selectedDateMillis
                onDateSelected(initialDate)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }

}

@Composable
fun UserInput(
    detailsScreenViewModel: DetailsScreenViewModel,
    value: String = "",
    inputType: INPUT_FIELD
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .border(
                width = 1.dp,
                color = Color.Unspecified,
                shape = RoundedCornerShape(50.dp)
            ),
        value = value.takeIf { inputType != INPUT_FIELD.AGE || it != "0" } ?: "",
        placeholder = {
            Text(
                text = when (inputType) {
                    INPUT_FIELD.NAME -> {
                        "Username"
                    }

                    INPUT_FIELD.ADDRESS -> {
                        "Address"
                    }

                    INPUT_FIELD.AGE -> {
                        "Age"
                    }
                }
            )

        },
        keyboardOptions = KeyboardOptions(keyboardType = if (inputType == INPUT_FIELD.AGE) KeyboardType.Number else KeyboardType.Text),
        onValueChange = { inputValue ->
            when (inputType) {
                INPUT_FIELD.NAME -> {
                    detailsScreenViewModel.setUserName(inputValue)
                }

                INPUT_FIELD.ADDRESS -> {
                    detailsScreenViewModel.setUserAddress(inputValue)
                }

                INPUT_FIELD.AGE -> {
                    val filteredValue = inputValue.filter { it.isDigit() }
                    detailsScreenViewModel.setUserAge(filteredValue)
                }
            }

        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Purple80,
            unfocusedContainerColor = UnFocusedContainerColor,
            focusedBorderColor = Purple80,
            unfocusedBorderColor = UnFocusedContainerColor,
            disabledPlaceholderColor = Color.Black,
            unfocusedPlaceholderColor = Color.Black,
            focusedPlaceholderColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
    Spacer(modifier = Modifier.padding(16.dp))
}

@Composable
fun HeaderText() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(top = 30.dp),
            painter = painterResource(
                id = R.drawable.book_open_reader
            ),
            contentDescription = "reader icon",
            tint = Purple80
        )
        Column {
            Text(
                text = "Tell Us",
                modifier = Modifier,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "About You",
                modifier = Modifier.padding(start = 24.dp),
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )

        }
    }
}
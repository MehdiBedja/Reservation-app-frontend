import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.reservation_app_frontend.data.parking.Parking
import com.example.reservation_app_frontend.data.parking.ParkingPlace
import com.example.reservation_app_frontend.data.reservation.ReservationDTO
import com.example.reservation_app_frontend.network.Globals.savedUsername
import com.example.reservation_app_frontend.viewModel.reservation.AddReservationViewModel
import com.example.reservation_app_frontend.viewModel.reservation.getMyReservationsViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun AddReservationScreen(
    getAllReservationsViewModel: getAllReservationModel,
    addReservationViewModel: AddReservationViewModel ,
    context: Context ,
    reservationviewModel : getMyReservationsViewModel,
    navController: NavHostController
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false) // Initially, the context menu is hidden
    }

    // Collect the state for parkings and parking places
    val parkingsState by getAllReservationsViewModel.parkings.collectAsState()
    val parkingPlacesState by getAllReservationsViewModel.parkingPlaces.collectAsState()

    // Remember the selected items
    var selectedParking by remember { mutableStateOf<Parking?>(null) }
    var selectedParkingPlace by remember { mutableStateOf<ParkingPlace?>(null) }

    // State to control showing parkings list and parking places list
    var showParkingsList by remember { mutableStateOf(false) }
    var showParkingPlacesList by remember { mutableStateOf(false) }


    var paymentStatus by rememberSaveable { mutableStateOf("pending") }


    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    fun generateRandomCode(length: Int): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }


    var reservationCode by rememberSaveable { mutableStateOf(generateRandomCode(5)) }

    // Reset selected parking place when selected parking changes
    LaunchedEffect(selectedParking) {
        selectedParkingPlace = null
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .padding(horizontal = 16.dp , vertical = 50.dp),

    horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Reservation",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        // First row: Button to show parkings list and selected parking
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.1F))
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showParkingsList = true },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(text = "Show Parkings List")
            }

            if (selectedParking != null) {
                Text(
                    text = "Selected Parking: ${selectedParking?.name}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Dropdown menu for Parking
        if (showParkingsList && parkingsState.isNotEmpty()) {
            DropdownMenu(
                expanded = showParkingsList,
                onDismissRequest = {
                    showParkingsList = false
                    selectedParking = null
                }
            ) {
                parkingsState.forEach { parking ->
                    DropdownMenuItem(
                        text = { Text(parking.name) },
                        onClick = {
                            selectedParking = parking
                            showParkingsList = false
                            getAllReservationsViewModel.fetchParkingPlaces(parking.id)
                            showParkingPlacesList = true // Show parking places list
                        }
                    )
                }
            }
        }

        // Second row: Button to choose parking place and selected parking place
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(alpha = 0.1F))
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedParking != null) {
                Button(
                    onClick = {
                        // Fetch parking places for the selected parking
                        selectedParking?.let { parking ->
                            getAllReservationsViewModel.fetchParkingPlaces(parking.id)
                        }
                        showParkingPlacesList = true // Show parking places list
                    },
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text(text = "Choose Parking Place")
                }
            }

            if (selectedParkingPlace != null) {
                Text(
                    text = "Selected Parking: ${selectedParkingPlace?.attributes}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Display parking places for the selected parking
        if (showParkingPlacesList && parkingPlacesState.isNotEmpty()) {
            DropdownMenu(
                expanded = showParkingPlacesList,
                onDismissRequest = {
                    showParkingPlacesList = false
                    selectedParkingPlace = null
                },
                offset = DpOffset(0.dp, 48.dp) // Offset to position the menu below the button
            ) {
                parkingPlacesState.forEach { parkingPlace ->
                    DropdownMenuItem(
                        text = { Text(parkingPlace.attributes) },
                        onClick = {
                            selectedParkingPlace = parkingPlace
                            showParkingPlacesList = false
                            isContextMenuVisible = false
                            Log.d(
                                "AddReservationScreen",
                                "Selected Parking Place: ${parkingPlace.attributes}, ID: ${parkingPlace.id}"
                            )
                        }
                    )
                }
            }
        }



        // Make Reservation button


    }

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            dateDialogState.show()
        }) {
            Text(text = "Pick date")
        }
        Text(text = formattedDate)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            timeDialogState.show()
        }) {
            Text(text = "Pick time")
        }
        Text(text = formattedTime)
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(
                    context,
                    "Clicked ok",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",

        ) {
            pickedDate = it
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(
                    context,
                    "Clicked ok",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Pick a time",
            timeRange = LocalTime.MIDNIGHT..LocalTime.NOON
        ) {
            pickedTime = it
        }
    }


    val userId = savedUsername
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp , vertical = 200.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                val reservationDTO = ReservationDTO(
                    user = userId, // Assuming you have a userId variable
                    parking_place = selectedParkingPlace?.id ?: -1, // Default to -1 if selectedParkingPlace is null
                    entry_datetime = formattedDate, // Example valid datetime format
                    exit_datetime = formattedTime, // Example valid datetime format
                    payment_status = paymentStatus,
                    reservation_code = reservationCode
                )
                addReservationViewModel.addReservation(reservationDTO)
                reservationviewModel.fetchReservations()
                      },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .sizeIn(minHeight = 36.dp, maxHeight = 48.dp) // Adjust the size as needed
        ) {
            Text(text = "Make Reservation", fontSize = 14.sp) // Adjust fontSize as needed
        }
    }



}





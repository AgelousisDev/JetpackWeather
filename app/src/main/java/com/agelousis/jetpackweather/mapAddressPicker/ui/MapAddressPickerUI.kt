package com.agelousis.jetpackweather.mapAddressPicker.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.viewModel.MapViewModel
import com.agelousis.jetpackweather.ui.composableView.WeatherSmallTopAppBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapAddressPickerView(
    viewModel: MapViewModel
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        topBar = {
            WeatherSmallTopAppBar(
                title = stringResource(id = R.string.app_name),
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationIconBlock = {
                    (context as Activity).finish()
                },
                actions = {
                    IconButton(
                        onClick = {
                            (context as Activity).setResult(Activity.RESULT_OK, Intent())
                            context.finish()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val addressDataModel by viewModel.addressDataModelStateFlow.collectAsState()
        val currentLatLng = LatLng(
            addressDataModel?.latitude ?: return@Scaffold,
            addressDataModel?.longitude ?: return@Scaffold
        )
        val cameraPositionState = rememberCameraPositionState()
        cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLatLng, 10f)
        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
        ) {
            OutlinedTextField(
                value = viewModel.addressLine ?: "",
                onValueChange = {
                    viewModel.addressLine = it
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier
                            .weight(
                                weight = 0.1f
                            )
                            .padding(
                                end = 16.dp
                            ),
                        onClick = {
                            keyboardController?.hide()
                            if (viewModel.addressLine != null)
                                viewModel.onTextChanged(
                                    context = context,
                                    text = viewModel.addressLine ?: return@IconButton
                                )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        if (viewModel.addressLine != null)
                            viewModel.onTextChanged(
                                context = context,
                                text = viewModel.addressLine ?: return@KeyboardActions
                            )
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    )
            )
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 16.dp
                    ),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(
                        position = currentLatLng
                    ),
                    title = addressDataModel?.countryName ?: "",
                    snippet = addressDataModel?.addressLine ?: ""
                )
            }
        }
    }
}

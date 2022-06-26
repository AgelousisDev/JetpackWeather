package com.agelousis.jetpackweather.mapAddressPicker.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.MapAddressPickerActivity
import com.agelousis.jetpackweather.mapAddressPicker.viewModel.MapViewModel
import com.agelousis.jetpackweather.ui.composableView.WeatherSmallTopAppBar
import com.agelousis.jetpackweather.ui.theme.Typography
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapAddressPickerView(
    viewModel: MapViewModel
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val addressDataModel by viewModel.addressDataModelStateFlow.collectAsState()
    val focusRequester = remember {
        FocusRequester()
    }
    val currentLatLng =
        if (addressDataModel != null)
            LatLng(
                addressDataModel?.latitude ?: return,
                addressDataModel?.longitude ?: return
            )
        else
            null
    val cameraPositionState = rememberCameraPositionState()
    if (currentLatLng != null)
        cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLatLng, 10f)
    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        topBar = {
            WeatherSmallTopAppBar(
                title = stringResource(id = R.string.key_location_label),
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationIconBlock = {
                    (context as Activity).finish()
                },
                actions = {
                    IconButton(
                        onClick = {
                            (context as Activity).setResult(
                                Activity.RESULT_OK, Intent().also { intent ->
                                    intent.putExtra(
                                        MapAddressPickerActivity.CURRENT_ADDRESS,
                                        addressDataModel
                                    )
                                }
                            )
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
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
        ) {
            val (outlinedTextFieldConstrainedReference, googleMapsConstrainedReference,
                progressIndicatorConstrainedReference) = createRefs()
            OutlinedTextField(
                value = viewModel.addressLine ?: "",
                onValueChange = {
                    viewModel.addressLine = it
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.key_search_places_label),
                        style = Typography.bodyMedium
                    )
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(
                                end = 16.dp
                            ),
                        onClick = {
                            viewModel.addressLine = ""
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusRequester.freeFocus()
                        keyboardController?.hide()
                        if (viewModel.addressLine != null)
                            viewModel.onTextChanged(
                                context = context,
                                text = viewModel.addressLine ?: return@KeyboardActions
                            )
                    }
                ),
                modifier = Modifier
                    .constrainAs(outlinedTextFieldConstrainedReference) {
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .focusRequester(
                        focusRequester = focusRequester
                    )
            )
            GoogleMap(
                modifier = Modifier
                    .constrainAs(googleMapsConstrainedReference) {
                        start.linkTo(parent.start)
                        top.linkTo(outlinedTextFieldConstrainedReference.bottom, 16.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    ),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false
                )
            ) {
                if (currentLatLng != null)
                    Marker(
                        state = MarkerState(
                            position = currentLatLng
                        ),
                        title = addressDataModel?.countryName ?: "",
                        snippet = addressDataModel?.addressLine ?: ""
                    )
            }
            if (viewModel.addressIsLoading)
                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(progressIndicatorConstrainedReference) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                )
        }
    }
}

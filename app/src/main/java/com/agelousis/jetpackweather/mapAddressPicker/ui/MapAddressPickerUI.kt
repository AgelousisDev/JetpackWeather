package com.agelousis.jetpackweather.mapAddressPicker.ui

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.agelousis.jetpackweather.R
import com.agelousis.jetpackweather.mapAddressPicker.viewModel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapAddressPickerView(
    viewModel: MapViewModel
) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        val mapView = rememberMapViewWithLifecycle()
        val currentLocation = viewModel.location.collectAsState()
        var text by remember { viewModel.addressText }
        val context = LocalContext.current

        Column(Modifier.fillMaxWidth()) {

            Box{
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        if(!viewModel.isMapEditable.value)
                            viewModel.onTextChanged(context, text)
                    },
                    modifier = Modifier.fillMaxWidth().padding(end = 80.dp),
                    enabled = !viewModel.isMapEditable.value,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)
                )

                Column(
                    modifier = Modifier.fillMaxWidth().padding(10.dp).padding(bottom = 20.dp),
                    horizontalAlignment = Alignment.End
                ){
                    Button(
                        onClick = {
                            viewModel.isMapEditable.value = !viewModel.isMapEditable.value
                        }
                    ) {
                        Text(text = if(viewModel.isMapEditable.value) "Edit" else "Save")
                    }
                }
            }

            Box(modifier = Modifier.height(500.dp)){

                currentLocation.value.let {
                    if(viewModel.isMapEditable.value) {
                        text = viewModel.getAddressFromLocation(context)
                    }
                    MapViewContainer(viewModel.isMapEditable.value, mapView, viewModel)
                }

                MapPinOverlay()
            }
        }
    }
}

@Composable
fun MapPinOverlay(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier.size(50.dp),
                bitmap = ImageBitmap.imageResource(id = R.drawable.pin).asAndroidBitmap().asImageBitmap(),
                contentDescription = "Pin Image"
            )
        }
        Box(
            Modifier.weight(1f)
        ){}
    }
}

@Composable
private fun MapViewContainer(
    isEnabled: Boolean,
    mapView: MapView,
    viewModel: MapViewModel
) {
    AndroidView(
        factory = { mapView }
    ) {

        mapView.getMapAsync { map ->

            map.uiSettings.setAllGesturesEnabled(isEnabled)

            val location = viewModel.location.value
            val position = LatLng(location.latitude, location.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(position,  15f))

            map.setOnCameraIdleListener {
                val cameraPosition = map.cameraPosition
                viewModel.updateLocation(cameraPosition.target.latitude, cameraPosition.target.longitude)
            }
        }

    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    // Makes MapView follow the lifecycle of this composable
    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
private fun rememberMapLifecycleObserver(mapView: MapView) =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }

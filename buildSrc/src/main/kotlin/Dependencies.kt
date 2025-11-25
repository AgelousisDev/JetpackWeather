/**
 * To define dependencies
 */
object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.CORE_KTX_VERSION}" }
    val material by lazy { "com.google.android.material:material:${Versions.MATERIAL_VERSION}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.APP_COMPAT_VERSION}" }
    val dataStorePreferences by lazy { "androidx.datastore:datastore-preferences:${Versions.DATA_STORE_PREFERENCE_VERSION}" }
    val browser by lazy { "androidx.browser:browser:${Versions.BROWSER_VERSION}" }
    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.COMPOSE_VERSION}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.COMPOSE_VERSION}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY_VERSION}" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_VERSION}" }
    val composeMaterialYou by lazy { "androidx.compose.material3:material3:${Versions.COMPOSE_MATERIAL_YOU_VERSION}" }
    val composeMaterialYouWindowSizeClass by lazy { "androidx.compose.material3:material3-window-size-class:${Versions.COMPOSE_MATERIAL_YOU_VERSION}" }
    val composeMaterialIcons by lazy { "androidx.compose.material:material-icons-core:${Versions.COMPOSE_ICONS_VERSION}" }
    val composeUiViewBinding by lazy { "androidx.compose.ui:ui-viewbinding:${Versions.COMPOSE_VERSION}" }
    val composeRuntimeLiveData by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.COMPOSE_VERSION}" }
    val constraintLayoutCompose by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.CONSTRAINT_LAYOUT_COMPOSE_VERSION}" }
    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIVE_DATA_VIEW_MODEL_VERSION}" }
    val coilCompose by lazy { "io.coil-kt:coil-compose:${Versions.COIL_COMPOSE_VERSION}" }
    val glanceAppWidget by lazy { "androidx.glance:glance-appwidget:${Versions.GLANCE_APP_WIDGET_VERSION}" }
    val retrofit2ConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_TWO_GSON_CONVERTER_VERSION}" }
    val http3LoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.HTTP_LOGGING_INTERCEPTOR_VERSION}" }
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_VERSION}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.NAVIGATION_VERSION}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME_KTX_VERSION}" }
    val lifecycleExtensions by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.LIFECYCLE_EXTENSION_VERSION}" }
    val gmsPlayServicesLocation by lazy { "com.google.android.gms:play-services-location:${Versions.PLAY_SERVICES_LOCATION_VERSION}" }
    val gmsPlayServicesMaps by lazy { "com.google.android.gms:play-services-maps:${Versions.PLAY_SERVICES_MAPS_VERSION}" }
    val androidMapsCompose by lazy { "com.google.maps.android:maps-compose:${Versions.GOOGLE_MAPS_COMPOSE_VERSION}" }
    val lottieCompose by lazy { "com.airbnb.android:lottie-compose:${Versions.LOTTIE_COMPOSE_VERSION}" }
    val junit by lazy { "junit:junit:${Versions.JUNIT_VERSION}" }
    val testExtJunit by lazy { "androidx.test.ext:junit:${Versions.TEST_EXT_JUNIT_VERSION}" }
    val testEspressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.TEST_ESPRESSO_CORE_VERSION}" }
    val composeUiTestJunit by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE_VERSION}" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.COMPOSE_VERSION}" }
    val composeUiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE_VERSION}" }
}
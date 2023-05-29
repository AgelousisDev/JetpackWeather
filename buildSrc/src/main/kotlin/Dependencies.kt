/**
 * To define dependencies
 */
object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtxVersion}" }
    val material by lazy { "com.google.android.material:material:${Versions.materialVersion}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompatVersion}" }
    val dataStorePreferences by lazy { "androidx.datastore:datastore-preferences:${Versions.dataStorePreferenceVersion}" }
    val browser by lazy { "androidx.browser:browser:${Versions.browserVersion}" }
    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.composeVersion}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Versions.composeVersion}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.composeActivityVersion}" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}" }
    val composeMaterialYou by lazy { "androidx.compose.material3:material3:${Versions.composeMaterialYouVersion}" }
    val composeMaterialYouWindowSizeClass by lazy { "androidx.compose.material3:material3-window-size-class:${Versions.composeMaterialYouVersion}" }
    val composeUiViewBinding by lazy { "androidx.compose.ui:ui-viewbinding:${Versions.composeVersion}" }
    val composeRuntimeLiveData by lazy { "androidx.compose.runtime:runtime-livedata:${Versions.composeVersion}" }
    val constraintLayoutCompose by lazy { "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintLayoutComposeVersion}" }
    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.liveDataViewModelVersion}" }
    val coilCompose by lazy { "io.coil-kt:coil-compose:${Versions.coilComposeVersion}" }
    val glanceAppWidget by lazy { "androidx.glance:glance-appwidget:${Versions.glanceAppWidgetVersion}" }
    val retrofit2ConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit2ConverterGsonVersion}" }
    val http3LoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.http3LoggingInterceptorVersion}" }
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navVersion}" }
    val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}" }
    val lifecycleExtensions by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensionsVersion}" }
    val gmsPlayServicesLocation by lazy { "com.google.android.gms:play-services-location:${Versions.playServicesLocationVersion}" }
    val gmsPlayServicesMaps by lazy { "com.google.android.gms:play-services-maps:${Versions.playServicesMapsVersion}" }
    val androidMapsCompose by lazy { "com.google.maps.android:maps-compose:${Versions.googleMapsComposeVersion}" }
    val lottieCompose by lazy { "com.airbnb.android:lottie-compose:${Versions.lottieComposeVersion}" }
    val junit by lazy { "junit:junit:${Versions.junitVersion}" }
    val testExtJunit by lazy { "androidx.test.ext:junit:${Versions.testExtJunitVersion}" }
    val testEspressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.testEspressoCoreVersion}" }
    val composeUiTestJunit by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling:${Versions.composeVersion}" }
    val composeUiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}" }
}
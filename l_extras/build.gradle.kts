plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}

android {
    dataBinding.isEnabled = true

    compileSdkVersion(AppConfigurations.ofNumberSdk.compile)
    defaultConfig {
        minSdkVersion(AppConfigurations.ofNumberSdk.minimum)
        targetSdkVersion(AppConfigurations.ofNumberSdk.maximum)
        versionCode = AppConfigurations.applicationBuild
        versionName = AppConfigurations.applicationName

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(GoogleLibraries.appCompatv7)
    api(GoogleLibraries.supportDesign)
    api(GoogleLibraries.kotlinStdLib7)

    api(GoogleLibraries.aacLcExtensions)

    kapt(GoogleLibraries.aacLcCompiler)

    api(GoogleLibraries.playMaps)
    api(GoogleLibraries.playLocation)

    api(GoogleLibraries.kotlinCoroutinesCore)
    api(GoogleLibraries.kotlinCoroutinesAndroid)
    api(GoogleLibraries.kotlinSerialization)
    api(GoogleLibraries.kotlinCoroutinesAdapter)

    api(GoogleLibraries.fireJob)

    api(GoogleLibraries.aacRoomRuntime)

    kapt(GoogleLibraries.aacRoomCompiler)

    api(GeneralLibraries.Debugging.stetho)

    api(GeneralLibraries.Network.retrofit)
    api(GeneralLibraries.Network.retrofitMoshiConverter)
    api(GeneralLibraries.Network.okhttp)
    api(GeneralLibraries.Network.okhttpLogging)

    api(GeneralLibraries.Utils.gson)
}


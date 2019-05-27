plugins {
    id(BuildPlugins.androidDynamicFeeature)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api(project(":baseapp"))
}

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "br.project.powerguard"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.project.powerguard"
        minSdk = 32
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.itextpdf)

    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Biblioteca para fazer requisições HTTP
    implementation(libs.okhttp)

    // Biblioteca para converter JSON em objetos Java
    implementation(libs.gson)

    // Biblioteca para carregar imagens no app
    implementation(libs.glide)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
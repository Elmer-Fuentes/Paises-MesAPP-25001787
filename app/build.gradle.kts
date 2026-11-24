import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localSecrets = Properties().apply {
    val secretsFile = rootProject.file("secrets.properties")
    if (secretsFile.exists()) secretsFile.inputStream().use { load(it) }
}
val restCountriesApiKey = providers.gradleProperty("RESTCOUNTRIES_API_KEY")
    .orElse(providers.environmentVariable("RESTCOUNTRIES_API_KEY"))
    .orElse(localSecrets.getProperty("RESTCOUNTRIES_API_KEY", "rc_live_demo"))
    .get()

android {
    namespace = "com.elmerfuentes.paisesmeapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.elmerfuentes.paisesmeapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 2
        versionName = "1.1"
        buildConfigField("String", "RESTCOUNTRIES_API_KEY", "\"$restCountriesApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")

}

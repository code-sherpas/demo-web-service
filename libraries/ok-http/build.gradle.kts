plugins {
    id("kotlin-library-conventions")
}

dependencies {
    api(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    api("com.squareup.okhttp3:okhttp")
}

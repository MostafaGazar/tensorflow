
def okhttp():
    native.maven_jar(
        name = "okhttp_logging",
        artifact = "com.squareup.okhttp3:logging-interceptor:3.1.0",
    )

    native.maven_jar(
        name = "okhttp_android",
        artifact = "com.squareup.okhttp:okhttp-android-support:2.7.2",
    )

    native.maven_jar(
        name = "okio",
        artifact = "com.squareup.okio:okio:1.6.0",
    )

    native.maven_jar(
        name = "okhttp",
        artifact = "com.squareup.okhttp3:okhttp:3.1.0",
    )

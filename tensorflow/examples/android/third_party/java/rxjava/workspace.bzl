
def rxjava():
    native.maven_jar(
        name = "rxjava",
        artifact = "io.reactivex:rxjava:1.1.8",
    )

    native.new_http_archive(
        name = "rxjava_android",
        url = "https://repo1.maven.org/maven2/io/reactivex/rxandroid/1.2.1/rxandroid-1.2.1.aar",
        type = "zip",
        build_file = "tensorflow/examples/android/third_party/java/rxjava/rxjava_android.BUILD",
    )

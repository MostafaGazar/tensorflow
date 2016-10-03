
def junit():
    native.maven_jar(
        name = "junit",
        artifact = "junit:junit:4.12",
    )

    native.maven_jar(
        name = "hamcrest",
        artifact = "org.hamcrest:hamcrest-all:1.3",
    )

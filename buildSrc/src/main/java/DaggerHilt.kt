object DaggerHilt {
    const val daggerHiltVersion = "2.43.1"
    const val daggerHilt =
        "com.google.dagger:hilt-android:$daggerHiltVersion"

    const val kaptDaggerHiltCompiler =
        "com.google.dagger:hilt-android-compiler:$daggerHiltVersion"

    private const val kaptHiltCompilerVersion = "1.0.0"
    const val kaptHiltCompiler = "androidx.hilt:hilt-compiler:$kaptHiltCompilerVersion"
}
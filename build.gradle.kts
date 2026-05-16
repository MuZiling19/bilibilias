// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.gms.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.multiplatform.library) apply false
    alias(libs.plugins.androidx.room3) apply false
    alias(libs.plugins.wire) apply false

}

val iosArtifactModules = listOf(
    ":core:common" to "CoreCommon",
    ":core:data" to "CoreData",
    ":core:database" to "CoreDatabase",
    ":core:datastore" to "CoreDatastore",
    ":core:datastore-proto" to "CoreDatastoreProto",
    ":core:network" to "CoreNetwork",
)

tasks.register("assembleCoreIosArtifacts") {
    group = "build"
    description = "Builds iOS XCFramework artifacts for core modules except ui and ffmpeg."

    dependsOn(
        iosArtifactModules.map { (path, frameworkName) ->
            "$path:assemble${frameworkName}ReleaseXCFramework"
        }
    )
}

tasks.register<Sync>("collectCoreIosArtifacts") {
    group = "build"
    description = "Collects iOS XCFramework artifacts for core modules into a single directory."

    dependsOn("assembleCoreIosArtifacts")
    into(layout.projectDirectory.dir("core/build/ios-artifacts"))

    iosArtifactModules.forEach { (path, frameworkName) ->
        from(project(path).layout.buildDirectory.dir("XCFrameworks/release/$frameworkName.xcframework")) {
            into("$frameworkName.xcframework")
        }
    }
}

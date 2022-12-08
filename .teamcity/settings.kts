import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.10"

project {
    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    allowExternalStatus = true
    artifactRules = "target/**/*"

    vcs {
        root(DslContext.settingsRoot)
    }

    expectSteps {
        step {
            name = "Build (Debug)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-bench-package", "platform_linux")
            param("cargo-build-package", "platform_linux")
            param("cargo-command", "build")
            param("cargo-test-no-default-features", "true")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
        }
        step {
            name = "Build (Release)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-bench-arguments", "--release")
            param("cargo-bench-package", "platform_linux")
            param("cargo-build-package", "platform_linux")
            param("cargo-build-release", "true")
            param("cargo-command", "build")
            param("cargo-test-no-default-features", "true")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
        }
    }
    steps {
        insert(0) {
            dockerCommand {
                name = "Build Docker Image"
                commandType = build {
                    source = file {
                        path = ".ci/Dockerfile"
                    }
                    contextDir = ".ci"
                    platform = DockerCommandStep.ImagePlatform.Linux
                    namesAndTags = "bevy_ci_image:latest"
                    commandArgs = "--pull"
                }
            }
        }
        insert(1) {
            script {
                name = "Build (Debug)"
                scriptContent = "cargo build"
                dockerImage = "bevy_ci_image:latest"
                dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
            }
        }
        insert(2) {
            script {
                name = "Test (Debug)"
                scriptContent = "cargo test"
                dockerImage = "bevy_ci_image:latest"
                dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
            }
        }
        insert(3) {
            script {
                name = "Build (Release)"
                scriptContent = "cargo build --release"
                dockerImage = "bevy_ci_image:latest"
                dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
            }
        }
        insert(4) {
            script {
                name = "Test (Release)"
                scriptContent = "cargo test --release"
                dockerImage = "bevy_ci_image:latest"
                dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
            }
        }
        items.removeAt(5)
        items.removeAt(5)
    }

    triggers {
        vcs {
        }
        schedule {
            schedulingPolicy = daily {
                hour = 3
            }
            triggerBuild = always()
        }
    }
})

package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildStep
import jetbrains.buildServer.configs.kotlin.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Test'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Test")) {
    expectSteps {
        step {
            name = "Test (Debug)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-command", "test")
            param("cargo-test-no-default-features", "true")
            param("cargo-test-no-fail-fast", "true")
            param("cargo-test-package", "platform_linux")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
        }
        step {
            name = "Test (Release)"
            type = "cargo"
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            param("cargo-command", "test")
            param("cargo-test-no-default-features", "true")
            param("cargo-test-no-fail-fast", "true")
            param("cargo-test-package", "platform_linux")
            param("cargo-test-release", "true")
            param("cargo-toolchain", "stable")
            param("cargo-verbosity", "--verbose")
        }
    }
    steps {
        update<BuildStep>(0) {
            clearConditions()
            param("cargo-toolchain", "nightly")
        }
        update<BuildStep>(1) {
            clearConditions()
            param("cargo-toolchain", "nightly")
        }
    }
}

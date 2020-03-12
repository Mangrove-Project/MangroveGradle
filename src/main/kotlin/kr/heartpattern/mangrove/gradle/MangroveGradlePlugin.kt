package kr.heartpattern.mangrove.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.invoke

@Suppress("UnstableApiUsage")
class MangroveGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurations.create("mangrove")
        project.extensions.create<MangroveExtension>("mangrove")

        project.tasks {
            create<GenerateDependencyInformationTask>("generateMangrove")
        }
    }
}
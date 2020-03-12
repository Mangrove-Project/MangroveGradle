package kr.heartpattern.mangrove.gradle

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

@Suppress("UnstableApiUsage")
open class GenerateDependencyInformationTask : DefaultTask() {
    @Input
    private val configuration: Configuration = project.configurations.getByName("mangrove")

    @Input
    private val extension: MangroveExtension = project.extensions.getByName("mangrove") as MangroveExtension

    @OutputFile
    private val dependencyFile: File = File(project.buildDir, "dependency.mangrove.json")

    @OutputFile
    private val repositoryFile: File = File(project.buildDir, "repository.mangrove.json")

    private val gson = GsonBuilder().setPrettyPrinting().create()

    @TaskAction
    fun generateDependencyInformation() {
        writeDependency()
        writeRepository()
    }

    private fun writeDependency() {
        if (extension.includeRuntime)
            configuration.extendsFrom(project.configurations.getByName("runtime"))

        val json = JsonArray()
        for (dependency in configuration.allDependencies) {
            val obj = JsonObject()
            obj.add("group", JsonPrimitive(dependency.group))
            obj.add("name", JsonPrimitive(dependency.name))
            obj.add("version", JsonPrimitive(dependency.version))

            if (dependency is ModuleDependency && dependency.excludeRules.isNotEmpty()) {
                val exclude = JsonArray()

                for (excludeRule in dependency.excludeRules) {
                    val ex = JsonObject()
                    ex.add("group", JsonPrimitive(excludeRule.group))
                    ex.add("name", JsonPrimitive(excludeRule.module))
                    exclude.add(ex)
                }

                obj.add("exclude", exclude)
            }
            json.add(obj)
        }

        if (json.size() == 0) {
            dependencyFile.delete()
        } else {
            dependencyFile.createNewFile()
            dependencyFile.writeText(gson.toJson(json))
        }
    }

    private fun writeRepository() {
        if (extension.repositories.isEmpty()) {
            repositoryFile.delete()
        } else {
            val json = JsonObject()

            for ((id, url) in extension.repositories) {
                json.add(id, JsonPrimitive(url))
            }

            repositoryFile.createNewFile()
            repositoryFile.writeText(gson.toJson(json))
        }
    }
}
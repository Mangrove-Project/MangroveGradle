package kr.heartpattern.mangrove.gradle

import java.util.*

open class MangroveExtension {
    var includeRuntime = false

    internal val repositories: LinkedList<Pair<String, String>> = LinkedList()

    fun includeRepository(id: String, url: String) {
        repositories += id to url
    }
}
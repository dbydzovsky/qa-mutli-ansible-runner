package cz.dbydzovsky.multiAnsibleRunner.tool

import org.apache.commons.lang3.SystemUtils
import java.io.File

fun String.runCommand(workingDir: File? = null): Int {
    println("Executing command: '$this' on path: ${workingDir?.path}")
//    val commands = if (SystemUtils.IS_OS_LINUX) mutableListOf("/bin/bash", "-c") else mutableListOf()
//    commands.addAll(this.split(" "))
    val process = ProcessBuilder(*this.split(" ").toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
    process.waitFor()
    return process.exitValue()
}

//fun String.runCommand(workingDir: File): String? {
//    try {
//        val parts = this.split("\\s".toRegex())
//        val proc = ProcessBuilder(*parts.toTypedArray())
//                .directory(workingDir)
//                .redirectOutput(ProcessBuilder.Redirect.PIPE)
//                .redirectError(ProcessBuilder.Redirect.PIPE)
//                .start()
//
//        proc.waitFor(60, TimeUnit.MINUTES)
//        return proc.inputStream.bufferedReader().readText()
//    } catch(e: IOException) {
//        e.printStackTrace()
//        return null
//    }
//}

fun String.toUnixPath(): String {
    return "/${this.replace("\\", "/").replace(":", "")}"
}

fun String.asResource(clazz: Any): java.net.URL {
    return clazz.javaClass.classLoader.getResource(this)
}

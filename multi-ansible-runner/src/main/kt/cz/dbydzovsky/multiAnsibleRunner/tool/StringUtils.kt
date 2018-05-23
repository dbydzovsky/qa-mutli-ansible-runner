package cz.dbydzovsky.multiAnsibleRunner.tool

import java.io.File

fun String.runCommand(workingDir: File? = null): Int {
    val process = ProcessBuilder(*split(" ").toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
    process.waitFor()
    return process.exitValue()
}

fun String.toUnixPath(): String {
    return "/${this.replace("\\", "/").replace(":", "")}"
}

// or
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
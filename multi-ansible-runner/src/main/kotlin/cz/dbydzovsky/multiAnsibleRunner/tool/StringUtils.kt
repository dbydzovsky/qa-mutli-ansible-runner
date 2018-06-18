package cz.dbydzovsky.multiAnsibleRunner.tool

import java.io.BufferedOutputStream
import java.io.File
import java.io.InputStream
import java.util.logging.Level
import java.util.logging.Logger

val logger = Logger.getLogger("commandExecutor")

fun String.runCommandWithoutOutput(workingDir: File? = null, envs: Map<out String,String>? = null): Int {
    return execute(this.split(" "), workingDir, envs, false).first
}

fun String.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Int {
    return execute(this.split(" "), workingDir, envs).first
}

fun Array<String>.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Int {
    return execute(this.toList(), workingDir, envs).first
}

fun List<String>.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Int {
    return execute(this, workingDir, envs).first
}

fun List<String>.runCommandAndRead(workingDir: File? = null, envs: Map<out String,String>? = null): Pair<Int, InputStream> {
    return execute(this, workingDir, envs, false)
}

private fun execute(commands: List<String>, dir: File? = null, envs: Map<out String,String>? = null, output: Boolean? = true): Pair<Int, InputStream> {
    if (output == true) {
        logger.log(Level.INFO,  "Executing command: [${commands.joinToString(",") { it }}] on path: ${dir?.path}")
    }
    val processBuilder = ProcessBuilder(commands)
            .directory(dir)
            .redirectOutput(if (output == true) ProcessBuilder.Redirect.INHERIT else ProcessBuilder.Redirect.PIPE)
            .redirectError(if (output == true) ProcessBuilder.Redirect.INHERIT else ProcessBuilder.Redirect.PIPE)
    processBuilder.environment().putAll(envs ?: emptyMap())
    val process = processBuilder.start()
    process.waitFor()
    return Pair(process.exitValue(), process.inputStream)
}

fun String.toUnixPath(): String {
    return "/${this.replace("\\", "/").replace(":", "")}"
}

fun String.asResource(clazz: Any): java.net.URL {
    return clazz.javaClass.classLoader.getResource(this)
}
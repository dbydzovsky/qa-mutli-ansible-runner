package cz.dbydzovsky.multiAnsibleRunner.tool

import java.io.File

fun String.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Number {
    return execute(this.split(" "), workingDir, envs)
}

fun Array<String>.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Number {
    return execute(this.toList(), workingDir, envs)
}

fun List<String>.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Number {
    return execute(this, workingDir, envs)
}

private fun execute(commands: List<String>, dir: File? = null, envs: Map<out String,String>? = null): Number {
    println("Executing command: [${commands.joinToString(",") { it }}] on path: ${dir?.path}")
    val processBuilder = ProcessBuilder(commands)
            .directory(dir)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
    processBuilder.environment().putAll(envs ?: emptyMap())
    val process = processBuilder.start()
    process.waitFor()
    return process.exitValue()
}

fun String.toUnixPath(): String {
    return "/${this.replace("\\", "/").replace(":", "")}"
}

fun String.asResource(clazz: Any): java.net.URL {
    return clazz.javaClass.classLoader.getResource(this)
}

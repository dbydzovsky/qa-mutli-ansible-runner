package cz.dbydzovsky.multiAnsibleRunner.tool

import java.io.File

fun String.runCommandWithoutOutput(workingDir: File? = null, envs: Map<out String,String>? = null): Number {
    return execute(this.split(" "), workingDir, envs, false)
}

fun String.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Number {
    return execute(this.split(" "), workingDir, envs)
}

fun Array<String>.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Number {
    return execute(this.toList(), workingDir, envs)
}

fun List<String>.runCommand(workingDir: File? = null, envs: Map<out String,String>? = null): Number {
    return execute(this, workingDir, envs)
}

private fun execute(commands: List<String>, dir: File? = null, envs: Map<out String,String>? = null, output: Boolean? = true): Number {
    if (output == true) {
        println("Executing command: [${commands.joinToString(",") { it }}] on path: ${dir?.path}")
    }
    val processBuilder = ProcessBuilder(commands)
            .directory(dir)
            .redirectOutput(if (output == true) ProcessBuilder.Redirect.INHERIT else ProcessBuilder.Redirect.PIPE)
            .redirectError(if (output == true) ProcessBuilder.Redirect.INHERIT else ProcessBuilder.Redirect.PIPE)
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

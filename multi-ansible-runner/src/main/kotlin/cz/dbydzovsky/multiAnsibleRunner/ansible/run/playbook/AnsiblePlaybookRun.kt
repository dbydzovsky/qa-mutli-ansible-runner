package cz.dbydzovsky.multiAnsibleRunner.ansible.run.playbook

import cz.dbydzovsky.multiAnsibleRunner.ansible.run.AnsibleRunType
import cz.dbydzovsky.multiAnsibleRunner.ansible.run.IAnsibleRun
import org.apache.commons.text.StringEscapeUtils
import java.io.File

open class AnsiblePlaybookRun : IAnsibleRun {

    var name: String? = null

    var playbook: String? = null

    var hosts: String? = null

    var additionalCommands: MutableList<String> = mutableListOf()

    var envs: MutableList<Pair<String, String>> = mutableListOf()

    var jsonEnvs: MutableList<Pair<String, String>> = mutableListOf()

    var fileEnvs: MutableList<String> = mutableListOf()

    override var workingDir: File? = null

    override fun toCommand(): List<String> {
        val command = mutableListOf(AnsibleRunType.ANSIBLE_PLAYBOOK.command, playbook ?: "")
        if (hosts != null) {
            command.add("-i")
            command.add(hosts!!)
        }
        envs.forEach {
            command.add("-e")
            command.add("'${it.first}=\"${it.second}\"'")
        }
        jsonEnvs.forEach {
            command.add("-e")
            command.add("\"{\\\"${it.first}\\\":${escape(it.second)}}\"")
        }
        additionalCommands.forEach {
            command.add(it)
        }
        fileEnvs.forEach {
            command.add("-e")
            command.add("\"@$it\"")
        }
        return command.toList()
    }

    fun escape(raw: String): String {
        return StringEscapeUtils.escapeJson(raw)
    }
}
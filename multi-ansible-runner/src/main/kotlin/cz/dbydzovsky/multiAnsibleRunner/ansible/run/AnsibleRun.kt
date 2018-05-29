package cz.dbydzovsky.multiAnsibleRunner.ansible.run

import java.io.File

class AnsibleRun: IAnsibleRun {

    var command: String = ""

    override var workingDir: File? = null

    override fun toCommand(): List<String> {
        return listOf(AnsibleRunType.ANSIBLE.command, command)
    }

}
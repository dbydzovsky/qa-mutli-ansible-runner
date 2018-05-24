package cz.dbydzovsky.multiAnsibleRunner.ansible.run.playbook

import cz.dbydzovsky.multiAnsibleRunner.ansible.run.AnsibleRunType
import cz.dbydzovsky.multiAnsibleRunner.ansible.run.IAnsibleRun
import java.io.File

class AnsiblePlaybookRun : IAnsibleRun {

    var name: String? = null

    var playbook: String? = null

    var hosts: String? = null

    override var workingDir: File? = null

    override fun toCommand(): String {
        var command = "${AnsibleRunType.ANSIBLE_PLAYBOOK.command} $playbook"
        if (hosts != null) {
            command += "-i $hosts"
        }
        return command
    }
}
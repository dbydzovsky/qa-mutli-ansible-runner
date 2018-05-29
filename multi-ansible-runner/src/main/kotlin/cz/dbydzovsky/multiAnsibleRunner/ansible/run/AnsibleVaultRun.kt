package cz.dbydzovsky.multiAnsibleRunner.ansible.run

import java.io.File

class AnsibleVaultRun() : IAnsibleRun{

    override var workingDir: File? = null

    override fun toCommand(): List<String> {
        return listOf(AnsibleRunType.ANSIBLE_VAULT.command)
    }
}
package cz.dbydzovsky.multiAnsibleRunner.ansible.auth

import cz.dbydzovsky.multiAnsibleRunner.ansible.run.playbook.AnsiblePlaybookRun

interface AnsibleAuthentication {

    fun authenticate(ansibleRun: AnsiblePlaybookRun)

}
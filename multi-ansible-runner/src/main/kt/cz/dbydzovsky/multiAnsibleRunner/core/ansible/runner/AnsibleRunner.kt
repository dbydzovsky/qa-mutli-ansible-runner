package cz.dbydzovsky.multiAnsibleRunner.core.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.core.ansible.obj.AnsibleRun

interface AnsibleRunner {

    fun run(ansibleRun: AnsibleRun)
}
package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.ansible.obj.AnsibleRun

interface AnsibleRunner {

    fun run(ansibleRun: AnsibleRun)
}
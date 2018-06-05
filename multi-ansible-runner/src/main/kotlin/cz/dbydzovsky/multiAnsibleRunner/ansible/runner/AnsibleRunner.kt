package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.ansible.run.IAnsibleRun


interface AnsibleRunner {

    fun run(ansibleRun: IAnsibleRun): Int
}
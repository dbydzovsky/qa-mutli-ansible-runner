package cz.dbydzovsky.multiAnsibleRunner.ansible.auth

import cz.dbydzovsky.multiAnsibleRunner.ansible.obj.AnsibleRun

interface AnsibleAuthentication {

    fun authenticate(ansibleRun: AnsibleRun)

}
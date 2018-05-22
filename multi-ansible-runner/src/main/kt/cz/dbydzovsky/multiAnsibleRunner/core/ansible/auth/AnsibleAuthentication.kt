package cz.dbydzovsky.multiAnsibleRunner.core.ansible.auth

import cz.dbydzovsky.multiAnsibleRunner.core.ansible.obj.AnsibleRun

interface AnsibleAuthentication {

    fun authenticate(ansibleRun: AnsibleRun)

}
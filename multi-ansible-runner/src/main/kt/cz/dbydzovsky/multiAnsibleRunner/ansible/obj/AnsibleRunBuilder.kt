package cz.dbydzovsky.multiAnsibleRunner.ansible.obj

import cz.dbydzovsky.multiAnsibleRunner.ansible.auth.AnsibleAuthentication

class AnsibleRunBuilder {

    var ansibleRun = AnsibleRun()

    fun withPlaybook(playbook: String): AnsibleRunBuilder  {
        ansibleRun.playbook = playbook
        return this
    }

    fun authenticateWith(ansibleAuthentication: AnsibleAuthentication): AnsibleRunBuilder {
        ansibleAuthentication.authenticate(ansibleRun)
        return this
    }

    fun withHosts(hosts: String): AnsibleRunBuilder  {
        ansibleRun.hosts = hosts
        return this
    }


    fun withShared(source: String, target: String): AnsibleRunBuilder  {
        ansibleRun.sharedFolders.add(Pair(source, target))
        return this
    }

    fun build(): AnsibleRun {
        val r = ansibleRun
        this.ansibleRun = AnsibleRun()
        return r
    }
}
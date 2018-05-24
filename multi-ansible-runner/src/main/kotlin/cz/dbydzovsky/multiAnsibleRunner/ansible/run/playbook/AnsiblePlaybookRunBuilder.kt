package cz.dbydzovsky.multiAnsibleRunner.ansible.run.playbook

import cz.dbydzovsky.multiAnsibleRunner.ansible.auth.AnsibleAuthentication
import java.io.File

class AnsiblePlaybookRunBuilder {

    var ansibleRun = AnsiblePlaybookRun()

    fun withPlaybook(playbook: String): AnsiblePlaybookRunBuilder {
        ansibleRun.playbook = playbook
        return this
    }

    fun withWorkingDir(workingDir: File): AnsiblePlaybookRunBuilder {
        ansibleRun.workingDir = workingDir
        return this
    }

    fun authenticateWith(ansibleAuthentication: AnsibleAuthentication): AnsiblePlaybookRunBuilder {
        ansibleAuthentication.authenticate(ansibleRun)
        return this
    }

    fun withHosts(hosts: String): AnsiblePlaybookRunBuilder {
        ansibleRun.hosts = hosts
        return this
    }

    fun build(): AnsiblePlaybookRun {
        val r = ansibleRun
        this.ansibleRun = AnsiblePlaybookRun()
        return r
    }
}
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

    fun withAdditionalCommand(command: String): AnsiblePlaybookRunBuilder {
        ansibleRun.additionalCommands.add(command)
        return this
    }

    fun withAdditionalCommands(commands: List<String>): AnsiblePlaybookRunBuilder {
        ansibleRun.additionalCommands.addAll(commands)
        return this
    }

    fun withEnv(name: String, value: String): AnsiblePlaybookRunBuilder {
        ansibleRun.envs.add(Pair(name, value))
        return this
    }

    fun withEnvs(envs: List<Pair<String, String>>): AnsiblePlaybookRunBuilder {
        ansibleRun.envs.addAll(envs)
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
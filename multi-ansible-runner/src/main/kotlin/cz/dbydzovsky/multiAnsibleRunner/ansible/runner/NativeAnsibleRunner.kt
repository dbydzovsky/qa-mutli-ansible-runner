package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.ansible.run.IAnsibleRun
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand

class NativeAnsibleRunner: AnsibleRunner {
    override fun run(ansibleRun: IAnsibleRun) {
        ansibleRun.toCommand().runCommand(ansibleRun.workingDir)
    }
}
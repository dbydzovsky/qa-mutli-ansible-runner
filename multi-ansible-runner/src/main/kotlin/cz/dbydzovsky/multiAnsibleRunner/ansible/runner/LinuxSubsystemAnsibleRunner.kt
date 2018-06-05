package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.ansible.run.IAnsibleRun
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import org.apache.commons.lang3.SystemUtils

class LinuxSubsystemAnsibleRunner: AnsibleRunner {

    init {
        if (!SystemUtils.IS_OS_WINDOWS) {
            throw IllegalStateException("Cannot run bash.exe in non-windows systems.")
        }
    }

    override fun run(ansibleRun: IAnsibleRun): Int {
        return (listOf("bash.exe", "-c") + ansibleRun.toCommand()).runCommand(ansibleRun.workingDir)
    }
}
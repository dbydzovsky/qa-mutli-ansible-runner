package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.docker.DockerTypeSurveyor
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommandWithoutOutput
import org.apache.commons.lang3.SystemUtils

class DefaultAnsibleRunnerFactory {

    val instance: AnsibleRunner
        get() {
            return if (DockerTypeSurveyor.dockerInstalled) {
                AnsibleInDockerRunner()
            } else if (isAnsibleInstalled()) {
                NativeAnsibleRunner()
            } else if (SystemUtils.IS_OS_WINDOWS && isSubsystemForLinuxInstalled()){
                LinuxSubsystemAnsibleRunner()
            } else {
                throw IllegalStateException("There is no ansible executor found.")
            }
        }

    private fun isAnsibleInstalled(): Boolean {
        return (if (SystemUtils.IS_OS_LINUX) "/bin/bash -c ansible" else "ansible").runCommandWithoutOutput() == 0
    }

    private fun isSubsystemForLinuxInstalled(): Boolean {
        return "bash.exe -c echo".runCommand() == 0
    }

}
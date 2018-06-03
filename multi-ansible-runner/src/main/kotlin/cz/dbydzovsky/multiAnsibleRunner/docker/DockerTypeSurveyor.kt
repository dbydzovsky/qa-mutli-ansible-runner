package cz.dbydzovsky.multiAnsibleRunner.docker

import cz.dbydzovsky.multiAnsibleRunner.tool.runCommandWithoutOutput
import org.apache.commons.lang3.SystemUtils

class DockerTypeSurveyor {
    companion object {
        private val isWindows = SystemUtils.IS_OS_WINDOWS

        val dockerInstalled: Boolean
            get() {
                return (if (SystemUtils.IS_OS_LINUX) "/bin/bash -c docker" else "docker").runCommandWithoutOutput() == 0
            }

        val dockerType: DockerType
            get() {
                return if (!dockerInstalled) {
                    DockerType.None
                } else if (isWindows) {
                    if ("docker-machine".runCommandWithoutOutput() != 9009) {
                        DockerType.DockerToolbox
                    } else {
                        DockerType.DockerForWindows
                    }
                } else {
                    DockerType.DockerLinux
                }
            }
    }
}
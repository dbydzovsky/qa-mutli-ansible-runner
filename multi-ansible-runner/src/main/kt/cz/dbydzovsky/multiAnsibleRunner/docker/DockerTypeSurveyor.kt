package cz.dbydzovsky.multiAnsibleRunner.docker

import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import org.apache.commons.lang3.SystemUtils

class DockerTypeSurveyor {
    companion object {
        private val isWindows = SystemUtils.IS_OS_WINDOWS

        val dockerInstalled: Boolean
            get() {
                return "docker".runCommand() != 0
            }

        val dockerType: DockerType
            get() {
                return if (!dockerInstalled) {
                    DockerType.None
                } else if (isWindows) {
                    if ("docker-machine".runCommand() != 9009) {
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
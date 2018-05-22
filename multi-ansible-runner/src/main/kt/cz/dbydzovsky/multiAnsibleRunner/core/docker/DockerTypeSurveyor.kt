package cz.dbydzovsky.multiAnsibleRunner.core.docker

import org.apache.commons.lang3.SystemUtils

class DockerTypeSurveyor {
    companion object {
        private val isWindows = SystemUtils.IS_OS_WINDOWS

        fun dockerInstalled() {
            TODO()
            // cz.dbydzovsky.core.clusterProvider.CommandExecutor.execute(["docker"], false) != 0
        }

        val dockerType: DockerType
                get() {
//                    if (cz.dbydzovsky.core.clusterProvider.CommandExecutor.execute(["docker-machine"], false) != 9009) {
//                        return DockerType.DOCKER_TOOLBOX
//                    }
//                    return DockerType.DOCKER_FOR_WINDOWS
                    return DockerType.DockerToolbox
                }
    }
}
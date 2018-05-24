package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.docker.DockerTypeSurveyor

class DefaultAnsibleRunnerFactory {

    val instance: AnsibleRunner
        get() {
            return if (DockerTypeSurveyor.dockerInstalled) {
                AnsibleInDockerRunner()
            } else {
                NativeAnsibleRunner()
            }
        }
}
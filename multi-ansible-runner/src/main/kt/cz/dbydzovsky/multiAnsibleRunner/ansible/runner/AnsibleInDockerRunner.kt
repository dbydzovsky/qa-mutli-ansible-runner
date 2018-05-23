package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.Constants.ANSIBLE_IN_DOCKER_IMAGE
import cz.dbydzovsky.multiAnsibleRunner.ansible.obj.AnsibleRun
import cz.dbydzovsky.multiAnsibleRunner.docker.DockerType
import cz.dbydzovsky.multiAnsibleRunner.docker.DockerTypeSurveyor
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import cz.dbydzovsky.multiAnsibleRunner.tool.toUnixPath
import org.apache.commons.lang3.SystemUtils

class AnsibleInDockerRunner(val dockerImage: String = ANSIBLE_IN_DOCKER_IMAGE()) : AnsibleRunner {

    val dockerType: DockerType = DockerTypeSurveyor.dockerType
    /**
     * Pair<String, String> ~ source:target
     */
    var sharedFolders: MutableList<Pair<String, String>> = mutableListOf()

    override fun run(ansibleRun: AnsibleRun) {
        val c = StringBuilder(dockerImage)
        c.append("docker run")
        c.append(toAnsibleSharedFolders(sharedFolders).joinToString { " " })
        c.append(dockerImage)
        c.append(ansibleRun.toString())

        c.toString().runCommand()
    }

    private fun toAnsibleSharedFolders(sharedFolders: List<Pair<String, String>>): List<String> {
        return sharedFolders.map {
            if (SystemUtils.IS_OS_WINDOWS && dockerType === DockerType.DockerToolbox) {
                return@map "-v \"${it.first.toUnixPath()}:${it.second}\""
            }
            return@map "${it.first}:${it.second}"
        }
    }
}
package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.Constants.ANSIBLE_IN_DOCKER_IMAGE
import cz.dbydzovsky.multiAnsibleRunner.ansible.run.IAnsibleRun
import cz.dbydzovsky.multiAnsibleRunner.docker.DockerType
import cz.dbydzovsky.multiAnsibleRunner.docker.DockerTypeSurveyor
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import cz.dbydzovsky.multiAnsibleRunner.tool.toUnixPath
import org.apache.commons.lang3.SystemUtils

open class AnsibleInDockerRunner(private var dockerImage: String = ANSIBLE_IN_DOCKER_IMAGE()) : AnsibleRunner {

//    var privateSsh = File("ssh/id_rsa".asResource(this).path)
//    var publicSsh = File("ssh/id_rsa.pub".asResource(this).path)

    private val dockerType: DockerType = DockerTypeSurveyor.dockerType

    private var playbookPath: String? = null
    /**
     * Pair<String, String> ~ source:target
     */
    private var sharedFolders: MutableList<Pair<String, String>> = mutableListOf()

    fun setDockerImage(image: String): AnsibleInDockerRunner {
        this.dockerImage = image
        return this
    }

    fun setPlaybookPath(source: String): AnsibleInDockerRunner {
        this.playbookPath = source
        return this
    }

    fun addSharedFolder(source: String, target: String): AnsibleInDockerRunner {
        this.sharedFolders.add(Pair(source, target))
        return this
    }

    fun addSharedFolders(shared: List<Pair<String, String>>): AnsibleInDockerRunner {
        this.sharedFolders.addAll(shared)
        return this
    }

    override fun run(ansibleRun: IAnsibleRun): Int {
        val c = mutableListOf("docker", "run")
        if (playbookPath != null) {
            c.addAll(sharePlaybookPath(playbookPath!!))
        }
//        c.addAll(toAnsibleSharedFolder(privateSsh.canonicalPath, "/root/.ssh/id_rsa"))
//        c.addAll(toAnsibleSharedFolder(publicSsh.canonicalPath, "/root/.ssh/id_rsa.pub"))
        c.addAll(toAnsibleSharedFolders(sharedFolders))
        c.add(dockerImage)
        c.add("\"" + ansibleRun.toCommand().joinToString(" ") + "\"")

        return c.runCommand(ansibleRun.workingDir)
    }

    private fun toAnsibleSharedFolders(sharedFolders: List<Pair<String, String>>): List<String> {
        return sharedFolders.map {
            return@map toAnsibleSharedFolder(it.first, it.second)
        }.flatten()
    }

    private fun toAnsibleSharedFolder(source: String, target: String): List<String> {
        return if (SystemUtils.IS_OS_WINDOWS && dockerType === DockerType.DockerToolbox) {
            listOf("--mount", "type=bind,source=\"${source.toUnixPath()}\",target=\"$target\"")
        } else {
            listOf("--mount", "type=bind,source=\"$source\",target=\"$target\"")
        }
    }

    private fun sharePlaybookPath(path: String): List<String>{
        return toAnsibleSharedFolder(path, "/ansible/playbooks")
    }
}
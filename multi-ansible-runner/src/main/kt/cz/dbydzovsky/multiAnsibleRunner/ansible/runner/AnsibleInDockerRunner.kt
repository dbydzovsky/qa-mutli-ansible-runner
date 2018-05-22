package cz.dbydzovsky.multiAnsibleRunner.ansible.runner

import cz.dbydzovsky.multiAnsibleRunner.Constants.ANSIBLE_IN_DOCKER_IMAGE
import cz.dbydzovsky.multiAnsibleRunner.ansible.obj.AnsibleRun
import cz.dbydzovsky.multiAnsibleRunner.docker.DockerType
import cz.dbydzovsky.multiAnsibleRunner.docker.DockerTypeSurveyor

class AnsibleInDockerRunner(val dockerImage: String = ANSIBLE_IN_DOCKER_IMAGE()): AnsibleRunner {

    val dockerType: DockerType = DockerTypeSurveyor.dockerType

    override fun run(ansibleRun: AnsibleRun) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

/*

    fun invoke

    String ISC_CLUSTER_DIR = {
        File root = new File("")
        try {
            if (root.getCanonicalFile().getName() == "nucleus-cluster") {
                root = root.getCanonicalFile().getParentFile().getParentFile()
            }
            if (root.getCanonicalFile().getName() == "nuc-system-tests") {
                root = root.getCanonicalFile().getParentFile()
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
        root.getAbsolutePath() + "/nuc-definitions/tools/isc-cluster/"
    }()

    String ISC_CLUSTER_DIR_TO_BE_SHARED = {
        if (DockerExaminer.dockerType == DockerType.DOCKER_TOOLBOX) {
            return "/" + ISC_CLUSTER_DIR.replace("\\", "/").replace(":", "")
        } else {
            return ISC_CLUSTER_DIR
        }
    }()

    void invoke(AnsibleRun ansibleRun) {
        List<String> command = []
        command.addAll("docker", "run")
        ansibleRun.sharedFolders.forEach {
            command.add("-v")
            command.add("\"$it\"")
        }

        command.addAll(["-v", "\"$ISC_CLUSTER_DIR_TO_BE_SHARED:/ansible/playbooks\"", DOCKER_IMAGE,
            ansibleRun.playbook])

        if (!ansibleRun.hosts.isEmpty()) {
            command.add("-i")
            command.add(ansibleRun.hosts)
        }

        ansibleRun.extraVars.forEach {
            command.add("-e")
            command.add(it)
        }

        def code = cz.dbydzovsky.core.core.CommandExecutor.execute(command)
        print(code)
    }
 */
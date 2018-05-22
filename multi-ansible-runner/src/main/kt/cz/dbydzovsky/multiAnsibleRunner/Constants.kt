package cz.dbydzovsky.multiAnsibleRunner

object Constants {

    fun ANSIBLE_IN_DOCKER_IMAGE(version: String = "latest"): String {
        return "dbydzovsky/ansible-in-docker:$version"
    }

}
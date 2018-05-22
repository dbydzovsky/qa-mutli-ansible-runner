package cz.dbydzovsky.multiAnsibleRunner.core

object Constants {

    fun ANSIBLE_IN_DOCKER_IMAGE(version: String = "latest"): String {
        return "dbydzovsky/ansible-in-docker:$version"
    }

}
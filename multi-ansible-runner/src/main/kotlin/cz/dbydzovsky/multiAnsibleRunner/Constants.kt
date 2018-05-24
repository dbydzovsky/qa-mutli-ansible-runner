package cz.dbydzovsky.multiAnsibleRunner

object Constants {

    val DEFAULT_IMAGE = "dbydzovsky/ansible-in-docker"

    fun ANSIBLE_IN_DOCKER_IMAGE(version: String = "latest"): String {
        return "$DEFAULT_IMAGE:$version"
    }

}
package cz.dbydzovsky.multiAnsibleRunner.ansible.obj

class AnsibleRun {

    var name: String? = null

    var playbook: String? = null

    var hosts: String? = null

    fun toCommand(): String {
        return "$playbook -i $hosts"
    }
}
package cz.dbydzovsky.multiAnsibleRunner.ansible.obj

class AnsibleRun {

    var name: String? = null

    var playbook: String? = null

    var hosts: String? = null

    /**
     * Pair<String, String> ~ source:target
     */
    var sharedFolders: MutableList<Pair<String, String>> = mutableListOf()
}
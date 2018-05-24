package cz.dbydzovsky.multiAnsibleRunner.ansible.run

enum class AnsibleRunType(val command: String) {
    ANSIBLE_PLAYBOOK("ansible-playbook"),
    ANSIBLE("ansible"),
    ANSIBLE_VAULT("ansible-vault")
}
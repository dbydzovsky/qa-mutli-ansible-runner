package cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth

class UsernamePasswordAuthentication constructor(var username: String, var password: String) : Authentication {
    constructor(): this("", "")
}
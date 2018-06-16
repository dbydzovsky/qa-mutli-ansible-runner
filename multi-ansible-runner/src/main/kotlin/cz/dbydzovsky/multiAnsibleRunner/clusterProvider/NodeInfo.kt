package cz.dbydzovsky.multiAnsibleRunner.clusterProvider

import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth.Authentication
import java.io.Serializable

class NodeInfo constructor(var authentication: Authentication?, var ip: String, var hostname: String): Serializable {
    constructor(): this(null, "", "")
}


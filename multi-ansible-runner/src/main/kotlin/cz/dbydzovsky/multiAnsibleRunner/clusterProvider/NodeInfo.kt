package cz.dbydzovsky.multiAnsibleRunner.clusterProvider

import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth.Authentication

class NodeInfo constructor(var authentication: Authentication?, var ip: String, var hostname: String)


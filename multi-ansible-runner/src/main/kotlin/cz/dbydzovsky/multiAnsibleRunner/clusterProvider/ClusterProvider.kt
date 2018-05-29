package cz.dbydzovsky.multiAnsibleRunner.clusterProvider

interface ClusterProvider {

    fun provide(): List<NodeInfo>?

    fun destroy()
}
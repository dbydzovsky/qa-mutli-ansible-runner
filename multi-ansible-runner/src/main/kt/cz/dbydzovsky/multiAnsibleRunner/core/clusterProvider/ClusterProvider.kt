package cz.dbydzovsky.multiAnsibleRunner.core.clusterProvider

interface ClusterProvider {

    fun provide(clusterEnvironment: ClusterEnvironment): List<NodeInfo>

}
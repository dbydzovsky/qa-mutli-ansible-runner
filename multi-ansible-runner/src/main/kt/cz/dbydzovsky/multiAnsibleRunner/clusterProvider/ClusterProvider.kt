package cz.dbydzovsky.multiAnsibleRunner.clusterProvider

interface ClusterProvider {

    fun provide(clusterEnvironment: ClusterEnvironment): List<NodeInfo>

}
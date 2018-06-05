package cz.dbydzovsky.multiAnsibleRunner.clusterProvider

/**
 * All methods must be idempotent according to its configuration
 */
interface ClusterProvider {

    fun provide(): List<NodeInfo>?

    fun destroy(params: List<String> = emptyList())
}
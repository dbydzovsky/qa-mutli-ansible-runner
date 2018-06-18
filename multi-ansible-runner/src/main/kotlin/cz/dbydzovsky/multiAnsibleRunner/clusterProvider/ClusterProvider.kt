package cz.dbydzovsky.multiAnsibleRunner.clusterProvider

/**
 * All methods must be idempotent according to its configuration
 */
interface ClusterProvider {

    fun provide(): List<NodeInfo>?

    fun getStatus(): List<NodeStatus>

    fun suspend(nodes: List<String>): List<Int>

    fun halt(nodes: List<String>): List<Int>

    fun destroy(nodes: List<String> = emptyList())
}
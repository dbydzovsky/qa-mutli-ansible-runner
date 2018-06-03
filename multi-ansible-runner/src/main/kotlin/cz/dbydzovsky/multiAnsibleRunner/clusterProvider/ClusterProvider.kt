package cz.dbydzovsky.multiAnsibleRunner.clusterProvider

interface ClusterProvider {

    fun provide(): List<NodeInfo>?

    fun destroy()

    /**
     * Cleans the folder with Vagrantfile
     */
    fun clean()
}
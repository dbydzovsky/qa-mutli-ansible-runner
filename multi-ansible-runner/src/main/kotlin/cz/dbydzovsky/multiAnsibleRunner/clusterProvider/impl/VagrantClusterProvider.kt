package cz.dbydzovsky.multiAnsibleRunner.clusterProvider.impl

import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.ClusterProvider
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.NodeInfo
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth.UsernamePasswordAuthentication
import cz.dbydzovsky.multiAnsibleRunner.tool.asResource
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import java.io.File

open class VagrantClusterProvider : ClusterProvider {

    protected open val vagrantfile: File = File("vagrant-cluster-provider/virtual-box/Vagrantfile".asResource(this).file)

    override fun provide(): List<NodeInfo> {

        val nodeCount = 3

        val envs = mutableMapOf(
                Pair("NODES_COUNT", "$nodeCount"),
                Pair("VM_MEMORY", "2048"),
                Pair("VM_CPUS", "2")
        )
        listOf("vagrant", "up", "--parallel").runCommand(vagrantfile.parentFile, envs)

        return (1..nodeCount).map {
            NodeInfo(UsernamePasswordAuthentication("vagrant", "vagrant"),
                    "172.17.11.${it + 100}",
                    "centos-0$it")
        }
    }

    override fun destroy() {
        listOf("vagrant", "destroy", "-f").runCommand(vagrantfile.parentFile)
    }
}
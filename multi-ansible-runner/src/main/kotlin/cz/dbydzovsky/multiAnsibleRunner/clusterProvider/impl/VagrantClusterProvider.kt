package cz.dbydzovsky.multiAnsibleRunner.clusterProvider.impl

import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.ClusterProvider
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.NodeInfo
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth.UsernamePasswordAuthentication
import cz.dbydzovsky.multiAnsibleRunner.tool.asResource
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import java.io.File

open class VagrantClusterProvider : ClusterProvider {

    open var vagrantfile: File = File("vagrant-cluster-provider/virtual-box/Vagrantfile".asResource(this).file)

    open var nodeNames = mutableListOf<String>()

//    open var private_key_path: String? = null
//    open var public_key_path: String? = null

//    open var ip_template = "172.17.11.#{i+100}"
    open var ip_template = "192.168.99.#{i+100}"

    override fun provide(): List<NodeInfo> {

        val envs = mutableMapOf(
                Pair("NODES_COUNT", "${nodeNames.size}"),
                Pair("NODE_NAMES", nodeNames.joinToString(",")),
                Pair("VM_MEMORY", "1028"),
                Pair("VM_CPUS", "2"),
//                Pair("PRIVATE_KEY_PATH", private_key_path ?: ""),
//                Pair("PUBLIC_KEY_PATH", public_key_path ?: ""),
                Pair("IP_TEMPLATE", ip_template)
        )
        listOf("vagrant", "up", "--parallel").runCommand(vagrantfile.parentFile, envs)

        return (1..nodeNames.size).map {
            NodeInfo(UsernamePasswordAuthentication("vagrant", "vagrant"),
                    "192.168.99.${it + 100}",
                    nodeNames[it-1])
        }
    }

    override fun destroy() {
        listOf("vagrant", "destroy", "-f").runCommand(vagrantfile.parentFile)
    }

}
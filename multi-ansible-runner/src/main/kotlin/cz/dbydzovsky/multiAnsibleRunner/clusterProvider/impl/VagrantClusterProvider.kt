package cz.dbydzovsky.multiAnsibleRunner.clusterProvider.impl

import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.ClusterProvider
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.NodeInfo
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth.UsernamePasswordAuthentication
import cz.dbydzovsky.multiAnsibleRunner.tool.asResource
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import java.io.File

open class VagrantClusterProvider(private val origVagrantfile: File? = null) : ClusterProvider {

    open var nodeNames = mutableListOf<String>()

//    open var private_key_path: String? = null
//    open var public_key_path: String? = null

    //    open var ip_template = "172.17.11.#{i+100}"
    // when running ansible in docker toolbox - virtuals must be in the same network
    open var ip_template = "192.168.99.#{i+100}"
    open var vm_memory = "1028"
    open var vm_cpus = "1"

    open var parallel = true
    /**
     * vmware_desktop, virtualbox, docker, hyperv
     */
    open var provider = "virtualbox"
    open var additionalParams = mutableListOf<String>()

    lateinit var vagrantfile: File

    init {
        if (origVagrantfile == null) {
            recreateVagrantfile()
        } else {
            this.vagrantfile = origVagrantfile
        }
    }

    override fun provide(): List<NodeInfo> {
        validate()
        val envs = mutableMapOf(
                Pair("NODES_COUNT", "${nodeNames.size}"),
                Pair("NODE_NAMES", nodeNames.joinToString(",")),
                Pair("VM_MEMORY", vm_memory),
                Pair("VM_CPUS", vm_cpus),
//                Pair("PRIVATE_KEY_PATH", private_key_path ?: ""),
//                Pair("PUBLIC_KEY_PATH", public_key_path ?: ""),
                Pair("IP_TEMPLATE", ip_template)
        )
        val commands = mutableListOf("vagrant", "up")

        if (parallel) {
            commands.add("--parallel")
        }
        commands.add("--provider")
        commands.add(provider)

        commands.addAll(additionalParams)

        commands.runCommand(vagrantfile.parentFile, envs)
        return (1..nodeNames.size).map {
            NodeInfo(UsernamePasswordAuthentication("vagrant", "vagrant"),
                    "192.168.99.${it + 100}",
                    nodeNames[it - 1])
        }
    }

    override fun destroy(params: List<String>) {
        listOf("vagrant", "destroy", "-f").plus(params).runCommand(vagrantfile.parentFile)
    }

    fun reset() {
        vagrantfile.parentFile.deleteRecursively()
        recreateVagrantfile()
    }

    private fun recreateVagrantfile() {
        this.vagrantfile = origVagrantfile ?: File(System.getProperty("java.io.tmpdir") + "/cz.dbydzovsky.multiAnsibleRunner.clusterProvider.36d72e98-e393/Milky-way/Vagrantfile")
        this.vagrantfile.parentFile.mkdirs()
        "vagrant-cluster-provider/virtual-box/Vagrantfile".asResource(this).openStream().copyTo(this.vagrantfile.outputStream())
    }

    private fun validate() {
        if (nodeNames.isEmpty()) {
            IllegalStateException("There is no nodeName set. There is nothing to configure in vagrant")
        }
    }

    fun addNodeName(nodeName: String): VagrantClusterProvider {
        this.nodeNames.add(nodeName)
        return this
    }

    fun addNodeNames(nodeNames: List<String>): VagrantClusterProvider {
        this.nodeNames.addAll(nodeNames)
        return this
    }


    fun setProvider(provider: String): VagrantClusterProvider {
        this.provider = provider
        return this
    }

    fun setIpTemplate(template: String): VagrantClusterProvider {
        this.ip_template = template
        return this
    }

    fun setVmMemory(memory: String): VagrantClusterProvider {
        this.vm_memory = memory
        return this
    }

    fun setVmCpus(CPUs: String): VagrantClusterProvider {
        this.vm_cpus = CPUs
        return this
    }

    fun addAdditionalParam(param: String): VagrantClusterProvider {
        this.additionalParams.add(param)
        return this
    }

    fun addAdditionalParams(params: List<String>): VagrantClusterProvider {
        this.additionalParams.addAll(params)
        return this
    }
}
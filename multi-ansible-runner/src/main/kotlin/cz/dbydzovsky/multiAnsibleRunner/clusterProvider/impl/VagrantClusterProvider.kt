package cz.dbydzovsky.multiAnsibleRunner.clusterProvider.impl

import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.ClusterProvider
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.NodeInfo
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth.UsernamePasswordAuthentication
import cz.dbydzovsky.multiAnsibleRunner.tool.asResource
import cz.dbydzovsky.multiAnsibleRunner.tool.runCommand
import org.junit.platform.commons.logging.LoggerFactory
import java.io.File

open class VagrantClusterProvider(
        private val workingDir: File = File(System.getProperty("java.io.tmpdir") + "/" + defaultUniqueStorage),
        vagrantfile: File? = null
) : ClusterProvider {

    companion object {
        private val defaultUniqueStorage = "clusterProvider.36d72e98-e393/cluster"
    }

    open var nodeNames = mutableListOf<String>()

//    open var private_key_path: String? = null
//    open var public_key_path: String? = null

    //    open var ip_template = "172.17.11.#{i+100}"
    // when running ansible in docker toolbox - virtuals must be in the same network
    open var ip_addresses: List<String>? = null
    open var starting_ip_address: String = "192.168.99.101"

    open var vm_memory = "1028"
    open var vm_cpus = "1"
    open var vm_box = "centos/7"

//    open var sharedFolder: String? = null
//    open var targetSharedFolder = "/shared"

    open var parallel = true
    /**
     * vmware_desktop, virtualbox, docker, hyperv
     */
    open var provider = "virtualbox"
    open var additionalParams = mutableListOf<String>()

    val vagrantfile: File

    init {
        workingDir.mkdirs()
        this.vagrantfile = File("$workingDir/Vagrantfile")
        if (vagrantfile == null) {
            "vagrant-cluster-provider/virtual-box/Vagrantfile".asResource(this).openStream().copyTo(this.vagrantfile.outputStream())
        } else {
            vagrantfile.copyTo(this.vagrantfile, true)
        }
    }

    override fun provide(): List<NodeInfo> {
        validate()

        val commands = mutableListOf("vagrant", "up")

        if (parallel) {
            commands.add("--parallel")
        }
        commands.addAll(getProvider())

        commands.addAll(additionalParams)

        val ipAddresses = createIpAddresses()

        commands.runCommand(vagrantfile.parentFile, getEnvironmentForVagrantfile(ipAddresses))
        return (1..nodeNames.size).map {
            NodeInfo(UsernamePasswordAuthentication("vagrant", "vagrant"),
                    ipAddresses[it - 1],
                    nodeNames[it - 1])
        }
    }

    /**
     * Launches command "vagrant <params>" in folder with given vagrantfile and with set environment.
     */
    fun execute(params: List<String>): Int {
        return listOf("vagrant").plus(params).plus(getProvider()).runCommand(vagrantfile.parentFile, getEnvironmentForVagrantfile(createIpAddresses()))
    }

    /**
     * Call "vagrant destroy -f" on vagrant file with given environment
     */
    override fun destroy(params: List<String>) {
        listOf("vagrant", "destroy", "-f").plus(params).plus(getProvider()).runCommand(vagrantfile.parentFile, getEnvironmentForVagrantfile(createIpAddresses()))
    }

    private fun getProvider(): List<String> {
        return listOf("--provider", provider)
    }

    private fun getEnvironmentForVagrantfile(ipAddresses: List<String>): MutableMap<String, String> {
//        if (sharedFolder == null) {
//            sharedFolder = vagrantfile.parentFile.canonicalPath
//        }
        return mutableMapOf(
                Pair("NODES_COUNT", "${nodeNames.size}"),
                Pair("NODE_NAMES", nodeNames.joinToString(",")),
                Pair("VM_MEMORY", vm_memory),
                Pair("VM_CPUS", vm_cpus),
                Pair("VM_BOX", vm_box),
                Pair("IP_ADDRESSES", ipAddresses.joinToString(","))
//                Pair("SHARED_FOLDER", sharedFolder!!),
//                Pair("TARGET_SHARED_FOLDER", targetSharedFolder)
//                Pair("PRIVATE_KEY_PATH", private_key_path ?: ""),
//                Pair("PUBLIC_KEY_PATH", public_key_path ?: ""),
        )
    }

    private fun createIpAddresses(): List<String> {
        if (ip_addresses == null) {
            val parts = starting_ip_address.split(".").map { it.toInt() }
            if (!isIpAddress(parts)) {
                throw IllegalStateException("$ip_addresses is not valid IP4 address.")
            }
            return (0..(nodeNames.size-1)).map {index ->
                "${parts[0]}.${parts[1]}.${parts[2]}.${parts[3] + index}"
            }
        } else {
            if (nodeNames.size > ip_addresses!!.size) {
                throw IllegalStateException("You provided ${ip_addresses!!.size} ip addresses, but expected ${nodeNames.size} according to count of nodes you want to provide.\nDelete ip_addresses field and the ip addresses will be determined automatically.")
            }
            return ip_addresses!!
        }
    }

    private fun isIpAddress(parts: List<Int>): Boolean {
        return parts.size == 4 && !parts.any { it < 0 || it > 255 }
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

//    fun setIpTemplate(template: String): VagrantClusterProvider {
//        this.ip_template = template
//        return this
//    }

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
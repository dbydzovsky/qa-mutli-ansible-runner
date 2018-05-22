package cz.dbydzovsky.multiAnsibleRunner.core.configuration

import cz.dbydzovsky.multiAnsibleRunner.core.ansible.auth.AnsibleAuthentication
import cz.dbydzovsky.multiAnsibleRunner.core.ansible.obj.AnsibleRun
import cz.dbydzovsky.multiAnsibleRunner.core.ansible.runner.AnsibleRunner
import cz.dbydzovsky.multiAnsibleRunner.core.clusterProvider.ClusterEnvironment
import cz.dbydzovsky.multiAnsibleRunner.core.clusterProvider.ClusterProvider
import cz.dbydzovsky.multiAnsibleRunner.core.clusterProvider.NodeInfo

typealias AnsiblePlaybookUpdate = (AnsibleRun, List<NodeInfo>) -> AnsibleRun

interface Configuration {

    val ansibleRunner: AnsibleRunner
    val ansibleRun: List<AnsibleRun>
    val clusterProvider: ClusterProvider
    val clusterEnvironment: ClusterEnvironment

    val registered: Map<Event, AnsiblePlaybookUpdate>

    /**
     * This function is called when environment is prepared and you need to update your ansible run playbooks
     */
    fun register(update: AnsiblePlaybookUpdate)

    /**
     * Add ansible run which should be ran againts environment. It follows the order of runs.
     */
    fun addAnsibleRun(ansibleRun: AnsibleRun): Configuration

    /**
     * Add all ansible run you wish to run againts your environment.
     */
    fun addAnsibleRuns(ansibleRun: List<AnsibleRun>): Configuration

    /**
     * Choose your AnsibleRunner (native, in docker..). If not set, it will be chosen automatically
     */
    fun setAnsibleRunner(ansibleRunner: AnsibleRunner): Configuration

    /**
     * Set cluster provider - localsystem, physical computers..
     */
    fun setClusterProvider(clusterProvider: ClusterProvider): Configuration

    /**
     * Set cluster environment which will be passed to ClusterProvider
     */
    fun setClusterEnvironment(clusterEnvironment: ClusterEnvironment): Configuration

    /**
     * Set authenticator of all ansible playbooks. Use if optional.
     */
    fun setAnsiblePlaybookAuthentication(ansibleAuthentication: AnsibleAuthentication): Configuration

}
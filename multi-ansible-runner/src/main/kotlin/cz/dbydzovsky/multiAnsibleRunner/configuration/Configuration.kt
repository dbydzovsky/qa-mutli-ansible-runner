package cz.dbydzovsky.multiAnsibleRunner.configuration

import cz.dbydzovsky.multiAnsibleRunner.ansible.auth.AnsibleAuthentication
import cz.dbydzovsky.multiAnsibleRunner.ansible.run.IAnsibleRun
import cz.dbydzovsky.multiAnsibleRunner.ansible.runner.AnsibleRunner
import cz.dbydzovsky.multiAnsibleRunner.ansible.runner.DefaultAnsibleRunnerFactory
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.ClusterProvider
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.NodeInfo

class Configuration {

    var ansibleRunner: AnsibleRunner = DefaultAnsibleRunnerFactory().instance
    var clusterProvider: ClusterProvider? = null
    var ansibleAuthentication: AnsibleAuthentication? = null

    val registered: MutableMap<Event, (IAnsibleRun, List<NodeInfo>?) -> IAnsibleRun> = mutableMapOf()
    val ansibleRuns: MutableList<IAnsibleRun> = mutableListOf()

    /**
     * Set cluster provider - localsystem, physical computers..
     */
    fun setClusterProvider(clusterProvider: ClusterProvider): Configuration {
        this.clusterProvider = clusterProvider
        return this
    }

    /**
     * This function is called when environment is prepared and you need to update your ansible run playbooks
     */
    fun register(update: (IAnsibleRun, List<NodeInfo>?) -> IAnsibleRun): Configuration {
        this.registered[Event.AnsiblePlaybookUpdate] = update
        return this
    }

    /**
     * Add ansible run which should be ran againts environment. It follows the order of runs.
     */
    fun addAnsibleRun(ansibleRun: IAnsibleRun): Configuration {
        this.ansibleRuns.add(ansibleRun)
        return this
    }

    /**
     * Add all ansible run you wish to run againts your environment.
     */
    fun addAnsibleRuns(ansibleRuns: List<IAnsibleRun>): Configuration {
        this.ansibleRuns.addAll(ansibleRuns)
        return this
    }

    /**
     * Choose your AnsibleRunner (native, in docker..). If not set, it will be chosen automatically
     */
    fun setAnsibleRunner(ansibleRunner: AnsibleRunner): Configuration {
        this.ansibleRunner = ansibleRunner
        return this
    }

    /**
     * Set authenticator of all ansible playbooks. Use if optional.
     */
    fun setAnsiblePlaybookAuthentication(ansibleAuthentication: AnsibleAuthentication): Configuration {
        this.ansibleAuthentication = ansibleAuthentication
        return this
    }
}

enum class Event {
    AnsiblePlaybookUpdate
}
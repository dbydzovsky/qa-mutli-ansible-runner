package cz.dbydzovsky.multiAnsibleRunner.application

import cz.dbydzovsky.multiAnsibleRunner.configuration.Configuration
import cz.dbydzovsky.multiAnsibleRunner.configuration.Event
import cz.dbydzovsky.multiAnsibleRunner.validation.ConfigurationValidation

class MultiAnsibleRunner : Processor {

    private val validation: ConfigurationValidation = ConfigurationValidation()

    override fun run(configuration: Configuration) {

        validation.validate(configuration)

        val nodeInfos = configuration.clusterProvider?.provide()

        /**
         * Call through registered functions, which should update ansibleRun according to prepared environment
         */
        configuration.ansibleRuns.forEach { ansibleRun ->
            configuration.registered[Event.AnsiblePlaybookUpdate]?.invoke(ansibleRun, nodeInfos)
        }

        /**
         * Run all playbooks againts all nodes
         */
        configuration.ansibleRuns.forEach {
            configuration.ansibleRunner.run(it)
        }
    }

}
package cz.dbydzovsky.multiAnsibleRunner.application

import cz.dbydzovsky.multiAnsibleRunner.configuration.Configuration
import cz.dbydzovsky.multiAnsibleRunner.configuration.Event
import cz.dbydzovsky.multiAnsibleRunner.validation.ConfigurationValidation
import cz.dbydzovsky.multiAnsibleRunner.validation.ConfigurationValidationImpl

class DefaultProcessor(private val validation: ConfigurationValidation = ConfigurationValidationImpl()): Processor {

    override fun run(configuration: Configuration) {

        validation.validate(configuration)

        val nodeInfos = configuration.clusterProvider.provide(configuration.clusterEnvironment)

        /**
         * Call through registered functions, which should update ansibleRun according to prepared environment
         */
        configuration.ansibleRun.forEach {
            configuration.registered[Event.AnsiblePlaybookUpdate]?.invoke(it, nodeInfos)
        }

        /**
         * Run all playbooks againts all nodes
         */
        configuration.ansibleRun.forEach {
            configuration.ansibleRunner.run(it)
        }
    }

}
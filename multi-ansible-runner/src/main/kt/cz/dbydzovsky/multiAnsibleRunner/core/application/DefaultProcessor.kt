package cz.dbydzovsky.multiAnsibleRunner.core.application

import cz.dbydzovsky.multiAnsibleRunner.core.configuration.Configuration
import cz.dbydzovsky.multiAnsibleRunner.core.configuration.Event
import cz.dbydzovsky.multiAnsibleRunner.core.validation.ConfigurationValidation
import cz.dbydzovsky.multiAnsibleRunner.core.validation.ConfigurationValidationImpl

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
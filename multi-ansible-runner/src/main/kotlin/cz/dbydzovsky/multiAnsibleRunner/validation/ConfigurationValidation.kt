package cz.dbydzovsky.multiAnsibleRunner.validation

import cz.dbydzovsky.multiAnsibleRunner.configuration.Configuration

class ConfigurationValidation {

    /**
     * throws InvalidArgumentException if invalid
     */
    fun validate(configuration: Configuration) {
        if (configuration.ansibleRuns.isEmpty()) {
            throw IllegalArgumentException("No ansible run was set to be ran.")
        }
    }

}
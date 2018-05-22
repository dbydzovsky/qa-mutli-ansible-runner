package cz.dbydzovsky.multiAnsibleRunner.validation

import cz.dbydzovsky.multiAnsibleRunner.configuration.Configuration

interface ConfigurationValidation {

    /**
     * throws InvalidArgumentException if invalid
     */
    fun validate(configuration: Configuration)

}
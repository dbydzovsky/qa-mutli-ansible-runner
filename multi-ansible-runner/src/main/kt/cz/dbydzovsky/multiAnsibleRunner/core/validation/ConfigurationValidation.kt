package cz.dbydzovsky.multiAnsibleRunner.core.validation

import cz.dbydzovsky.multiAnsibleRunner.core.configuration.Configuration

interface ConfigurationValidation {

    /**
     * throws InvalidArgumentException if invalid
     */
    fun validate(configuration: Configuration)

}
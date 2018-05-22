package cz.dbydzovsky.multiAnsibleRunner.core.application

import cz.dbydzovsky.multiAnsibleRunner.core.configuration.Configuration

interface Processor {

    fun run(configuration: Configuration)

}
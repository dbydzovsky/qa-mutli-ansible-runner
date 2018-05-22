package cz.dbydzovsky.multiAnsibleRunner.application

import cz.dbydzovsky.multiAnsibleRunner.configuration.Configuration

interface Processor {

    fun run(configuration: Configuration)

}
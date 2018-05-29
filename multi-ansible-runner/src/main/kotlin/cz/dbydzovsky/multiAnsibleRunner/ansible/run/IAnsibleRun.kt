package cz.dbydzovsky.multiAnsibleRunner.ansible.run

import java.io.File

interface IAnsibleRun {

    var workingDir: File?

    fun toCommand(): List<String>
}
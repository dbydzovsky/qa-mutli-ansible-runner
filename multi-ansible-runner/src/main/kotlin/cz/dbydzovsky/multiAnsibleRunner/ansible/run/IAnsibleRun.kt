package cz.dbydzovsky.multiAnsibleRunner.ansible.run

import java.io.File
import java.io.Serializable

interface IAnsibleRun : Serializable {

    var workingDir: File?

    fun toCommand(): List<String>
}
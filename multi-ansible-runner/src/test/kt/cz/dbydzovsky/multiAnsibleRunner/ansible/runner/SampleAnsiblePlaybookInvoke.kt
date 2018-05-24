package cz.dbydzovsky.multiAnsibleRunner.ansible.runner
import cz.dbydzovsky.multiAnsibleRunner.ansible.run.playbook.AnsiblePlaybookRunBuilder
import cz.dbydzovsky.multiAnsibleRunner.application.MultiAnsibleRunner
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.ClusterProvider
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.NodeInfo
import cz.dbydzovsky.multiAnsibleRunner.configuration.Configuration
import cz.dbydzovsky.multiAnsibleRunner.tool.asResource
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File

@ExtendWith(MockitoExtension::class)
internal class SampleAnsiblePlaybookInvoke {

    val playbookName = "playbook-ping.yml"
    val pingPlaybook = "playbook_ping/$playbookName".asResource(this)


    @Test
    fun `run ansible playbook`() {
        val conf = Configuration()

        val playbookBuilder = AnsiblePlaybookRunBuilder()
                .withPlaybook(playbookName)
                .withWorkingDir(File(pingPlaybook.path).parentFile)

        conf.addAnsibleRun(playbookBuilder.build())

        MultiAnsibleRunner().run(conf)

    }

}

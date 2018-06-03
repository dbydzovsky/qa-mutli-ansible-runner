package cz.dbydzovsky.multiAnsibleRunner.ansible.runner
import cz.dbydzovsky.multiAnsibleRunner.ansible.run.playbook.AnsiblePlaybookRunBuilder
import cz.dbydzovsky.multiAnsibleRunner.application.MultiAnsibleRunner
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.auth.UsernamePasswordAuthentication
import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.impl.VagrantClusterProvider
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
        val provider = VagrantClusterProvider()
        provider.nodeNames = mutableListOf("Earth")

        conf.setClusterProvider(provider)
        conf.addAnsibleRun(playbookBuilder.build())
        MultiAnsibleRunner().run(conf)
    }



    @Test
    fun `run ansible playbook in vagrant virtuals`() {
        val hosts = File.createTempFile("hosts", ".file")
        val conf = Configuration()

        val playbookBuilder = AnsiblePlaybookRunBuilder()
                .withPlaybook(playbookName)
                .withHosts("inventories/hosts.file")
                .withWorkingDir(File(pingPlaybook.path).parentFile)

        conf.addAnsibleRun(playbookBuilder.build())

        val provider = VagrantClusterProvider()
        provider.nodeNames.add("Mercury")
        provider.nodeNames.add("Venus")
        provider.nodeNames.add("Earth")
//        provider.nodeNames.add("Mars")
//        provider.nodeNames.add("Mars")
        conf.setClusterProvider(provider)

        conf.register { ansibleRun, info ->
            hosts.appendText("[all]\n")
            info?.forEach {
                    val authentication = it.authentication as UsernamePasswordAuthentication
                    hosts.appendText("${it.hostname}\tansible_host=${it.ip}\tansible_connection=ssh\tansible_user=${authentication.username}\tansible_password=${authentication.password}\n")
                }
            return@register ansibleRun
        }

        val ansibleRunner = AnsibleInDockerRunner()
        ansibleRunner.setPlaybookPath(File(pingPlaybook.path).parentFile.canonicalPath)
        ansibleRunner.addSharedFolder(hosts.canonicalPath, "/ansible/playbooks/inventories/hosts.file")
        conf.setAnsibleRunner(ansibleRunner)
        MultiAnsibleRunner().run(conf)
    }

    @Test
    fun `destroy with same parameters as creation`() {
        val provider = VagrantClusterProvider()
        provider.nodeNames.add("Earth")
        provider.nodeNames.add("Mars")
        provider.nodeNames.add("Venus")
        provider.destroy()
    }
}

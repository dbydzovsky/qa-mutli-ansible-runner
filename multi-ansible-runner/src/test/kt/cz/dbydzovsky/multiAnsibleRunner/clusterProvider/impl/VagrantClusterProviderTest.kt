package cz.dbydzovsky.multiAnsibleRunner.clusterProvider.impl

import cz.dbydzovsky.multiAnsibleRunner.clusterProvider.ClusterProvider
import name.falgout.jeffrey.testing.junit.mockito.MockitoExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MockitoExtension::class)
internal class VagrantClusterProviderTest {

    @Test
    fun `Provide and destroy instances`() {
        val provider: ClusterProvider = VagrantClusterProvider()

        val infos = provider.provide()
        infos?.isEmpty()

        provider.destroy()
    }

}
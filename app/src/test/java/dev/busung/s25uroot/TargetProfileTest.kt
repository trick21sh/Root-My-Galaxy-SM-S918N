package dev.busung.s25uroot

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TargetProfileTest {
    private val profile = TargetProfile(
        profileId = "galaxy-s25-series-kernel-6.6.98",
        displayName = "Galaxy S25 series",
        models = setOf("SM-S931B", "SM-S938N"),
        kernelVersions = setOf("6.6.98"),
        exploit = RemoteArtifact("https://example.invalid/exploit", 1),
        kernelSu = RemoteArtifact("https://example.invalid/ksud", 1),
        helper = RemoteArtifact("https://example.invalid/helper", 1),
    )

    @Test
    fun matchesRegionalS25OnSameKernelVersion() {
        assertTrue(profile.matches(snapshot("SM-S931B", "6.6.98-android15-8-build-a")))
        assertTrue(profile.matches(snapshot("SM-S938N", "6.6.98-android15-8-build-b")))
    }

    @Test
    fun rejectsUnlistedModelOrKernelVersion() {
        assertFalse(profile.matches(snapshot("SM-S928B", "6.6.98-android15-8-build")))
        assertFalse(profile.matches(snapshot("SM-S938N", "6.6.102-android15-8-build")))
    }

    @Test
    fun s918nProfileOnlyMatchesItsExactFirmware() {
        val s918n = TargetProfile(
            profileId = "dm3q-S918NKSS8FZG1-ksunext",
            displayName = "Galaxy S23 Ultra SM-S918N",
            models = setOf("SM-S918N"),
            kernelVersions = setOf("5.15.189"),
            exploit = RemoteArtifact("asset://exploit", 1),
            kernelSu = RemoteArtifact("asset://ksud", 1),
            helper = RemoteArtifact("asset://helper", 1),
            buildDisplays = setOf("BP4A.251205.006.S918NKSS8FZG1"),
            fingerprints = setOf(
                "samsung/dm3qksx/dm3q:16/BP4A.251205.006/S918NKSS8FZG1:user/release-keys",
            ),
            kernelReleases = setOf("5.15.189-android13-8-33413713-abS918NKSS8FZG1"),
        )
        val exact = snapshot(
            model = "SM-S918N",
            kernelRelease = "5.15.189-android13-8-33413713-abS918NKSS8FZG1",
            buildId = "BP4A.251205.006.S918NKSS8FZG1",
            fingerprint = "samsung/dm3qksx/dm3q:16/BP4A.251205.006/S918NKSS8FZG1:user/release-keys",
        )

        assertTrue(s918n.matches(exact))
        assertFalse(s918n.matches(exact.copy(buildId = "BP4A.251205.006.S918NKSS8FZH3")))
        assertFalse(s918n.matches(exact.copy(model = "SM-S918B")))
    }

    @Test
    fun s918nFzh2ProfileMatchesConnectedDeviceSnapshot() {
        val profile = TargetProfile(
            profileId = "dm3q-S918NKSS8FZH2-ksunext",
            displayName = "Galaxy S23 Ultra SM-S918N FZH2",
            models = setOf("SM-S918N"),
            kernelVersions = setOf("5.15.189"),
            exploit = RemoteArtifact("asset://exploit", 131072),
            kernelSu = RemoteArtifact("asset://ksud", 6756208),
            helper = RemoteArtifact("asset://helper", 29392),
            buildDisplays = setOf("BP4A.251205.006.S918NKSS8FZH2"),
            fingerprints = setOf(
                "samsung/dm3qksx/dm3q:16/BP4A.251205.006/S918NKSS8FZH2:user/release-keys",
            ),
            kernelReleases = setOf("5.15.189-android13-8-33413713-abS918NKSS8FZH2"),
            kernelVersionInfos = setOf("#1 SMP PREEMPT Tue Aug 11 06:15:40 UTC 2026"),
        )
        val connectedDevice = snapshot(
            model = "SM-S918N",
            kernelRelease = "5.15.189-android13-8-33413713-abS918NKSS8FZH2",
            buildId = "BP4A.251205.006.S918NKSS8FZH2",
            fingerprint = "samsung/dm3qksx/dm3q:16/BP4A.251205.006/S918NKSS8FZH2:user/release-keys",
            kernelVersionInfo = "#1 SMP PREEMPT Tue Aug 11 06:15:40 UTC 2026",
        )

        assertTrue(profile.matches(connectedDevice))
        assertFalse(profile.matches(connectedDevice.copy(kernelVersionInfo = "different build")))
    }

    private fun snapshot(
        model: String,
        kernelRelease: String,
        buildId: String = "BP4A.251205.006.S938BCZG1",
        fingerprint: String = "samsung/example",
        kernelVersionInfo: String = "",
    ) = DeviceSnapshot(
        manufacturer = "samsung",
        model = model,
        device = "unused",
        kernelRelease = kernelRelease,
        kernelVersionInfo = kernelVersionInfo,
        machine = "aarch64",
        buildId = buildId,
        fingerprint = fingerprint,
        androidRelease = "16",
        sdk = 36,
        abi = "arm64-v8a",
        pageSize = 4096,
    )
}

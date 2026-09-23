# KernelSU SM-S918N FZH2

The bundled `ksud-kernelsu-s918n-fzh2` is built from official KernelSU
v3.2.5 and embeds the Samsung-targeted `android13-5.15_kernelsu.ko` module.
It is tied to the exact FZH2 kernel release:

```text
5.15.189-android13-8-33413713-abS918NKSS8FZH2
```

The APK invokes:

```text
ksud late-load --kmi android13-5.15 --package-name me.weishu.kernelsu
```

The Manager package and activation check both use the official KernelSU
contract. No other device or regional profile is included here.

The patch reference is retained at
`patches/KernelSU-v3.2.5-samsung-kdp-rkp-defex.patch`. The first real FZH2
exploit attempt rebooted the connected phone, so this artifact is not claimed
as hardware-validated.

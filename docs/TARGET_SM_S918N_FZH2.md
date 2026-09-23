# SM-S918N FZH2 Port

This profile was generated from Samsung firmware
`S918NKSS8FZH2` and checked against the connected `SM-S918N`.

## Exact Target

```text
model: SM-S918N
device: dm3q
build display: BP4A.251205.006.S918NKSS8FZH2
fingerprint: samsung/dm3qksx/dm3q:16/BP4A.251205.006/S918NKSS8FZH2:user/release-keys
kernel release: 5.15.189-android13-8-33413713-abS918NKSS8FZH2
kernel build: #1 SMP PREEMPT Tue Aug 11 06:15:40 UTC 2026
Android SDK: 36
page size: 4096
```

The app and native helper reject any mismatch before loading the payload.

## Recovered Offsets

The firmware `boot.img` exposed 126,220 kallsyms entries. The three values
embedded in the closed payload differ from the FZG1 baseline:

```text
ashmem_fops:      0x0200d1b8
anon_pipe_buf_ops: 0x01e7f160
kmalloc_caches:   0x02064178
```

The reproducible patch is in
`tools/f731u-to-dm3q-s918n-fzh2.spec.json`.

## Artifacts

```text
01A4FFCE3CC0570C3CA3B363E7D772D762335A311FC2C5C45CD509BDE74DD47B  cve-2026-43499-app-s918n-fzh2.so
52123F612890D688C9AD935B8F4114A64C1DC63A157E225592FD7BAB007298F5  libcve43499root-s918n-fzh2.so
11329C52ADF28130D75290BD095FB6831C0682F85817534A531C773933AEFE8E  ksud-f731u-kdp-s918n-fzg1
```

## Validation Performed

- Android APK assembled successfully.
- APK installed and launched on the connected device without a Java crash.
- The FZH2 native helper returned
  `target profile=dm3q-S918NKSS8FZH2 validated` on the connected device.
- The exploit and KernelSU late-load were intentionally not executed during
  safe validation because a kernel panic or reboot cannot be ruled out.

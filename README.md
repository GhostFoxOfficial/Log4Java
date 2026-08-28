<H1 align="center">Log4Java</H1>

<a href="./README_RU.md">README на русском</a>

<img src="./app_pojavlauncher/src/main/assets/pojavlauncher.png" align="left" width="150" height="150" alt="MojoLauncher logo">

[![Android CI](https://github.com/MutantBloodcell/Log4Java/workflows/Android%20CI/badge.svg)](https://github.com/MutantBloodcell/Log4Java/actions)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/MutantBloodcell/Log4Java)](https://github.com/MutantBloodcell/Log4Java/actions)
[![Discord](https://img.shields.io/discord/1365346109131722753.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/dCQ2cuAn7z)

* Log4Java is a launcher, based on [MojoLauncher](https://github.com/MojoLauncher/MojoLauncher), that allows you to play Minecraft: Java Edition on your Android device!

* It can run every version of Minecraft, allowing you to use .jar only installers to install modloaders such as [Forge](https://files.minecraftforge.net/) and [Fabric](http://fabricmc.net/) and mods like [OptiFine](https://optifine.net).

## Navigation
- [Introduction](#introduction)
- [Getting Log4Java](#getting-mojolauncher)
- [Building](#building) 
- [Current roadmap](#current-roadmap) 
- [License](#license) 
- [Contributing](#contributing) 
- [Credits & Third party components and their licenses](#credits--third-party-components-and-their-licenses-if-available)

## Introduction 
* Log4Java is a Minecraft: Java Edition launcher for Android based on [MojoLauncher](https://github.com/MojoLauncher/MojoLauncher)
* This launcher can launch all available Minecraft versions ranging from rd-132211 to 26.x snapshots (including Combat Test versions). 
* Modding via Forge and Fabric are also supported. 

## Getting Log4Java

You can get Log4Java via four methods:

1. You can get the prebuilt app from the [releases section](http://github.com/MutantBloodcell/Log4Java/releases).

2. You can get early builds from [Github Actions](http://github.com/MutantBloodcell/Log4Java/actions).

4. You can [build](#building) from source.
## Building   
* Build the launcher (it will automatically download all required components)
```
./gradlew :app_pojavlauncher:assembleDebug
```
(Replace `./gradlew` with `.\gradlew.bat` if you are building on Windows).

## Current roadmap
- [x] Instance system in favor of profiles
- [x] Out-of-the box 1.21.5 support
- [x] mrpack/CurseForge zip import
- [ ] LTW: resolve issues with Create
- [ ] LTW: enable compute shader/image extensions
- [ ] LTW: switch to a color-renderable format for framebuffers
- [ ] Modpack/mod management tool
- [ ] MMC-compatible instance import
- [ ] Implement common native library standard

## Known Issues
- Some physical mice may have very slow mouse speed
- On Holy GL4ES, large texture atlases may be distorted (resulting in stretched/blocky textures in modpacks)
- Probably more, that's why we have a bug tracker ;) 

## License
- Log4Java is licensed under [GNU LGPLv3](https://github.com/MojoLauncher/MojoLauncher/blob/v3_openjdk/LICENSE).

## Contributing
Contributions are welcome! We welcome any type of contribution, not only code. For example, you can help the wiki shape up. You can help the [translation](https://crowdin.com/project/pojavlauncher) too!


Any code change to this repository should be submitted as a pull request. The description should explain what the code does and give steps to execute it.

## Third party components, licenses and sources (when applicable)
- [PojavLauncher](https://github.com/PojavLauncherTeam/PojavLauncher): [GNU LGPLv3 License](https://github.com/PojavLauncherTeam/PojavLauncher/blob/v3_openjdk/LICENSE)
- [Boardwalk](https://github.com/zhuowei/Boardwalk) (JVM Launcher): Unknown License/[Apache License 2.0](https://github.com/zhuowei/Boardwalk/blob/master/LICENSE) or GNU GPLv2.
- Android Support Libraries: [Apache License 2.0](https://android.googlesource.com/platform/prebuilts/maven_repo/android/+/master/NOTICE.txt).
- [Holy GL4ES](https://github.com/artdeell/gl4es_extra_extra/): [MIT License](https://github.com/ptitSeb/gl4es/blob/master/LICENSE).<br>
- [OpenJDK](https://github.com/PojavLauncherTeam/openjdk-multiarch-jdk8u): [GNU GPLv2 License](https://openjdk.java.net/legal/gplv2+ce.html).<br>
- [GLFW](https://github.com/MojoLauncher/glfw): [zlib license](https://github.com/MojoLauncher/glfw/blob/glfw34/LICENSE.md)
- [LWJGL2-GLFW](https://github.com/MojoLauncher/lwjgl2-glfw): 3-Clause BSD license
- [LWJGL3](https://github.com/LWJGL/lwjgl3): [BSD-3 License](https://github.com/LWJGL/lwjgl3/blob/master/LICENSE.md).
- [Mesa 3D Graphics Library](https://gitlab.freedesktop.org/mesa/mesa): [MIT License](https://docs.mesa3d.org/license.html).
- [pro-grade](https://github.com/pro-grade/pro-grade) (Java sandboxing security manager): [Apache License 2.0](https://github.com/pro-grade/pro-grade/blob/master/LICENSE.txt).
- [bhook](https://github.com/bytedance/bhook) (Used for exit code trapping): [MIT license](https://github.com/bytedance/bhook/blob/main/LICENSE).
- [Authlib-Injector](https://github.com/yushijinhun/authlib-injector) (Used for authorisation via ely.by): [AGPL-3.0](https://github.com/yushijinhun/authlib-injector/blob/develop/LICENSE).
- [alsoft](https://github.com/kcat/openal-soft/) (Audio output library): [GNU LIBRARY GENERAL PUBLIC LICENSE](https://github.com/kcat/openal-soft/blob/master/COPYING) and [modified PFFFT](https://github.com/kcat/openal-soft/blob/master/LICENSE-pffft).
- [oboe](https://github.com/google/oboe): [Apache License 2.0](https://github.com/google/oboe/blob/main/LICENSE).
- Thanks to [Mineskin](https://mineskin.eu/) for providing Minecraft avatars.

- ## Now KivyAI is implemented!
Now launcher has an AI that soon will get full access to launcher (change settings, and more).
Check [KivyAI](https://github.com/MutantBloodcell/KivyAI) and README.txt for more info on setting up, etc. (You can build your own AI for your launcher if you forked).

## Building your own AI agent
1. You can edit, rewrite, or anything you want with the bridge (even add new bridge).
2. Edit web_app.py (the AI itself in launcher (KivyAI Agent, the name of AI in launcher), change it to your desire, you can rewrite if you wanna build from scratch).
3. Setup everything by clicking on KivyAI on upper message. (And check README.txt).
4. You're all set!

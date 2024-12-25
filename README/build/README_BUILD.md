# WanKMP-构建产物

我们写的代码，最终都需要安装到用户的设备上，那么就需要掌握对应平台的一些安装知识，以及如何操作KMP来产出这些产物交付给用户

https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-apps.html

https://blog.csdn.net/haojiagou/article/details/128332635

# Android

## 产物文件
## 产物配置

### 签名、包名

### 反破解？

### 携带资源？

## 产物地址

## 产物命令

## 产物更新

# iOS

## 产物文件
## 产物配置

### 签名、包名

### 反破解？

### 携带资源？

## 产物地址

## 产物命令

## 产物更新

# Windows/Macos/Linux

## 产物文件
- macOS — .dmg ( TargetFormat.Dmg)、 .pkg( TargetFormat.Pkg)
- Windows — .exe ( TargetFormat.Exe)、 .msi( TargetFormat.Msi)
- Linux — .deb ( TargetFormat.Deb)、 .rpm(TargetFormat.Rpm)、.AppImage(TargetFormat.AppImage)

## 产物配置
```
compose.desktop {
    application {
        // 启动的入口类
        mainClass = ""
        nativeDistributions{
            // 要打的包，但是现在编译对应类型的包，必须在对应的机器上打包，例如，windows要在windows机器上，linux要在linux机器上，macos要在mac电脑上
            // 默认打包会跳过与当前系统不兼容的任务
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
            // 配置包含的 JDK 模块
            // Gradle 插件使用 jlink 通过仅包含必要的 JDK 模块来最小化可分发的大小。
            // 此时，Gradle 插件不会自动确定必要的 JDK 模块。未能提供必要的模块不会导致编译问题，但会导致在运行时出现 ClassNotFoundException 的错误。
            // 如果在运行打包的应用程序或任务时遇到 ClassNotFoundException ，可以使用DSL 方法runDistributable 来配置包含额外的 JDK 模块，需要使用 modules 来配置。
            // 可以通过手动或运行 suggestModules 任务来确定哪些模块是必需的。
            // suggestModules 使用 jdeps 静态分析工具来确定可能缺少的模块。
            // 如果安装包的大小不重要的话，可以使用 includeAllModules = true 属性简单地包括所有运行时模块作为替代。
            modules("java.instrument", "java.management", "java.naming", "java.sql", "jdk.unsupported")
            
            // 其他属性写在下面的模块里
            ...
        }
    }
}
```

### 签名、包名
```
nativeDistributions {
    // 包名
    packageName = ""
    description = ""
    // 著作权声明
    copyright = ""
    // 供应商声明
    vendor = "Lenovo"
    // 授权协议声明
    licenseFile.set(project.file("LICENSE.txt"))
}
```
### 版本号

需要注意的是，版本必须遵循以下规则： 
- dmg 和 pkg ：格式为 MAJOR.MINOR.PATCH 
    - MAJOR是一个 > 0 的整数；
    - MINOR是一个可选的非负整数；
    - PATCH是一个可选的非负整数； 
- msi 和 exe ：格式为 MAJOR.MINOR.BUILD 
    - MAJOR是一个非负整数，最大值为255；
    - MINOR是一个非负整数，最大值为255；
    - BUILD是一个非负整数，最大值为65535； 
- rpm ：版本不得包含-（破折号）字符。 
- deb ：格式为 EPOCH:UPSTREAM_VERSION-DEBIAN_REVISION 
    - EPOCH是一个可选的非负整数；
    - UPSTREAM_VERSION 只包含字母数字和字符., +, -, ~，必须以数字开头；
    - DEBIAN_REVISION是可选的，可能只包含字母数字和字符., +, ~。

```
nativeDistributions {

    // 版本号-优先级最低 是否能够用toml管理？
    packageVersion = "1.0.0"
    
    linux {
        // 优先级第二 a version for all Linux distributables
        packageVersion = "1.0.0"
        // 优先级最高 a version only for the deb package
        debPackageVersion = "1.0.0"
        // a version only for the rpm package
        rpmPackageVersion = "1.0.0"
        // 设置图标
        iconFile.set(project.file("launcher/icon.png"))
    }
    macOS {
        // a version for all macOS distributables
        packageVersion = "1.1.0"
        // a version only for the dmg package
        dmgPackageVersion = "1.1.0"
        // a version only for the pkg package
        pkgPackageVersion = "1.1.0"

        // a build version for all macOS distributables
        packageBuildVersion = "1.1.0"
        // a build version only for the dmg package
        dmgPackageBuildVersion = "1.1.0"
        // a build version only for the pkg package
        pkgPackageBuildVersion = "1.1.0"
        // 设置图标
        iconFile.set(project.file("launcher/icon.icns"))
    }
    windows {
        // a version for all Windows distributables
        packageVersion = "1.2.0"
        // a version only for the msi package
        msiPackageVersion = "1.2.0"
        // a version only for the exe package
        exePackageVersion = "1.2.0"
        // 设置图标
        iconFile.set(project.file("launcher/icon.ico"))
    }
}
```
### 反破解？
```
nativeDistributions {     
    buildTypes.release.proguard {
        // 是否开启
        obfuscate.set()
        // 混淆配置
        configurationFiles.from(project.file("proguard-rules.pro"))
    }
}
```
### 携带资源

#### 应用图标

```
nativeDistributions {
    linux {
        // 设置图标
        iconFile.set(project.file("launcher/icon.png"))
    }
    macOS {
        // 设置图标
        iconFile.set(project.file("launcher/icon.icns"))
    }
    windows {
        // 设置图标
        iconFile.set(project.file("launcher/icon.ico"))
    }
}
```

## 产物地址
```
nativeDistributions {
    // 设置自定义输出目录
    outputBaseDir.set(customOutPutDir)
}
```
## 安装配置
```
nativeDistributions {
    windows {
        //TODO windows怎么自定义安装界面？
        // 是否可以选择自定义安装路径
        dirChooser
        // 默认安装的绝对路径
        installationPath
        // 为应用程序添加一个控制台启动器
        console
        // 是否为所有用户安装
        perUserInstall
        // 将程序添加到指定的开始菜单栏里
        menuGroup
        // 一个唯一的 ID，当更新的版本比安装的版本更新时，它使用户能够通过安装程序更新应用程序。对于单个应用程序，该值必须保持不变；
        upgradeUuid
    }
    linux{
        // 将程序添加到指定的开始菜单栏里
        menuGroup
    }
    macOS{
        dockName
        appStore
        appCategory
        entitlementsFile
        runtimeEntitlementsFile
        runtimeProvisioningProfile
        infoPlist()
    }
}
```

## 产物命令

- package<FormatName>（例如 packageDmg 或 packageMsi）用于将应用程序打包成相应的格式。
  这块需要注意的是，目前没有交叉编译支持，因此只能使用特定操作系统构建格式（例如，要构建.dmg 就必须使用 macOS）。默认情况下会跳过与当前操作系统不兼容的任务。 
- packageDistributionForCurrentOS 是一个生命周期任务，聚合了应用程序的所有包任务。 
- packageUberJarForCurrentOS 用于创建单个 jar 文件，其中包含当前操作系统的所有依赖项。
- run 用于在本地运行应用程序。
  需要定义一个 mainClass 包含该 main 函数的类。请注意，run 将启动具有完整运行时的非打包 JVM 应用程序。
  这比创建具有最小运行时间的紧凑二进制映像更快、更容易调试。要运行最终的二进制图像，需要改用 runDistributable 。 
- createDistributable 用于在不创建安装程序的情况下创建预打包的应用程序映像和最终应用程序映像。 
- runDistributable 用于运行预先打包的应用程序映像。 只有在脚本中使用 block 或 property 时才会创建任务




## 产物更新

# Js

## 产物文件
## 产物配置

### 签名、包名

### 反破解？

### 携带资源？

## 产物地址

## 产物命令

## 产物更新


https://juejin.cn/post/7390586008771526710#heading-5

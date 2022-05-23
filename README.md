## 一、框架组成

1. `component`组件层，俗称通用工具，不涉及业务逻辑，纯工具使用。
2. `framework`架构层，框架集成主流`MVP`、`MVVM`、`AAC`架构，在业务模块中直接引用，方便使用对应架构。
3. `module`业务层，根据具体业务进行划分，可以根据功能划分、业务划分、页面划分..
4. `service`业务接口层，主要针对跨模块通讯，通过接口或者路由。

## 二、模块化疑难杂症剖析

**1、如何实现Application Context 全局共享？每个模块单独一个Application?每个模块的Application基于同一个基类BaseApplication?**

为了更好的代码隔离与解耦，在特定组件内使用的SDK及三方库，应该只在该组件内依赖，不应该让该组件的特定SDK及三方库的API暴露给其他不需要用的组件。有一个问题就出现了，SDK及三方库常常需要手动去初始化，而且一般都需要在项目一启动（即 Application中）初始化，但是一个项目肯定只能有一个自定义的 `Application`，该项目中的自定义 `Application`在 `cams-component-common`模块中，并且在容器`App`的清单文件中声明的，那其他组件该如何初始化呢？带着这个问题我们一起来深入研究下。

**常见的组件初始化解决方案：**

在我的了解范围内，目前有两种最为常见的解决方案：

- **面向接口编程 + 反射扫描实现类：**

  &emsp;&emsp;该方案是基于接口编程，自定义 `Application` 去实现一个自定义的接口（`interface`），这个接口中定一些和 `Application` 生命周期相对应的抽象方法及其他自定义的抽象方法，每个组件去编写一个实现类，该实现类就类似于一个假的自定义 `Application`，然后在真正的自定义 `Application` 中去通过反射去动态查找当前运行时环境中所有该接口的实现类，并且去进行实例化，然后将这些实现类收集到一个集合中，在 `Application` 的对应声明周期方法中去逐一调用对应方法，以实现各实现类能够和 `Application` 生命周期相同步，并且持有 `Application` 的引用及 `context`上下文对象，这样我们就可以在组件内模拟 `Application` 的生命周期并初始化SDK和三方库。使用反射还需要做一些异常的处理。该方案是我见过的最常见的方案，在一些商业项目中也见到过。

- **面向接口编程 + meta-data + 反射：**

  &emsp;&emsp;该方案的后半部分也是和第一种方法一样，通过接口编程实现 `Application` 的生命周期同步，其实这一步是避免不了的，在我的方案中，后半部分也是这样实现的。不同的是前半部分，也就是如何找到接口的实现类，该方案使用的是 `AndroidManifest` 的 `meta-data` 标签，通过每个组件内的 `AndroidManifest` 内去声明一个 `meta-data` 标签，包含该组件实现类的信息，然后在 `Application` 中去找到这些配置信息，然后通过反射去创建这些实现类的实例，再将它们收集到一个集合中，剩下的操作基本相同了。该方案和第一种方案一样都需要处理很多的异常。这种方案我在一些开源项目中见到过，个人认为过于繁琐，还要处理很多的异常。

**本项目中所使用面向接口编程 + Java的SPI机制（ServiceLoader）+AutoService：**

&emsp;&emsp;先来认识下 `Java` 的 `SPI`机制：面向的对象的设计里，我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。为了实现在模块装配的时候不用在程序里动态指明，这就需要一种服务发现机制。`JavaSPI` 就是提供这样的一个机制：为某个接口寻找服务实现的机制。这有点类似 `IOC`的思想，将装配的控制权移到了程序之外。这段话也是我复制的别人的，听起来很懵逼，大致意思就是我们可以通过 `SPI` 机制将实现类暴露出去。关于如何使用 `SPI`，这里不在陈述，总之是我们在各组件内通过 `SPI` 去将实现类暴露出去，在 `Application` 中我们通过 `Java` 提供的 `SPI` `API` 去获取这些暴露的服务，这样我们就拿到了这些类的实例，剩下的步骤就和上面的方案一样了，通过一个集合遍历实现类调用其相应的方法完成初始化的工作。由于使用 `SPI` 需要在每个模块创建对应的文件配置，这比较麻烦，所以我们使用 `Google`的 `AutoService` 库来帮助我们自动创建这些配置文件，使用方式也非常的简单，就是在实现类添加一个 `AutoService` 注解。

**详细介绍一下在项目中使用：**

首先创建一个`AppDelegate`接口类，方法定义参考`Application`生命周期。这样`AutoService`获取每个`AppDelegate`实现类，并在真实`Application`的生命周期中调用接口中对应方法。

```java
interface AppDelegate {

    /**
     * 同[Application.attachBaseContext]
     * @param context [Context]
     */
    fun onAttachBaseContext(context: Context)

    /**
     * 同[Application.onCreate]
     * @param application [Application]
     */
    fun onCreate(application: Application)

    /**
     * 同[Application.onTerminate]
     * @param application [Application]
     */
    fun onTerminate(application: Application)

    /**
     * 同[Application.onLowMemory]低内存时执行
     * @param application [Application]
     */
    fun onLowMemory(application: Application)

    /**
     * 同[Application.onTrimMemory]清理内存时执行
     * @param level [Int]
     */
    fun onTrimMemory(level: Int)

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    fun initByFrontDesk(): MutableList<() -> String>

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    fun initByBackstage()
}
```

在每个模块中实现`AppDelegate`接口，在类中可以做一些第三方库初始化操作。注意:需要在类上面增加@AutoService注解，这样才能通过`AutoService`查找。

```java
@AutoService(AppDelegate::class)
class CommonAppDelegate : AppDelegate {

    private lateinit var mContext: Context

    private lateinit var mApplication: Application

    /**
     * 同[Application.attachBaseContext]
     * @param context [Context]
     */
    override fun onAttachBaseContext(context: Context) {
        this.mContext = context
    }

    /**
     * 同[Application.onCreate]
     * @param application [Application]
     */
    override fun onCreate(application: Application) {
        this.mApplication = application
    }

    /**
     * 同[Application.onTerminate]
     * @param application [Application]
     */
    override fun onTerminate(application: Application) {
    }

    /**
     * 同[Application.onLowMemory]低内存时执行
     * @param application [Application]
     */
    override fun onLowMemory(application: Application) {
    }

    /**
     * 同[Application.onTrimMemory]清理内存时执行
     * @param level [Int]
     */
    override fun onTrimMemory(level: Int) {
    }

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(mContext)) {
            list.add { initARouter() }
        }
        list.add { initTencentBugly() }
        return list
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
    }

    /**
     * 阿里路由 ARouter 初始化
     */
    private fun initARouter(): String {
        ARouterManager.init(mApplication)
        return "ARouter -->> init complete"
    }

    /**
     * 初始化 腾讯Bugly
     * 测试环境应该与正式环境的日志收集渠道分隔开
     * 目前有两个渠道 测试版本/正式版本
     */
    private fun initTencentBugly(): String {
        // 第三个参数为SDK调试模式开关
        CrashReport.initCrashReport(
            mContext,
            "",
            BuildConfig.DEBUG
        )
        return "Bugly -->> init complete"
    }
}
```

通过`AutoService`获取所有被@AutoService标注的类，并进行生命周期调用。

```java
/**
 * 加载组件代理类
 * 组件初始化的工作将由该代理类代理实现
 */
class AppBindModuleDelegate : AppDelegate {

    private var mLoader: ServiceLoader<AppDelegate> =
        ServiceLoader.load(AppDelegate::class.java)

    /**
     * 同[Application.attachBaseContext]
     * @param context Context
     */
    override fun onAttachBaseContext(context: Context) {
        mLoader.forEach {
            it.onAttachBaseContext(context)
        }
    }

    /**
     * 同[Application.onCreate]
     * @param application Application
     */
    override fun onCreate(application: Application) {
        mLoader.forEach { it.onCreate(application) }
    }

    /**
     * 同[Application.onTerminate]
     * @param application Application
     */
    override fun onTerminate(application: Application) {
        mLoader.forEach { it.onTerminate(application) }
    }

    /**
     * 同[Application.onLowMemory]
     * @param application Application
     */
    override fun onLowMemory(application: Application) {
        mLoader.forEach { it.onLowMemory(application) }
    }

    /**
     * 同[Application.onTrimMemory]
     * @param level Int
     */
    override fun onTrimMemory(level: Int) {
       mLoader.forEach { it.onTrimMemory(level) }
    }

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        val list: MutableList<() -> String> = mutableListOf()
        mLoader.forEach { list.addAll(it.initByFrontDesk()) }
        return list
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
        mLoader.forEach { it.initByBackstage() }
    }
}
```

在真实的`Application`中做真实初始化操作。

```java
open class CommonBaseApplication : MultiDexApplication() {

    private val mCoroutineScope by lazy { MainScope() }

    private val mLoadModuleProxy by lazy { AppBindModuleDelegate() }

    companion object {
        // 全局Context
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
        //全局Application
        @SuppressLint("StaticFieldLeak")
        lateinit var application: CommonBaseApplication
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        context = base
        application = this

        mLoadModuleProxy.onAttachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())

        mLoadModuleProxy.onCreate(this)

        // 策略初始化第三方依赖
        initDepends()
    }

    /**
     * 初始化第三方依赖
     */
    private fun initDepends() {
        // 开启一个 Default Coroutine 进行初始化不会立即使用的第三方
        mCoroutineScope.launch(Dispatchers.Default) {
            mLoadModuleProxy.initByBackstage()
        }

        // 前台初始化
        val allTimeMillis = measureTimeMillis {
            val depends = mLoadModuleProxy.initByFrontDesk()
            var dependInfo: String
            depends.forEach {
                val dependTimeMillis = measureTimeMillis { dependInfo = it() }
                Log.d("BaseApplication", "initDepends: $dependInfo : $dependTimeMillis ms")
            }
        }
        Log.d("BaseApplication", "初始化完成 $allTimeMillis ms")
    }

    override fun onTerminate() {
        super.onTerminate()
        mLoadModuleProxy.onTerminate(this)
        mCoroutineScope.cancel()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mLoadModuleProxy.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mLoadModuleProxy.onTrimMemory(level)
    }

}
```

到这里基本解决疑惑问题，最后注意记得在AndroidManifest中引用自定义`Application`类。

**2、如何实现多模块资源文件名冲突？**

&emsp;&emsp;在组件化方案中，资源命名冲突是一个比较严重的问题，由于在打包时会进行资源的合并，如果两个模块中有两个相同名字的文件，那么最后只会保留一份，如果不知道这个问题的小伙伴，在遇到这个问题时肯定是一脸懵逼的状态。问题既然已经出现，那我们就要去解决，解决办法就是每个组件都用固定的命名前缀，这样就不会出现两个相同的文件的现象了，我们可以在 **build.gradle** 配置文件中去配置前缀限定，如果不按该前缀进行命名，**AS** 就会进行警告提示，配置如下：

```
android {
    defaultConfig{
    	resourcePrefix "前缀_"
    }
}
```

注意：res资源下的`layout`、`drawable`、`mipmap`、`colors`、`strings`文件名、字段名加入前缀。

**3、如何实现模块为Application类型单独编译运行？模块为Library类型整体项目编译运行？**

首先，两种编译方式做到切换随意自如，可以设置开关，在开发阶段关闭，单独编译，上线阶段开启，整体编译。我们可以在gradle.properties文件内定义，这样可以在配置gradle使用。为什么要这样处理？因为在gradle需要根据不同编译方式使用不同的插件**plugin**、清单文件**AndroidManifest**。

首先，project下的gradle.properties设置开关

```
#组件化开关
isBuildModule=false
```

module模块build.gradle配置，根据开关切换插件

```
if (isBuildModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```

切换清单文件,在`srcFile`下配置的路径，在所在的路径下分别创建AndroidManifest.xml

```
sourceSets {
        main {
            if (isBuildModule.toBoolean()) {
                manifest.srcFile 'src/main/debug/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/release/AndroidManifest.xml'
            }
        }
    }
```

src/main/debug/AndroidManifest.xml文件配置详情，由于`com.android.application`，所以文件中Activity必须设置默认路口`android.intent.action.MAIN`

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.linwei.cams.module.square">

    <application
        android:name="com.linwei.cams.component.common.base.CommonBaseApplication"
        android:allowBackup="true"
        android:icon="@mipmap/square_ic_launcher"
        android:label="@string/square_app_name"
        android:supportsRtl="true"
        android:theme="@style/SquareModuleAppTheme">
        <activity
            android:name=".ui.SquareActivity"
            android:exported="true"
            android:label="@string/square_module_page">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>

</manifest>
```

src/main/release/AndroidManifest.xml文件配置详情

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.linwei.cams.module.square">

    <applications
        android:allowBackup="true"
        android:supportsRtl="true">
        <activity android:name=".ui.SquareActivity"/>
    </application>

</manifest>
```

## 三、组件划分

&emsp;&emsp;其实组件的划分一直是一个比较难的部分，这里其实也给不到一些非常适合的建议，看是看具体项目而定。

&emsp;&emsp;关于基础组件通常要以独立可直接复用的角度出现，比如网络模块、二维码识别模块等。

&emsp;&emsp;关于业务组件，业务组件一般可以进行单独调试，也就是可以作为 **app** 运行，这样才能发挥组件化的一大用处，当项目越来越大，业务组件越来越多时，编译耗时将会是一个非常棘手的问题，但是如果每个业务模块都可以进行的单独调试，那就大大减少了编译时间，同时，开发人员也不需要关注其他组件。

WanAndroid项目中划分方式：

`cams-component-common` -工具，引用三方库

`cams-component-cache`缓存组件、`cams-component-database`数据库组件、`cams-component-image`图片加载组件、`cams-component-network`网络请求组件、`cams-component-web` 网页组件

`cams-framework-mvp`**MVP**架构、`cams-framework-mvvm`**MVVM**架构、`cams-framework-mvi`**MVI**架构

`cams-module-main`容器模块、`cams-module-login`登录模块、`cams-module-home`首页模块、`cams-module-project`项目模块、`cams-module-publis`广场模块、`cams-module-square`公众号模块、`cams-module-mine`我的模块

`cams-service-base`接口、`cams-service-login`登录接口、`cams-service-home`首页接口、`cams-service-project`项目接口、`cams-service-publis`广场接口、`cams-service-square`公众号接口、`cams-service-mine`我的接口

## 四、依赖版本控制

&emsp;&emsp;组件化常见的一个问题就是依赖版本，每个组件都有可能自己的依赖库，那我们应该统一管理各种依赖库及其版本，使项目所有使用的依赖都是同一个版本，而不是不同版本。项目中引入第三方资源通过`component`中导入,其中公共工具在`cams-component-common`统一导入。

在项目根目录下可以定义一个.gradle文件，来统一管理第三方库版本号和Android编译版本,这样可以在`component`、`module`、`service`中直接使用。

## 五、项目使用的三方库及其简单示例和资料

* [Kotlin](https://github.com/JetBrains/kotlin)
* [Kotlin-Coroutines-Flow](https://github.com/JetBrains/kotlin)
* [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
* [Hilt](https://developer.android.com/jetpack/androidx/releases/hilt)
* [OkHttp3](https://github.com/square/okhttp)：网络请求
* [Retrofit](https://github.com/square/retrofit)：网络请求
* [Rxlifecycle4](https://github.com/trello/RxLifecycle)：Lifecycle handling APIs for Android apps using RxJava
* [Rxjava3](https://github.com/ReactiveX/RxJava)：RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM
* [Rxandroid3](https://github.com/ReactiveX/RxAndroid)：RxJava bindings for Android
* [Rxkotlin3](https://github.com/ReactiveX/RxKotlin)：RxJava bindings for Kotlin
* [Rxcache2](https://github.com/VictorAlbertos/RxCache)：Reactive caching library for Android and Java
* [XTool](https://github.com/WeiShuaiDev/XTool)：App错误日志、请求数据、Log信息抓取，同时每次触发错误，通过手机消息列表弹出
* [MMKV](https://github.com/Tencent/MMKV)：腾讯基于 mmap内存映射的 key-value 本地存储组件
* [Coil](https://github.com/coil-kt/coil)：一个 Android 图片加载库，通过 Kotlin 协程的方式加载图片
* [StateLayout](https://github.com/liangjingkanji/StateLayout)：Android 一行代码构建整个应用的缺省页
* [ARoute](https://github.com/alibaba/ARouter)：阿里用于帮助 Android App进行组件化改造的框架 —— 支持模块间的路由、通信、解耦
* [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)：一个强大并且灵活的 RecyclerViewAdapter
* [EventBus](https://github.com/greenrobot/EventBus)：适用于 Android 和 Java的发布/订阅事件总线
* [Bugly](https://bugly.qq.com/v2/index)：腾讯异常上报及热更新(只集成了异常上报)
* [PermissionX](https://github.com/guolindev/PermissionX)：郭霖权限请求框架
* [LeakCanary](https://github.com/square/leakcanary)：Android 的内存泄漏检测库
* [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)： 今日头条屏幕适配方案终极版

### 1、Kotlin协程

关于 **Kotlin 协程**，是真的香，具体教程可以看我的一篇文章：

- [万字长文 - Kotlin 协程进阶](https://juejin.cn/post/6950616789390721037)
- [一文看透 Kotlin 协程本质](https://juejin.cn/post/6987724340775108622#heading-35)
- [Kotlin协程实现原理:CoroutineScope&Job](https://cloud.tencent.com/developer/article/1748586)

### 2、Flow

- [Google 推荐在 MVVM 架构中使用 Kotlin Flow: ](https://juejin.im/post/6854573211930066951)
- [即学即用Kotlin - 协程:](https://juejin.im/post/6854573211418361864)
- [Kotlin Coroutines Flow 系列(1-5):](https://juejin.im/post/6844904057530908679)

### 3、PermissionX

**PermissionX** 是郭霖的一个权限申请框架
**使用方式:**

```
PermissionX.init(this)
     .permissions("需要申请的权限")
     .request { allGranted, grantedList, deniedList -> }
```

**资料:**

GitHub: [https://github.com/guolindev/PermissionX](https://github.com/guolindev/PermissionX)

### 4、EventBus APT

事件总线这里选择的还是 **EventBus**，也有很多比较新的事件总线框架,还是选择了这个直接上手的
在框架内我对 **EventBus** 进行了基类封装，自动注册和解除注册，在需要注册的类上添加 **@EventBusRegister** 注解即可，无需关心内存泄漏及没及时解除注册的情况，基类里已经做了处理

```kotlin
@EventBusRegister
class MainActivity : AppCompatActivity() {}
```

很多使用 **EventBus** 的开发者其实都没有发现 **APT** 的功能，这是 **EventBus3.0** 的重大更新，使用 **EventBus APT** 可以在编译期生成订阅类，这样就可以避免使用低效率的反射，很多人不知道这个更新，用着**3.0**的版本，实际上却是**2.0**的效率。
项目中已经在各模块中开启了 **EventBus APT**，**EventBus** 会在编译器对各模块生成订阅类，需要我们手动编写代码去注册这些订阅类：

```kotlin
// 在APP壳的AppApplication类中
EventBus
     .builder()
	 .addIndex("各模块生成的订阅类的实例 类名在base_module.gradle脚本中进行了设置 比如 module_home 生成的订阅类就是 module_homeIndex")
     .installDefaultEventBus()
```

### 5、AndroidAutoSize

屏幕适配使用的是 **JessYan** 大佬的 今日头条屏幕适配方案终极版

GitHub: [https://github.com/JessYanCoding/AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)

**使用方式:**

```
// 在清单文件中声明
<manifest>
    <application> 
    // 主单位使用dp 没设置副单位
        <meta-data
            android:name="design_width_in_dp"
            android:value="360"/>
        <meta-data
            android:name="design_height_in_dp"
            android:value="640"/>           
     </application>           
</manifest>

// 默认是以竖屏的宽度为基准进行适配
// 如果是横屏项目要适配Pad(Pad适配尽量使用两套布局 因为手机和Pad屏幕宽比差距很大 无法完美适配)
<manifest>
    <application>            
    // 以高度为基准进行适配 (还需要手动代码设置以高度为基准进行适配) 目前以高度适配比宽度为基准适配 效果要好
        <meta-data
            android:name="design_height_in_dp"
            android:value="400"/>           
     </application>           
</manifest>

// 在Application 中设置
// 屏幕适配 AndroidAutoSize 以横屏高度为基准进行适配
AutoSizeConfig.getInstance().isBaseOnWidth = false
```

### 6、ARoute

**ARoute** 是阿里巴巴的一个用于帮助 **Android App** 进行组件化改造的框架 —— 支持模块间的路由、通信、解耦

**使用方式:**

```
// 1.在需要进行路由跳转的Activity或Fragment上添加 @Route 注解
@Route(path = "/test/activity")
public class YourActivity extend Activity {
    ...
}

// 2.发起路由跳转
ARouter.getInstance()
    .build("目标路由地址")
    .navigation()
    
// 3.携带参数跳转
ARouter.getInstance()
	.build("目标路由地址")
    .withLong("key1", 666L)
    .withString("key3", "888")
    .withObject("key4", new Test("Jack", "Rose"))
    .navigation()

// 4.接收参数
@Route(path = RouteUrl.MainActivity2)
class MainActivity : AppCompatActivity() {

    // 通过name来映射URL中的不同参数
    @Autowired(name = "key")
    lateinit var name: String
    
	override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        // ARouter 依赖注入 ARouter会自动对字段进行赋值，无需主动获取
        ARouter.getInstance().inject(this)
    }
}

// 5.获取Fragment
Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
```

**资料:**

官方文档:[https://github.com/alibaba/ARouter](https://github.com/alibaba/ARouter)

### 7、ViewBinding

通过视图绑定功能，可以更轻松地编写可与视图交互的代码。在模块中启用视图绑定之后，系统会为该模块中的每个 **XML** 布局文件生成一个绑定类。绑定类的实例包含对在相应布局中具有 **ID** 的所有视图的直接引用。
在大多数情况下，视图绑定会替代 **findViewById**

**使用方式:**

按模块启用**ViewBinding**

```groovy
// 模块下的build.gradle文件
android {
	// 开启ViewBinding
    // 高版本AS
    buildFeatures {
        viewBinding = true
    }
    // 低版本AS 最低3.6
    viewBinding {
        enabled = true
    }
}
```

**Activity** 中 **ViewBinding** 的使用

```kotlin
// 之前设置视图的方法
setContentView(R.layout.activity_main)

// 使用ViewBinding后的方法
val mBinding = ActivityMainBinding.inflate(layoutInflater)
setContentView(mBinding.root)

// ActivityMainBinding类是根据布局自动生成的 如果没有请先build一下项目
// ViewBinding会将控件id转换为小驼峰命名法,所以为了保持一致规范,在xml里声明id时也请使用小驼峰命名法
// 比如你有一个id为mText的控件,可以这样使用
mBinding.mText.text = "ViewBinding"
```

**Fragment** 中 **ViewBinding** 的使用

```kotlin
// 原来的写法
return inflater.inflate(R.layout.fragment_blank, container, false)

// 使用ViewBinding的写法
mBinding = FragmentBlankBinding.inflate(inflater)
return mBinding.root
```

**资料:**

官方文档: [https://developer.android.com/topic/libraries/view-binding](https://developer.android.com/topic/libraries/view-binding)

CSDN: [https://blog.csdn.net/u010976213/article/details/104501830?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5](https://blog.csdn.net/u010976213/article/details/104501830?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5)

### 8、ViewModel

**ViewModel** 类旨在以注重生命周期的方式存储和管理界面相关的数据。**ViewModel** 类让数据可在发生屏幕旋转等配置更改后继续留存。

**使用方式:**

```kotlin
class MainViewModel : ViewModel(){}

class MainActivity : AppCompatActivity() {
		// 获取无参构造的ViewModel实例
    val mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
}
```

**资料:**

官方文档: [https://developer.android.com/topic/libraries/architecture/viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel)

Android ViewModel，再学不会你砍我: [https://juejin.im/post/6844903919064186888](https://juejin.im/post/6844903919064186888)

### 9、LiveData

**LiveData** 是一种可观察的数据存储器类。与常规的可观察类不同，**LiveData** 具有生命周期感知能力，意指它遵循其他应用组件（如 **Activity**、**Fragment** 或 **Service**）的生命周期。这种感知能力可确保 **LiveData** 仅更新处于活跃生命周期状态的应用组件观察者

**LiveData** 分为可变值的 **MutableLiveData** 和不可变值的 **LiveData**

**常用方法:**

```kotlin
fun test() {
        val liveData = MutableLiveData<String>()
        // 设置更新数据源
        liveData.value = "LiveData"
        // 将任务发布到主线程以设置给定值
        liveData.postValue("LiveData")
        // 获取值
        val value = liveData.value
        // 观察数据源更改(第一个参数应是owner:LifecycleOwner 比如实现了LifecycleOwner接口的Activity)
        liveData.observe(this, {
            // 数据源更改后触发的逻辑
        })
    }
```

**资料:**

官方文档: [https://developer.android.com/topic/libraries/architecture/livedata](https://developer.android.com/topic/libraries/architecture/livedata)

### 10、Lifecycle

**Lifecycle** 是一个类，用于存储有关组件（如 **Activity** 或 **Fragment**）的生命周期状态的信息，并允许其他对象观察此状态。**LifecycleOwner** 是单一方法接口，表示类具有 **Lifecycle**。它具有一种方法（即 **getLifecycle()**），该方法必须由类实现。实现 **LifecycleObserver** 的组件可与实现 **LifecycleOwner** 的组件无缝协同工作，因为所有者可以提供生命周期，而观察者可以注册以观察生命周期。

**资料:**

官方文档: [https://developer.android.com/topic/libraries/architecture/lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)

### 11、Hilt

**Hilt** 是 **Android** 的依赖项注入库，可减少在项目中执行手动依赖项注入的样板代码。执行手动依赖项注入要求您手动构造每个类及其依赖项，并借助容器重复使用和管理依赖项。

**Hilt** 通过为项目中的每个 **Android** 类提供容器并自动管理其生命周期，提供了一种在应用中使用 **DI（依赖项注入）**的标准方法。**Hilt** 在热门 **DI** 库 **Dagger** 的基础上构建而成，因而能够受益于 **Dagger** 的编译时正确性、运行时性能、可伸缩性和 **Android Studio** 支持。

**资料:**

目前官方文档还没有更新正式版的，还是 **alpha** 版本的文档：[使用 Hilt 实现依赖项注入](https://developer.android.com/training/dependency-injection/hilt-android)

**Dagger** 的 **Hilt** 文档目前是最新的：[Dagger-Hilt](https://dagger.dev/hilt/)

[Jetpack新成员，一篇文章带你玩转Hilt和依赖注入](https://juejin.cn/post/6902009428633698312#heading-11)

[Hilt使用姿势全解析](https://juejin.cn/post/6986450257295081502)

### 12、Coil

**Coil** 是一个 Android 图片加载库，通过 Kotlin 协程的方式加载图片。特点如下：

- **更快**: Coil 在性能上有很多优化，包括内存缓存和磁盘缓存，把缩略图存保存在内存中，循环利用 bitmap，自动暂停和取消图片网络请求等。
- **更轻量级**: Coil 只有2000个方法（前提是你的 APP 里面集成了 OkHttp 和 Coroutines），Coil 和 Picasso 的方法数差不多，相比 Glide 和 Fresco 要轻量很多。
- **更容易使用**: Coil 的 API 充分利用了 Kotlin 语言的新特性，简化和减少了很多样板代码。
- **更流行**: Coil 首选 Kotlin 语言开发并且使用包含 Coroutines, OkHttp, Okio 和 AndroidX Lifecycles 在内最流行的开源库。

**Coil** 名字的由来：取 **Co**routine **I**mage **L**oader 首字母得来。

**资料:**

官方文档: [https://coil-kt.github.io/coil/](https://coil-kt.github.io/coil/)

三方库源码笔记（13）-可能是全网第一篇 Coil 的源码分析文章：[https://juejin.cn/post/6897872882051842061](https://juejin.cn/post/6897872882051842061)

【奇技淫巧】新的图片加载库？基于Kotlin协程的图片加载库——Coil：[https://juejin.cn/post/6844904159527829518](https://juejin.cn/post/6844904159527829518)


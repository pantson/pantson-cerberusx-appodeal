
# Module appodeal

The Appodeal module provides simple support for the Appodeal ad service allowing you to display advers.

(Note: AppodealCX plugin is only currently available on the android target)

V1.0.0 14-12-2019
Inital release

## Class appodeal.Appodeal

The Appodeal module provides simple support for the Appodeal ad service allowing you to display advers.

To use Appodeal in your game:

+ Get your APP_KEY from the Appodeal dashboard
+ Import the Appodeal module into your game
+ Compile once. This will fail. Then change files listed below.

### Compatibility
Tested with CerburusX version V2019-10-13 and Appodeal 2.6.0

You minimum API level will be raised to API 21. This is to fix the multidexing issues.

### Manual ammedments to files

Compile your application and then ammend these files. This only has to be done once (unless you remove your build folder)

#### file: build.folder\android\gradletemplate\build.gradle

change dependencies block to:
    dependencies {
        classpath 'com.android.tools.build:gradle:3.5.2'
    }
    
change maven block to:

        maven {
            url "https://maven.google.com"
            url "https://artifactory.appodeal.com/appodeal"
        }

#### file: build.folder\android\gradletemplate\gradle\wrapper\gradle-wrapper.properties

change distributionUrl line:

    distributionUrl=https\://services.gradle.org/distributions/gradle-5.4.1-all.zip

#### file: build.folder\android\gradletemplate\app\build.gradle

change:

    android {
        compileSdkVersion ${ANDROID_TARGET_SDK_VERSION}
        buildToolsVersion "${ANDROID_BUILD_TOOLS_VERSION}"

to:

    android {
        compileSdkVersion ${ANDROID_TARGET_SDK_VERSION}
        buildToolsVersion "${ANDROID_BUILD_TOOLS_VERSION}"
    
        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }

#### file: android\gradletemplate\app\src\main\AndroidManifest.xml

change:

      <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="${ANDROID_APP_LABEL}"
        android:roundIcon="@mipmap/ic_launcher_round">

to:

      <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="${ANDROID_APP_LABEL}"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:networkSecurityConfig="@xml/network_security_config">

#### file: build.folder\android\app\src\main\res\xml\network_security_config.xml

create this file. contents:

    <?xml version="1.0" encoding="utf-8"?>
    <network-security-config>
        <base-config cleartextTrafficPermitted="true">
            <trust-anchors>
                <certificates src="system" />
                <certificates src="user" />
            </trust-anchors>
        </base-config>
        <domain-config cleartextTrafficPermitted="true">
            <domain includeSubdomains="true">127.0.0.1</domain>
        </domain-config>
    </network-security-config>

### Example
    Strict

    Import mojo2
    Import appodeal

    Class myClass Extends App
      Field cnvs:Canvas
      
      Field ad:Appodeal
      
      Method OnCreate:Int()
        SetUpdateRate(60)        
        cnvs = New Canvas
        
        ' my test key. Change this
        Local APPKEY:String="b3b684d2e77c12114fae4095a345ac0f3aa4dcc3fccb58f0"
        
        ad = New Appodeal()
        ad.Initialise(APPKEY,Appodeal.INTERSTITIAL | Appodeal.BANNERTOP)
        Return 0
      End

      Method OnUpdate:Int()
        If MouseHit(MOUSE_LEFT)
          If MouseY()<DeviceHeight()/2
            If ad.IsLoaded(Appodeal.BANNERTOP)
              ad.Show(Appodeal.BANNERTOP)
            End
          Else
            If ad.IsLoaded(Appodeal.INTERSTITIAL)
              ad.Show(Appodeal.INTERSTITIAL)
            End
          End
        End
        Return 0
      End
      
      Method OnRender:Int()
        cnvs.Clear (0,0.5,0)
        cnvs.DrawRect 0,0,DeviceWidth(),DeviceHeight()/2  
        cnvs.Flush

        Return 0
      End
    End

    Function Main:Int()
      New myClass    
      Return 0
    End

### Methods

#### Method Initialise:Void(AppKey:String, AdTypes:Int)

Initialises the Appodeal plugin. Use the app key from the dashboard.

If compiled in debg mode, only test adverts will appear.

Adtypes is a combination of the type of adverts you want it your app, for example Appodeal.BANNERTOP | Appodeal.INTERSTITIAL

By default GDPR consent is set to false so user detials arent tracked.

#### Method IsLoaded:Bool(AdType:Int)

Check to see if an advert has loaded and ready for display. AdType is a single advert type.

#### Method Show:Void(AdType:Int)

Show an advert of AdType

#### Method EnableGDPRConsent:Void()

If you have permission from the user, you can Enable GDPR consent. By default GDPR consent is disabled. 

#### Method DisableGDPRConsent:Void()

Disable GDPR consent. By default GDPR consent is disabled. 

#### Method EnableCOPPA:Void()

If the user has Child settings in the OS, this will enable child friendly advertising in Appodeal.
This is the defauly option.

#### Method DisableCOPPA:Void()

If the user has Child settings in the OS, this will disable child friendly advertising in Appodeal.

#### Method IgnoreCOPPA:Void()

This will ignore and child friendly settings in Appodeal.


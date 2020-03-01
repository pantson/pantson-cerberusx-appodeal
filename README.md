
# Module appodeal

The Appodeal module provides simple support for the Appodeal ad service allowing you to display adverts.

(Note: AppodealCX plugin is only currently available on the android target)

Latest version

+ Added APPODEAL_AMAZON

V1.2.2 29-02-2020

+ Updated docs

V1.2.1 29-02-2020

+ Removed permission: android.permission.REQUEST_INSTALL_PACKAGES

V1.2.0 27-02-2020

+ Updated to Appodeal 2.6.2
+ Added #ADMOB_APP_ID

V1.1.0 17-02-2020

+ Added Hide for banner ads
+ Added EU Consent prompts

V1.0.0 14-12-2019

+ Inital release

# Class appodeal.Appodeal

The Appodeal module provides simple support for the Appodeal ad service allowing you to display adverts.

To use Appodeal in your game:

+ Get your APP_KEY from the Appodeal dashboard

+ Import the Appodeal module into your game

+ Compile once. This will fail. Then change files listed below.

+ Include APPODEAL_LOCATION_PERMISSIONS or APPODEAL_FULL_PERMISSIONS to increase permissions. Basic permissions are default.

```
#APPODEAL_LOCATION_PERMISSIONS="true"
#APPODEAL_FULL_PERMISSIONS="true"
```

## Permissions

### Basic Permissions

+ android.permission.ACCESS_NETWORK_STATE

+ android.permission.INTERNET

### #APPODEAL_LOCATION_PERMISSIONS

LOCATION permissions include all Basic permissions plus the following: 

+ android.permission.ACCESS_COARSE_LOCATION

+ android.permission.ACCESS_FINE_LOCATION

+ android.permission.WRITE_EXTERNAL_STORAGE

### #APPODEAL_FULL_PERMISSIONS

FULL permissions include all Location permissions plus the following: 

+ android.permission.ACCESS_WIFI_STATE

+ android.permission.VIBRATE

## AMAZON App Store

If compiling for the amazon app store, assign #APPODEAL_AMAZON="yes". This will remove some of the ad providors from Appodeal plugin required by the Amazon App Store. If compiling for the Google Play store, remove that line. Any assignment to APPODEAL_AMAZON will remove these providors.

## Compatibility
Tested with CerburusX version V2019-10-13 and Appodeal 2.6.2

You minimum API level will be raised to API 21. This is to fix the multidexing issues.

## Manual ammedments to files

Compile your application and then ammend these files. This only has to be done once (unless you remove your build folder)

### file: build.folder\android\gradletemplate\build.gradle

change dependencies block to:
```
    dependencies {
        classpath 'com.android.tools.build:gradle:3.5.2'
    }
```
change maven block to:
```
        maven {
            url "https://maven.google.com"
            url "https://artifactory.appodeal.com/appodeal"
        }
```

### file: build.folder\android\gradletemplate\gradle\wrapper\gradle-wrapper.properties

change distributionUrl line:
```
distributionUrl=https\://services.gradle.org/distributions/gradle-5.4.1-all.zip
```

### file: build.folder\android\gradletemplate\app\build.gradle

change:
```
android {
    compileSdkVersion ${ANDROID_TARGET_SDK_VERSION}
    buildToolsVersion "${ANDROID_BUILD_TOOLS_VERSION}"
```
to:
```
android {
    compileSdkVersion ${ANDROID_TARGET_SDK_VERSION}
    buildToolsVersion "${ANDROID_BUILD_TOOLS_VERSION}"

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```

### file: android\gradletemplate\app\src\main\AndroidManifest.xml

change:
```
  <application
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="${ANDROID_APP_LABEL}"
    android:roundIcon="@mipmap/ic_launcher_round">
```
to:
```
  <application
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="${ANDROID_APP_LABEL}"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:networkSecurityConfig="@xml/network_security_config">
```

### file: build.folder\android\app\src\main\res\xml\network_security_config.xml

create this file. contents:
```
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
```

Example:
```
Strict

#ADMOB_APP_ID="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"

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
```

# Method Initialise:Void(AppKey:String, AdTypes:Int)

Initialises the Appodeal plugin. Use the app key from the dashboard.

If compiled in debug mode, only test adverts will appear.

Adtypes is a combination of the type of adverts you want it your app, for example Appodeal.BANNERTOP | Appodeal.INTERSTITIAL

By default GDPR consent is set to false so user detials arent tracked.

# Method IsLoaded:Bool(AdType:Int)

Check to see if an advert has loaded and ready for display. AdType is a single advert type.

# Method Show:Void(AdType:Int)

Show an advert of AdType

# Method Hide:Void(AdType:Int)

Hide an advert of AdType

# Method GetGDPRConsent:Bool()

Returns if the user has enabled GDPR consent when app first initialised

# Method EnableGDPRConsent:Void()

If you have permission from the user, you can Enable GDPR consent. 

# Method DisableGDPRConsent:Void()

Disable GDPR consent. 

# Method EnableCOPPA:Void()

If the user has Child settings in the OS, this will enable child friendly advertising in Appodeal.
This is the default option.

# Method DisableCOPPA:Void()

If the user has Child settings in the OS, this will disable child friendly advertising in Appodeal.

# Method IgnoreCOPPA:Void()

This will ignore any child friendly settings in Appodeal.


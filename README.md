# Module appodeal

The Appodeal module provides simple support for the Appodeal ad service allowing you to display adverts.

(Note: AppodealCX plugin is only currently available on the android target)

V1.4 04-04-2021

+ Updated Appodeal lib to 2.8.1
+ Added custom Appodeal ad providor exclusions
+ Removed old manual integration from docs. See readme here for manual instructions: https://github.com/pantson/pantson-cerberusx-appodeal/blob/201d81be3876ad6fd7428ed121eec9690f643420/README.md
+ *New manaul integration required for upgrade to AndroidX*

V1.3 27-06-2020

+ Updated Appodeal lib to 2.7.1
+ Fixed Appodeal logging
+ Removed ironsource ads from Package due to clash in manifest file

V1.2.2 29-02-2020

+ Added APPODEAL_AMAZON
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

## Class appodeal.Appodeal

The Appodeal module provides simple support for the Appodeal ad service allowing you to display adverts.

To use Appodeal in your game:

+ Get your APP_KEY from the Appodeal dashboard

+ Import the Appodeal module into your game

+ Include APPODEAL_LOCATION_PERMISSIONS or APPODEAL_FULL_PERMISSIONS to increase permissions. Basic permissions are default.

```cerberusx
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
Tested with CerburusX version V2020-12-05 and Appodeal 2.8.1

You minimum API level will be raised to API 28. This is to fix the multidexing issues.

## Manual integration

On first compilation this will fail. Do not panic.

Open up your build folder and navigate to android/gradletemplate

Open up file gradle.properties

The content of this file will just be a huge comment. Add the following to the end of the line

```cerberusx
android.useAndroidX=true
android.enableJetifier=true
```

Save this and compile again. Integration complete!

Example:
```cerberusx
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

<a name="appodeal.Appodeal.Initialise"></a>
### Method Initialise:Void(AppKey:String, AdTypes:Int)

Initialises the Appodeal plugin. Use the app key from the dashboard.

If compiled in debug mode, only test adverts will appear.

Adtypes is a combination of the type of adverts you want it your app, for example Appodeal.BANNERTOP | Appodeal.INTERSTITIAL

By default GDPR consent is set to false so user detials arent tracked.

<a name="appodeal.Appodeal.IsLoaded"></a>
### Method IsLoaded:Bool(AdType:Int)

Check to see if an advert has loaded and ready for display. AdType is a single advert type.

<a name="appodeal.Appodeal.Show"></a>
### Method Show:Void(AdType:Int)

Show an advert of AdType

<a name="appodeal.Appodeal.Hide"></a>
### Method Hide:Void(AdType:Int)

Hide an advert of AdType

<a name="appodeal.Appodeal.GetGDPRConsent"></a>
### Method GetGDPRConsent:Bool()

Returns if the user has enabled GDPR consent when app first initialised

<a name="appodeal.Appodeal.EnableGDPRConsent"></a>
### Method EnableGDPRConsent:Void()

If you have permission from the user, you can Enable GDPR consent.

<a name="appodeal.Appodeal.DisableGDPRConsent"></a>
### Method DisableGDPRConsent:Void()

Disable GDPR consent.

<a name="appodeal.Appodeal.EnableCOPPA"></a>
### Method EnableCOPPA:Void()

If the user has Child settings in the OS, this will enable child friendly advertising in Appodeal.
This is the default option.

<a name="appodeal.Appodeal.DisableCOPPA"></a>
### Method DisableCOPPA:Void()

If the user has Child settings in the OS, this will disable child friendly advertising in Appodeal. Not recommended.

<a name="appodeal.Appodeal.IgnoreCOPPA"></a>
### Method IgnoreCOPPA:Void()

This will ignore any child **friendly** settings in Appodeal. Not recommended.

<a name="appodeal.Appodeal.EnableLogging"></a>
### Method EnableLogging:Void()

This will enable **Appodeal** logging. In Debug mode this is enabled by default.

#Method DisableLogging:Void()

This will disable Appodeal logging.

Links [appodeal.Appodeal.Initialise](#appodeal.Appodeal.Initialise)

<a name="appodeal.Appodeal.EnableVerboseLogging"></a>
### Method EnableVerboseLogging:Void()

This will enable Appodeal logging in verbose mode.

<a name="appodeal.Appodeal.EnableTestAds"></a>
### Method EnableTestAds:Void()

Enable displaying of test adverts only. This is enable by default in DEBUG mode

<a name="appodeal.Appodeal.DisableTestAds"></a>
### Method DisableTestAds:Void()

Disables test ads

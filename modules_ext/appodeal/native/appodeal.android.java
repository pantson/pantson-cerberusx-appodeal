import android.util.Log;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.Native;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeAdView;
import com.appodeal.ads.UserSettings;
//import com.appodeal.ads.utils.Log;

import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentForm;
import com.explorestack.consent.ConsentFormListener;
import com.explorestack.consent.ConsentInfoUpdateListener;
import com.explorestack.consent.ConsentManager;
import com.explorestack.consent.exception.ConsentManagerException;

class BBAppodeal {
	
	private boolean _adconsent;
	static BBAppodeal appodeal;
	Activity activity = BBAndroidGame._androidGame._activity;
	private ConsentForm consentForm;
	private int adTypes;
	private String appKey;
	
	static public BBAppodeal GetAppodeal(){
		if(appodeal == null) appodeal = new BBAppodeal();
		return appodeal;
	}
	
	public void initialise(String APP_KEY, int adType){
		adTypes = adType;
		appKey = APP_KEY;
		
		enablePermissions();
		/*
		Requesting Consent from European Users
		*/
		ConsentManager consentManager = ConsentManager.getInstance(activity);
		consentManager.requestConsentInfoUpdate(appKey, new ConsentInfoUpdateListener() {
			@Override
			public void onConsentInfoUpdated(Consent consent) {
				Log.d("Appodeal[Consent]", "onConsentInfoUpdated");
				if (consentManager.shouldShowConsentDialog() == Consent.ShouldShow.TRUE) {
					loadConsentForm();
				} else {
					Start(consent.getStatus() == Consent.Status.PERSONALIZED);
				}
			}

			@Override
			public void onFailedToUpdateConsentInfo(ConsentManagerException e) {
				Log.d("Appodeal", "onFailedToUpdateConsentInfo - " + e.getReason() + " " + e.getCode());
			}
		});
		
	}

	private void loadConsentForm() {
		consentForm = new ConsentForm.Builder(activity)
		.withListener(new ConsentFormListener() {
			@Override
			public void onConsentFormLoaded() {
				Log.d("Appodeal[Consent]", "onConsentFormLoaded");
				consentForm.showAsActivity();
				
			}

			@Override
			public void onConsentFormError(ConsentManagerException e) {
				Log.d("Appodeal[Consent]", "ConsentManagerException - "
				+ e.getReason() + " " + e.getCode());
			}

			@Override
			public void onConsentFormOpened() {
				Log.d("Appodeal[Consent]", "onConsentFormOpened");
			}

			@Override
			public void onConsentFormClosed(Consent consent) {
				Log.d("Appodeal[Consent]", "onConsentFormClosed");
				Start(consent.getStatus() == Consent.Status.PERSONALIZED);
			}
		}).build();
		consentForm.load();
	}	
	
	private void Start(boolean consent) {
		Appodeal.disableLocationPermissionCheck();
		Appodeal.disableWriteExternalStoragePermissionCheck();
		Appodeal.initialize(activity, appKey, adTypes, consent);
		_adconsent = consent;
		updateCOPPA(true);
	}

	public void show(int adType){
		Appodeal.show(activity, adType);
	}
	
	public void hide(int adType){
		Appodeal.hide(activity, adType);
	}

	public boolean show(int adType, String placement){
		return Appodeal.show(activity, adType, placement);
	}

	public void updateGDPRconsent(boolean value) {
		Appodeal.updateConsent(value);
	}
	
	public boolean getGDPRconsent() {
		return _adconsent;
	}
	public boolean isLoaded(int adType){
		return Appodeal.isLoaded(adType);
	}
	
	public void enableLogging(){
		Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.debug);
	}
	
	public void disableLogging(){
		Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.none);
	}

	public void enableVerboseLogging(){
		Appodeal.setLogLevel(com.appodeal.ads.utils.Log.LogLevel.verbose);
	}

	public void setTesting(boolean state){
		Appodeal.setTesting(state);
	}

	public void updateCOPPA(boolean value) {
		Appodeal.setChildDirectedTreatment(value);
	}
	
	public void ignoreCOPPA() {
		Appodeal.setChildDirectedTreatment(null);
	}
	
	private void enablePermissions() {
	}
}
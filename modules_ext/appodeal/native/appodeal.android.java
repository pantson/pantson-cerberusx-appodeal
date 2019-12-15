import com.appodeal.ads.Appodeal;

class BBAppodeal {
	
	static BBAppodeal appodeal;
	Activity activity = BBAndroidGame._androidGame._activity;
	
	static public BBAppodeal GetAppodeal(){
		if(appodeal == null) appodeal = new BBAppodeal();
		return appodeal;
	}
	
	public void initialise(String appKey, int adType){
		Appodeal.disableLocationPermissionCheck();
		Appodeal.disableWriteExternalStoragePermissionCheck();
		Appodeal.initialize(activity, appKey, adType, false);
		updateCOPPA(true);
	}
	
	public void show(int adType){
		Appodeal.show(activity, adType);
 	}
	
	public boolean show(int adType, String placement){
		return Appodeal.show(activity, adType, placement);
	}

	public void updateGDPRconsent(boolean value) {
		Appodeal.updateConsent(value);
	}
		
	public boolean isLoaded(int adType){
		return Appodeal.isLoaded(adType);
	}
	
	public void enableLogging(){
//		Appodeal.setLogLevel(Log.LogLevel.debug);
	}
	
	public void disableLogging(){
//		Appodeal.setLogLevel(Log.LogLevel.none);
	}

	public void enableVerboseLogging(){
//		Appodeal.setLogLevel(Log.LogLevel.verbose);
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
}
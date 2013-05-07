package com.java.mat;

public final class GlobalSettings {
	
    private final static GlobalSettings ourInstance = new GlobalSettings();
    
    /* private ingredients section */
    private boolean logged = false;
    private boolean roaming = false;
    private boolean compression = true;
    private boolean showSth = false;
    private int visibilityRadius = 0;
    
    /* getter/setter section */
    public boolean getUserLoggingStatus(){
    	return logged;
    }
    public void setUserLoggedStatus(boolean value){
    	logged = value;
    }
    
    public boolean getRoamingStatus(){
    	return roaming;
    }
    public void setRoamingStatus(boolean value){
    	roaming = value;
    }
    
    public boolean getCompressionStatus(){
    	return compression;
    }
    public void setCompressionStatus(boolean value){
    	compression = value;
    }
    
    public boolean getShowSthStatus(){
    	return showSth;
    }
    public void setShowSthStatus(boolean value){
    	showSth = value;
    }
    
    
    /* internal singleton implementation */
    public static GlobalSettings getInstance() {
        return ourInstance;
    }
 
    private GlobalSettings() {
    }
	public int getVisibilityRadius() {
		return visibilityRadius;
	}
	public void setVisibilityRadius(int visibilityRadius) {
		this.visibilityRadius = visibilityRadius;
	}
}
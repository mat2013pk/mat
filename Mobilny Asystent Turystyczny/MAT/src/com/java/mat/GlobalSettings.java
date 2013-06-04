package com.java.mat;

public final class GlobalSettings {
	
    private final static GlobalSettings ourInstance = new GlobalSettings();
    
    /* private ingredients section */
    private boolean logged = false;
    private boolean roaming = false;
    private boolean compression = true;
    private boolean showSth = false;
    private boolean messageStatus = false;
    private int visibilityRadius = 0;
    private final String host = "http://mat.sofect.com/server.php?key=412fg68kw378";
    private final String secretKey = "412fg68kw378";
    private String mail = "k@k.com";
    private String guardMail = "k@k.com";
    
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
	public String getHost() {
		return host;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public String getGuardMail() {
		return guardMail;
	}
	public void setGuardMail(String guardMail) {
		this.guardMail = guardMail;
	}
	public boolean isMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(boolean messageStatus) {
		this.messageStatus = messageStatus;
	}
}
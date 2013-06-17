package com.java.mat;

import android.os.StrictMode;

public class StrictModeWrapper {
	   static {
	       try {
	           Class.forName("android.os.StrictMode", true, Thread.currentThread().getContextClassLoader());
	       } catch (Exception ex) {
	           throw new RuntimeException(ex);
	       }
	   }
	 
	   public static void checkAvailable() {}
	 
	   public static void enableDefaults() {
	       StrictMode.enableDefaults();
	    }
}
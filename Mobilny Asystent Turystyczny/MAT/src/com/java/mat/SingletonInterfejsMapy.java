package com.java.mat;

import android.support.v4.app.Fragment;
import android.util.Log;

public class SingletonInterfejsMapy {
	private static InterfejsDlaMapy instance= null;
	public static InterfejsDlaMapy getInstance()
	{
		if(instance == null)
		{
			instance = new InterfejsDlaMapy();
		};
		
		return instance;
		
	}
	private SingletonInterfejsMapy(){}
}



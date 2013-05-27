package com.java.mat;

public class InterfejsDlaMapy
{
	protected boolean trasa, znajomi, pinezki, miejsca;
	protected Mapa mapa;
	
	private static InterfejsDlaMapy instance= null;
	public static InterfejsDlaMapy getInstance()
	{
		if(instance == null)
		{
			instance = new InterfejsDlaMapy();
		};
		
		return instance;
		
	}
	
	private InterfejsDlaMapy()
	{
		trasa = true;
		znajomi= true;
		pinezki= true;
		miejsca= true;
	}
	public Mapa getMapInstance(){ return mapa; }
	public void setMapa(Mapa mapa){ this.mapa = mapa;}
	public void setTrasa(boolean val)  { trasa = val;   }
	public void setZnajomi(boolean val){ znajomi = val; }
	public void setPinezki(boolean val){ pinezki = val; }
	public void setMiejsca(boolean val){ miejsca = val; }
}

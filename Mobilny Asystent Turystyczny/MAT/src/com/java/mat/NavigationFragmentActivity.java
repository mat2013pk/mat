package com.java.mat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class NavigationFragmentActivity extends Fragment {
	
	InterfejsDlaMapy ustawienia;
	private Mapa map;
	
	
	
	public void onHiddenChanged(boolean hidden)
    {	
		
		ustawienia.setWyznaczanieTrasy(((ToggleButton)this.getActivity().findViewById(R.id.trasaOnOff)).isChecked());
		ustawienia.setDodawaniePinezek(((ToggleButton)this.getActivity().findViewById(R.id.pinezkiPut)).isChecked());
		
		
		RadioGroup bu = ((RadioGroup)this.getActivity().findViewById(R.id.radioGroup1));
		if(bu.getCheckedRadioButtonId()==0x7f040038)
		{
			ustawienia.setTrybTrasa(Trasa.Mode.walking);
			Log.d("userD", "z buta");
		}else
		{
			ustawienia.setTrybTrasa(Trasa.Mode.driving);
			Log.d("userD", "jazda");
		}

        ustawienia.setZnajomi(((CheckBox) this.getActivity().findViewById(R.id.chgrupa)).isChecked());
        ustawienia.setPinezki(((CheckBox) this.getActivity().findViewById(R.id.chpinezki)).isChecked());
        ustawienia.setTrasa(((CheckBox) this.getActivity().findViewById(R.id.chtrasa)).isChecked());
        ustawienia.setMiejsca(((CheckBox) this.getActivity().findViewById(R.id.chmiejsca)).isChecked());
        
		super.onHiddenChanged(hidden);
    }
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       
       
        if (container == null) {
            return null;
        }
        ustawienia = SingletonInterfejsMapy.getInstance();
        map = SingletonInterfejsMapy.getInstance().getMapInstance();
        
        
        
      map.getInstancjeMapyGoogle().setOnMapLongClickListener
      (
    	new  GoogleMap.OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng arg0) {
				if(ustawienia.dodajPinezke)
				{
					map.getPinezki().dodajPinezkeDoListy(arg0, "Moja Pinezka");
					map.getPinezki().dodajPinezkiDoMapy();
				}
				
			}
		}
      ); //wlaczenie lisnera na mape  dodanie w³asnej pinezki
        
        return (LinearLayout)inflater.inflate(R.layout.activity_tab02_nawigacja_zalog, container, false);

    }
}
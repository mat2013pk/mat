package com.java.mat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class NavigationFragmentActivity extends Fragment {
	
	InterfejsDlaMapy ustawienia;
	
	public void onHiddenChanged(boolean hidden)
    {	

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
        ustawienia = InterfejsDlaMapy.getInstance();
        
        return (LinearLayout)inflater.inflate(R.layout.activity_tab02_nawigacja_zalog, container, false);

    }
}
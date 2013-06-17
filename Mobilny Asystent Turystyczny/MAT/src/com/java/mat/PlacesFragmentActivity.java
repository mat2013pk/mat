package com.java.mat;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Parser;
import org.xml.sax.helpers.ParserAdapter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class PlacesFragmentActivity extends Fragment {

	InterfejsDlaMapy interfejs;
	String array_spinner2[] = 
		{
			"Rachunkowo��",
			"Lotnisko",
			"Weso�e miasteczko",
			"Basen",
			"Muzeum",
			"Bankomat",
			"Piekarnia",
			"Bank",
			"Bar",
			"Salon pi�kno�ci",
			"Wypo�yczalnia rower�w",
			"Ksi�garnia",
			"Kr�gielnia",
			"Przystanek autobusowy",
			"Kawiarnia",
			"Pole namiotowe",
			"Salon samochodowy",
			"Wypo�yczalnia samochod�w",
			"Warsztat samochodowy",
			"Myjnia",
			"Kasyno",
			"Cmentarz",
			"Ko�ci�",
			"Ratusz",
			"Sklep z ciuchami",
			"Sklep spo�ywczy",
			"S�d",
			"Dentysta",
			"Dom towarowy",
			"Doktor",
			"Elektryczny",
			"Elektroniczny",
			"Ambasada",
			"Urz�d",
			"Finanse",
			"Stra� po�arna",
			"Kwiaciarnia",
			"Knajpa z jedzeniem",
			"Zak�ad pogrzebowy",
			"Sklep z meblami",
			"Stacji gazu",
			"Generalny wykonawca",
			"Warzywniak",
			"Si�ownia",
			"Fryzjer",
			"Sklep komputerowy",
			"Przychodnia",
			"�wi�tynia",
			"Sprz�t gospodarstwa domowego",
			"Szpital",
			"Agencja ubezpieczeniowa",
			"Jubiler",
			"Pralnia",
			"Adwokat",
			"Biblioteka",
			"Monopolowy",
			"Lokalne biuro rz�dowe",
			"�lusarz",
			"Noclegi",
			"Jedzenie na zam�wienie",
			"Bar szybkiej obs�ugi",
			"Meczet",
			"Wypo�yczalnia film�w",
			"Kino",
			"Firma przeprowadzkowa",
			"Muzeum",
			"Klub nocny",
			"Malarz",
			"Park",
			"Parking",
			"Zwierz�cy",
			"Apteka",
			"Fizjoterapeuta",
			"Miejsce kultu",
			"Hydraulik",
			"Policja",
			"Poczta",
			"Agencja nieruchomo�ci",
			"Restauracja",
			"Dekarz",
			"RV Park",
			"Szko�a",
			"Obuwniczy",
			"Centrum handlowe",
			"SPA",
			"Stadion",
			"Magazyn",
			"Przechowalnia",
			"Stacja metra",
			"Synagoga",
			"Post�j taks�wek",
			"Peron",
			"Biuro podr�y",
			"Uczelnia",
			"Weterynarz",
			"ZOO"
		};
    String array_spinner[] = {
    		"accounting",
    		"airport",
    		"amusement_park",
    		"aquarium",
    		"art_gallery",
    		"atm",
    		"bakery",
    		"bank",
    		"bar",
    		"beauty_salon",
    		"bicycle_store",
    		"book_store",
    		"bowling_alley",
    		"bus_station",
    		"cafe",
    		"campground",
    		"car_dealer",
    		"car_rental",
    		"car_repair",
    		"car_wash",
    		"casino",
    		"cemetery",
    		"church",
    		"city_hall",
    		"clothing_store",
    		"convenience_store",
    		"courthouse",
    		"dentist",
    		"department_store",
    		"doctor",
    		"electrician",
    		"electronics_store",
    		"embassy",
    		"establishment",
    		"finance",
    		"fire_station",
    		"florist",
    		"food",
    		"funeral_home",
    		"furniture_store",
    		"gas_station",
    		"general_contractor",
    		"grocery_or_supermarket",
    		"gym",
    		"hair_care",
    		"hardware_store",
    		"health",
    		"hindu_temple",
    		"home_goods_store",
    		"hospital",
    		"insurance_agency",
    		"jewelry_store",
    		"laundry",
    		"lawyer",
    		"library",
    		"liquor_store",
    		"local_government_office",
    		"locksmith",
    		"lodging",
    		"meal_delivery",
    		"meal_takeaway",
    		"mosque",
    		"movie_rental",
    		"movie_theater",
    		"moving_company",
    		"museum",
    		"night_club",
    		"painter",
    		"park",
    		"parking",
    		"pet_store",
    		"pharmacy",
    		"physiotherapist",
    		"place_of_worship",
    		"plumber",
    		"police",
    		"post_office",
    		"real_estate_agency",
    		"restaurant",
    		"roofing_contractor",
    		"rv_park",
    		"school",
    		"shoe_store",
    		"shopping_mall",
    		"spa",
    		"stadium",
    		"storage",
    		"store",
    		"subway_station",
    		"synagogue",
    		"taxi_stand",
    		"train_station",
    		"travel_agency",
    		"university",
    		"veterinary_care",
    		"zoo"
    };
	
    public void onHiddenChanged(boolean hidden)
    {
    	//false gdy jestem w niej 
    	Log.d("userDebuggggg","zak�adka ukryta:"+ hidden); 
   
    	super.onHiddenChanged(hidden);
    }
	
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	

        if (container == null) {
            return null;
        }
        
        final View tmp =  inflater.inflate(R.layout.activity_tab04_miejsca, container, false);
        interfejs = SingletonInterfejsMapy.getInstance();
        
        Spinner s = (Spinner) tmp.findViewById(R.id.spinner1);
        ArrayAdapter adapter = new ArrayAdapter(
        		this.getActivity(),
        		//android.R.layout.simple_spinner_item,
        		R.layout.moj_spiner,
        		array_spinner2);
      
        s.setAdapter(adapter);
      
        ((TextView)tmp.findViewById(R.id.wynikiZnalezione)).setText("Ilo�� znalezionych miejsc: "+interfejs.getMapInstance().getMiejsca().getIloscMiejsc());
        
        final Button button = (Button)tmp.findViewById(R.id.buttonSzukaj);
        

        
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                    	interfejs.getMapInstance().getMiejsca().wyczyscMiejsca();
                		int promien =0;
                		try
                		{
                			promien = Integer.parseInt(((EditText)tmp.findViewById(R.id.promien)).getText().toString());
                		}
                		catch(Exception e)
                		{
                			promien = 100;
                		}
                		LatLng pozycja;
                		
                		try
                		{
                			pozycja = new LatLng(interfejs.getMapInstance().getMojaPozycja().getLatitude(), interfejs.getMapInstance().getMojaPozycja().getLongitude());
                		}catch(Exception e)
                		{
                			pozycja = new LatLng(50.061664,19.937217);															//dodanie pozycji wzgledem kt�rej b�dze okre�lane
                			Log.d("userDebuggggg","pozycja wyjatek"); 
                			((TextView)tmp.findViewById(R.id.wynikiZnalezione)).setText("Ilo�� znalezionych miejsc: 0 Nie okre�lono Twojej pozycji");
                			
                			return;
                		}
                		
                		    		
                		interfejs.getMapInstance().getMiejsca().getPlaces(
                				pozycja, 
                				array_spinner[((Spinner) tmp.findViewById(R.id.spinner1)).getSelectedItemPosition()], 
                				promien
                				);
                		((TextView)tmp.findViewById(R.id.wynikiZnalezione)).setText("Ilo�� znalezionych miejsc: "+interfejs.getMapInstance().getMiejsca().getIloscMiejsc());

                    }
                }
            );
 


        

        
        return (LinearLayout)tmp;
    }
}
package message;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class MsgList {
	// Klasa przechowuj�ca list� wiadomo��i oraz metody do jej obs�ugi
	private int last_index, last_index_used;

	private ArrayList<Msg> msg;

	public MsgList() {
		last_index = -1;
		last_index_used = -1;
		msg = new ArrayList<Msg>();
	}

	// funkcja zwraca obiekt ostatniej wiadomo�ci, kt�ra nie zosta�a jeszcze
	// przeczytana, lub ostatniej na li�cie(je�eli wszystkie zosta�y
	// przeczytane) w przypadku braku wiadomo��i zwracane null
	public Msg getMsg() {
		if (last_index == last_index_used
				|| msg.get(msg.size() - 1).getid() <= last_index_used) {
			return msg.get(msg.size() - 1);
		} else {
			Msg tmp;
			while (last_index_used < last_index) {
				last_index_used++;
				tmp = getMsgByIndex(last_index_used);
				if (tmp != null)
					return tmp;
			}
		}
		return null;
	}

	// funkcja dodaj�ca odebran� wiadomo��, wraz z ewentualnym adresem pliku do
	// listy
	public void additem(String text, String addres, String email) {
		last_index++;
		Msg tmp = new Msg(last_index, text, addres, email);
		Log.e("add", "Dodaje wiadomosc");
		msg.add(tmp);
	}

	// funkcja zwracaj�ca id ostatniej wiadomo�ci, kt�re s� nadawane po jej
	// odebraniu, nie musi bezpo�rednio odzwierciedla� lokacji wiadomo�ci na
	// li�cie, je�eli Last index wynosi -1 lista zosta�a dopiero utworzona
	public int getLastIndex() {
		return last_index;
	}

	// funkcja zwracaj�ca d�ugo�� listy, je�eli 0 lista jest pusta
	public int getSize() {
		return msg.size();
	}

	// funkcja zwraca obiekt z wiadomo�ci� po podaniu numeru id, w przypadku jej
	// braku funkcja zwraca null
	public Msg getMsgByIndex(int id) {
		for (int i = 0; i < msg.size(); i++) {
			if (msg.get(i).getid() == id)
				return msg.get(i);
		}
		return null;
	}

	// funkcja zwracaj�ca ca�� list� obiekt�w przechowuj�cych wiadomo�ci, w
	// postaci tablicy tych obiekt�w
	public ArrayList<Msg> getMsgAll() {
		ArrayList<Msg> tmp = new ArrayList<Msg>();
		//Log.e("ILOSC WIADOMOSCI MSG:", " " + msg.size());
		for (int i = 0; i < msg.size(); i++) {
			//tmp[i] = msg.get(i);
			tmp.add( msg.get(i));
		}
		//Log.e("ILOSC WIADOMOSCI TMP:", " " + tmp.size());
		return tmp;
	}

	// funkcja usuwaj�ca z listy wiadomo�� o podanym indexie z listy, je�li
	// zosta�a usuni�ta zwracane 0, je�li nie zosta�a znaleziona zwracane jest
	// -1
	public int rmvMsgByIndex(int id) {
		for (int i = 0; i < msg.size(); i++) {
			if (msg.get(i).getid() == id) {
				msg.remove(i);
				return 0;
			}
		}
		return -1;
	}

	// funkcja zwraca i-t� wiadomo�� z listy
	public Msg getMsgByListNumBer(int i) {
		if (i >= msg.size())
			return null;
		return msg.get(i);
	}

	// funkcja
	public int rmvMsgByListNumBer(int i) {
		if (i >= msg.size())
			return -1;
		msg.remove(i);
		return 0;
	}

}

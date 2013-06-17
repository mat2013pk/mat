package message;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class MsgList {
	// Klasa przechowuj¹ca listê wiadomoœæi oraz metody do jej obs³ugi
	private int last_index, last_index_used;

	private ArrayList<Msg> msg;

	public MsgList() {
		last_index = -1;
		last_index_used = -1;
		msg = new ArrayList<Msg>();
	}

	// funkcja zwraca obiekt ostatniej wiadomoœci, która nie zosta³a jeszcze
	// przeczytana, lub ostatniej na liœcie(je¿eli wszystkie zosta³y
	// przeczytane) w przypadku braku wiadomoœæi zwracane null
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

	// funkcja dodaj¹ca odebran¹ wiadomoœæ, wraz z ewentualnym adresem pliku do
	// listy
	public void additem(String text, String addres, String email) {
		last_index++;
		Msg tmp = new Msg(last_index, text, addres, email);
		Log.e("add", "Dodaje wiadomosc");
		msg.add(tmp);
	}

	// funkcja zwracaj¹ca id ostatniej wiadomoœci, które s¹ nadawane po jej
	// odebraniu, nie musi bezpoœrednio odzwierciedlaæ lokacji wiadomoœci na
	// liœcie, je¿eli Last index wynosi -1 lista zosta³a dopiero utworzona
	public int getLastIndex() {
		return last_index;
	}

	// funkcja zwracaj¹ca d³ugoœæ listy, je¿eli 0 lista jest pusta
	public int getSize() {
		return msg.size();
	}

	// funkcja zwraca obiekt z wiadomoœci¹ po podaniu numeru id, w przypadku jej
	// braku funkcja zwraca null
	public Msg getMsgByIndex(int id) {
		for (int i = 0; i < msg.size(); i++) {
			if (msg.get(i).getid() == id)
				return msg.get(i);
		}
		return null;
	}

	// funkcja zwracaj¹ca ca³¹ listê obiektów przechowuj¹cych wiadomoœci, w
	// postaci tablicy tych obiektów
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

	// funkcja usuwaj¹ca z listy wiadomoœæ o podanym indexie z listy, jeœli
	// zosta³a usuniêta zwracane 0, jeœli nie zosta³a znaleziona zwracane jest
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

	// funkcja zwraca i-t¹ wiadomoœæ z listy
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

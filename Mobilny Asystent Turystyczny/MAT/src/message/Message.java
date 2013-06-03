package message;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import org.json.JSONException;
import org.json.JSONObject;

public class Message implements IMessage {
	private String code = null, msg = null, file = null;
	Bitmap tmpBitmap;
	HttpClient client = new DefaultHttpClient();
	HttpGet request = new HttpGet();
	HttpResponse response = null;

	public String getCode() {
		return code;
	}

	private void resetCode() {
		code = "-1";
		response = null;
	}

	public String sendMsg(String key, String email, String emailRecv,
			String text, String filePath, Boolean compression) {
		// ------------------------------------------------------------------------------------------
		// funkcje odpowiedzialne za wysy�anie wiadomo�ci
		resetCode();

		// to kodowanie jest potrzebne gdy w gecie s� znaki specjalne np. spacje
		try {
			text = URLEncoder.encode(text, "UTF8");
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		}

		String uri = "http://mat.sofect.com/server.php?" + "&key=" + key
				+ "&email=" + email + "&msg=" + text;

		// adresat konkretny
		if (emailRecv != null)
			uri += "&function=sendMsg" + "&emailRecv=" + emailRecv;
		// lub adresat grupowy
		else
			uri += "&function=sendMsgToAll";

		// Wiadomosci bez obrazka �migaj�

		// czy wysy�amy obrazek
		if (filePath != null) {
			// to jest jeszcze niesko�czone

			// Bitmap tmpBitmap;
			// //czy kompresujemy
			// if(compression){
			// tmpBitmap = resizeAndCompressImage(filePath, 2);
			// }else
			// tmpBitmap = resizeAndCompressImage(filePath, 1);
			//
			// string adresPliku = uploadImage(tmpBitmap);
			// uri += "&file=" + adresPliku;
		}

		return useURI(uri);
	}

	private String uploadImage(String sciezkaPliku) {
		HttpPost request2Upload = new HttpPost(
				"http://mat.sofect.com/upload.php?");
		HttpContext localContext = new BasicHttpContext();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("image", sciezkaPliku));

		StringBuilder sb = new StringBuilder();
		// UrlEncodedFormEntity entity = new
		// UrlEncodedFormEntity(nameValuePairs);
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		// entity.addPart("image", new FileBody(new File(sciezkaPliku) ));

		// request2Upload.setEntity(entity);

		try {
			response = client.execute(request2Upload, localContext);
		} catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
		InputStream data;
		try {
			data = response.getEntity().getContent();

			InputStreamReader reader = new InputStreamReader(data);
			BufferedReader buffer = new BufferedReader(reader);

			String cur;
			while ((cur = buffer.readLine()) != null)
				sb.append(cur + "\n");

			data.close();
		} catch (IllegalStateException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}

		return sb.toString();
	}

	private static Bitmap resizeAndCompressImage(String filePath, int scale) {
		int sampleSize = 2;
		Bitmap bitmap = null;
		ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = scale;// 2; //zmniejszenie ilo�ci linii pionowych
										// i poziomych dwukrotnie=>rozdzielczo��
										// zmniejszona 4krotnie

		while (true)// zmniejszanie rozdzielczo�ci w przypadku zbyt du�ej
					// rozdzielczo�ci
		{
			try {
				options.inSampleSize = sampleSize;
				bitmap = BitmapFactory.decodeFile(filePath, options);
				break;
			} catch (Exception ex) {
				sampleSize *= sampleSize;
			}
		}
		if (bitmap.compress(Bitmap.CompressFormat.JPEG, 60, imageStream))// kompresja
																			// bitmapy
																			// do
																			// strumienia
			bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(
					imageStream.toByteArray()));

		return bitmap;
	}

	private String useURI(String uri) {

		resetCode();

		// wcze�niejsze kodowanie wymusza tu takie przechwytywanie wyj�tk�w
		try {
			request.setURI(new URI(uri));
		} catch (URISyntaxException e) {

			return e.getMessage();
		}

		try {
			response = client.execute(request);
			code = Parser.parseOnlyCode(response);
			return code;
		} catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	// ---------------------------------------------------------------------------------------
	// funkcje odpowiedzialne za odbieranie wiadomo�ci
	private MsgList lista = new MsgList();

	// funkcja pobiera wiadomo�� z serwera i zapisuje j� na li�cie, w przypadku
	// gdy wszystko przebieg�o pomy�lnie zwraca "0"
	public String recvMsg(String key, String email) {

		String uri = "http://mat.sofect.com/server.php?" + "&key=" + key
				+ "&function=recvMsg" + "&email=" + email;

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(uri);
		HttpResponse response = null;

		try {
			response = client.execute(request);
			InputStream in = response.getEntity().getContent();

			code = null;
			msg = null;
			file = null;
			StringBuilder sb = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader buffer = new BufferedReader(reader);
			String cur;
			while ((cur = buffer.readLine()) != null)
				sb.append(cur + "\n");

			// parsowanie JSONa
			try {
				JSONObject json = new JSONObject(sb.toString());
				code = json.getString("code");
				msg = json.getString("msg");
				file = json.getString("file");
			} catch (JSONException e) {
				return e.getMessage();
			}
			if (code != "0")
				return code;
			lista.additem(msg, file);
			return "0";
		} catch (ClientProtocolException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}

	}

	// funkcja zwracaj�ca ostatni� nieprzeczytan� wiadomo��, je�eli takich brak,
	// to zwraca ostatni� na li�cie
	public Msg getMsg() {
		return lista.getMsg();
	}

	// funkcja zwraca wiadomo�� o odpowiednim id
	public Msg getMsgByIndex(int id) {
		return lista.getMsgByIndex(id);
	}

	// funkcja zwraca tablic� z wszystkimi wiadomo�ciami
	public Msg[] getMsgAll() {
		return lista.getMsgAll();
	}

	// funkcja zwraca wiadomo�� znajduj�c� si� na miejscu i na li�cie(numeracja
	// od 0 do size-1
	public Msg getMsgByListNumBer(int i) {
		return lista.getMsgByListNumBer(i);
	}

	// funkcja usuwa wiadomo�� o podanym id, je�eli takiej nie ma to zwracane
	// jest -1
	public int rmvMsgByIndex(int id) {
		return lista.rmvMsgByIndex(id);
	}

	// funkcja usuwa wiadomo�� z miejsca i na li�cie
	public int rmvMsgByListNumBer(int i) {
		return lista.rmvMsgByListNumBer(i);
	}

	// funkcje do pobierania id ostatniej odebranej wiadomo�ci, oraz rozmiaru
	// listy
	public int getLastIndex() {
		return lista.getLastIndex();
	}

	public int getSize() {
		return lista.getSize();
	}

	// funkcja zwracaj�ca tre�� z przekazanego do niej obiektu wiadomo�ci
	public String getTextFromMsg(Msg message) {
		return message.getmsg();
	}

	// funkcja zwracaj�ca adres pliku z przekazanego do niej obiektu wiadomo�ci
	public String getFileFromMsg(Msg message) {
		return message.getfile();
	}

	// funkcja zwracaj�ca numer id z przekazanego do niej obiektu wiadomo�ci
	public int getIdFromMsg(Msg message) {
		return message.getid();
	}

	@Override
	public String getFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String recvMsgs(String key, String email) {
		// TODO Auto-generated method stub
		return null;
	}

}

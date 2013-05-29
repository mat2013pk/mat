package message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class Parser {
	private static String code; //-1 aplikacja zglasza b≥πd, 0 serwer zg≥asza OK, 1 serwer zg≥asza b≥πd

	private static void resetCode() {
		code = "-1";
	}

	public static String parseOnlyCode(HttpResponse response) {
		
		// zresetowanie kodu, aby nie zostawa≥y úmieci po wczeúniejszych
		// wywo≥aniach
		resetCode();
		
		// tu trafia odpowiedü w jsonie
		InputStream in;
		
		try {
			in = response.getEntity().getContent();

			// konwersja jsona do tekstu
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
			} catch (JSONException e) {
				e.printStackTrace();
			}
			in.close();
			return code;
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return code;
	}
}

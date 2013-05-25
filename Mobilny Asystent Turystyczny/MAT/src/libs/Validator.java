package libs;

public class Validator {
	
	/**
	 * Email validator (requires API 8)
	 * @param CharSequance (email address)
	 * @return boolean 
	 */
	public final static boolean isValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}
}

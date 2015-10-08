package exception;

public class AutoException extends Exception{
	static final long serialVersionUID = 1158L;
	
	private String _info = ""; //a string that can be fixed to correct the error (line from parsing a file, file name)
	private String _errmess = "";
	private int _errcode = 0;
	
	public AutoException() {
		super();
	}
	public AutoException(String message) {
		super(message);
		_info = message;
	}
	public AutoException(int errcode, String message) {
		super (message);
		_info = message;
		_errcode = errcode;
		setErrorMessage ();
		
	}
	protected void setErrorMessage () {
		StringBuilder message = new StringBuilder ();
		switch (_errcode) {
		
			case 10404 :	message.append("Could not find file ");
							message.append(_info);
							message.append(".");
							_errmess = message.toString();
			break;
			case 10206 :	message.append("No number of options given. Line: ");
							message.append(_info);
			break;
		}
	}
	public String fixError () {
		String solution = "";
		ErrorFix ef = new ErrorFix ();
		switch (_errcode) {
		
		case 10404 : solution = ef.fixInputFileString();
		break;
		case 10206 : solution = ef.fixOptionCountLine(_info);
		break;
		
		}
		return solution;
	}
	
	public String getErrMessage() {
		return this._errmess;
	}
	public int getErrCode() {
		return this._errcode;
	}
	
}

/*Errors so far:
10404 file not found
*/
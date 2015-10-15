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
				break;
			case 10206 :	message.append("Option set line element missing. Line: ");
				break;
			case 102061 :	message.append("Option line element missing. Line: ");
				break;
			case 102062 : 	message.append("Number expected. Line: ");
				break;
			case 102063 : 	message.append("Number not present or is not an integer. Line: ");	
				break;
			case 102064 :	message.append("Model name not found. Line: ");
				break;
		}
		message.append(_info);
		message.append(".");
		_errmess = message.toString();
	}
	public String fixError () {
		String solution = "";
		ErrorFix ef = new ErrorFix ();
		switch (_errcode) {
		
		case 10404 : solution = ef.fixInputFileString();
			break;
		case 10206 : solution = ef.fixOptionLine(_info);
			break;
		case 102061 : solution = ef.fixOptionSetLine(_info);
			break;
		case 102062 : solution = ef.fixNumber(_info);
			break;
		case 102063 :solution = ef.fixOptionSetCount(_info);
			break;
		case 102064 : solution = ef.fixModelName(_info);
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
10206 missing element in option line 
102061 missing element in option set line
102062 number not in correct format --used for both Optionset and Option lines
102063 option set count not in correct format
102064 model name missing
*/
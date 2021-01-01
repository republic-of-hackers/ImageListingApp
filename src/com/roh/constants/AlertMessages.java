package com.roh.constants;

public class AlertMessages implements Messages {
	
	public String getAlertOutputMessage(String msg, String redirectLoc) {
		
		String res = SCRIPT_JAVASCRIPT_START_LTR  
				  + String.format("alert('%s');", msg)
				  + String.format("location='%s';", redirectLoc)
				  + "</script>";
//		
//		String args = String.format("alert('%s');", msg)
//				  + String.format("location='%s';", redirectLoc)
//		
//		String res = String.format(SCRIPT_JAVASCRIPT_START_LTR, );
		
		return res;
	}

}

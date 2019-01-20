package app1;

public class util {
	
	public static String todbchar(String str)
	{ String values=str; 
    	
    	values=values.replace("|", "||||");
    	values=values.replace("'", "''''");
    	values=values.replace("(", "((((");
    	values=values.replace(")", "))))");
    	values=values.replace("((((", "'||chr(40)||'");
    	values=values.replace("))))", "'||chr(41)||'");
    	values=values.replace("&", "'||chr(38)||'");
    	values=values.replace("||||", "'||chr(124)||'");
    	values=values.replace("''''", "'||chr(39)||'");
    	values=values.replace("\n", "'||chr(13)||'");
		return  values;
		
	}

}

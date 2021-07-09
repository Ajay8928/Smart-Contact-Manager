package com.smart.helper;

public class Message {
	
	private String content;
	private String type;
	
  public Message(String content, String type) 
  {
		super();
		this.content = content;
		this.type = type;
   }



public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String insertString(
        String originalString,
        int stringToBeInserted
        )
    {
	    
	     int index=0;
	     int length=originalString.length();
	     for(int i=0;i<length;i++)
	     {
	    	 if(originalString.charAt(i)=='.')
	    	 {
	    		 index=i;
	    		 break;
	    	 }
	     }
 
        String newString = new String();
  
        for (int i = 0; i < originalString.length(); i++) {
  
            newString += originalString.charAt(i);
  
            if (i == index-1) {
                newString += stringToBeInserted;
            }
        }
        return newString;
    }


}

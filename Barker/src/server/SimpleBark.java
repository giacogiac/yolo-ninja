package server;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import barker.Bark;

public class SimpleBark implements Bark {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private Date sendtime;
	private String message;
	private Set<String> topics;
	private Set<String> related;
	
	public SimpleBark(String username, Date sendtime, String message) {
	    this.username = username;
	    this.sendtime = sendtime;
		this.message = message;
		
		topics = new HashSet<String>();
		Pattern patternHastag = Pattern.compile("(.*?)*(^|[^\\p{L}\\p{Digit}_])#([\\p{L}\\p{Digit}_]+)([^\\p{L}\\p{Digit}_#]|$).*?");
		Matcher mHastag = patternHastag.matcher(message);
		while(mHastag.find()) {
			topics.add(mHastag.group(3).toLowerCase());
		}
		
		related = new HashSet<String>();
		Pattern patternAt = Pattern.compile("(.*?)*(^|[^\\p{L}\\p{Digit}_#])@([\\p{L}\\p{Digit}_]+)([^\\p{L}\\p{Digit}_@]|$).*?");
		Matcher mAt = patternAt.matcher(message);
		while(mAt.find()) {
			related.add(mAt.group(3).toLowerCase());
		}
	}
	
	@Override
	public String getUsername() {
        return username;
    }
	
	@Override
    public Date getSendtime() {
        return sendtime;
    }
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public Set<String> getTopics() {
		return topics;
	}
	
	@Override
	public Set<String> getRelated() {
		return related;
	}

}

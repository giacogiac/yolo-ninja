package server;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleBark {
	private String message;
	private Collection<String> topics;
	private Collection<String> related;
	
	public SimpleBark(String message) {
		this.message = message;
		
		topics = new HashSet<String>();
		Pattern patternHastag = Pattern.compile("(.*?)*(^|[^\\p{L}\\p{Digit}_])#([\\p{L}\\p{Digit}_]+)([^\\p{L}\\p{Digit}_#]|$).*?");
		Matcher mHastag = patternHastag.matcher(message);
		while(mHastag.find()) {
			topics.add(mHastag.group(3));
		}
		
		related = new HashSet<String>();
		Pattern patternAt = Pattern.compile("(.*?)*(^|[^\\p{L}\\p{Digit}_#])@([\\p{L}\\p{Digit}_]+)([^\\p{L}\\p{Digit}_@]|$).*?");
		Matcher mAt = patternAt.matcher(message);
		while(mAt.find()) {
			related.add(mAt.group(3));
		}
	}
	
	public String getMessage() {
		return message;
	}
	
	public Collection<String> getTopics() {
		return topics;
	}
	
	public Collection<String> getRelated() {
		return related;
	}

}

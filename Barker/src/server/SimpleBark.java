package server;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.twitter.Extractor;

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
		Extractor ex = new Extractor();
		topics = new HashSet<String>(ex.extractHashtags(message));
		related = new HashSet<String>(ex.extractMentionedScreennames(message));
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

    @Override
    public String getType() {
        return "simple";
    }

    @Override
    public String getOriginalUsername() {
        return null;
    }

}

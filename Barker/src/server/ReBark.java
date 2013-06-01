package server;

import java.util.Date;
import java.util.Set;

import barker.Bark;

public class ReBark implements Bark {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private Date sendtime;
    private Bark bark;
    
    public ReBark(String username, Date sendtime, Bark bark) {
        this.username = username;
        this.sendtime = sendtime;
        this.bark = bark;
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
        return bark.getMessage();
    }

    @Override
    public Set<String> getTopics() {
        return bark.getTopics();
    }

    @Override
    public Set<String> getRelated() {
        return bark.getRelated();
    }

}

package barker;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public interface Bark extends Serializable{

    String getUsername();

    Date getSendtime();

    String getMessage();

    Set<String> getTopics();

    Set<String> getRelated();

}

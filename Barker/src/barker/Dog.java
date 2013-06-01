package barker;

import java.io.Serializable;
import java.util.Set;

public interface Dog extends Serializable {

    public String getUsername();

    public Set<String> getSniffed();

    public void sniff(String username);

}

package barker;

import java.io.Serializable;
import java.util.Set;

public interface Dog extends Serializable {

    String getUsername();

    Set<String> getSniffed();

}

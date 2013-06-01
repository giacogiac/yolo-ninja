package server;

import java.util.Comparator;
import java.util.Map;

public class TrendingsComparator implements Comparator<String> {
    private Map<String, Integer> trendings; //pour garder une copie du Map que l'on souhaite traiter
    
    public TrendingsComparator(Map<String, Integer> trendings){
        this.trendings = trendings; //stocker la copie pour qu'elle soit accessible dans compare()
    }

    @Override
    public int compare(String arg0, String arg1) {
        Integer i0 = trendings.get(arg0);
        Integer i1 = trendings.get(arg1);
        return i0.compareTo(i1);
    }

}

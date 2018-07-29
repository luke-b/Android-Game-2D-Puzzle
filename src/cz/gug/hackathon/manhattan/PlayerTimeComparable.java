package cz.gug.hackathon.manhattan;

import java.util.Comparator;

public class PlayerTimeComparable implements Comparator<Player>{
 
    public int compare(Player p1, Player p2) {
        return (p1.completedInSeconds < p2.completedInSeconds ? -1 : 
        	(p1.completedInSeconds == p2.completedInSeconds ? 0 : 1));
    }
}
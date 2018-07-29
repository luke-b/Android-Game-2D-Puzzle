package cz.gug.hackathon.manhattan;

import java.util.Comparator;

public class PlayerTapsComparable implements Comparator<Player>{
 
    public int compare(Player p1, Player p2) {
        return (p1.completedInTaps < p2.completedInTaps ? -1 : 
        	(p1.completedInTaps == p2.completedInTaps ? 0 : 1));
    }
}
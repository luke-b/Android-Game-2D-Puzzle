package cz.gug.hackaton.manhattan.actors;

import java.util.Vector;

import android.graphics.Point;

public class GameLevel {

	
	private String lastPlayed;
	private int taps;
	private String time;
		
	private boolean assistance; //true-shows turns, false-no assistance
	private int index;  // 0..
	private int par;  //1..
	private int[][] map = new int[6][5];
	
	private Point[] spots = new Point[10];

	public GameLevel(INIFile iniFile,int index) {
		parseLevel(iniFile,index);
		this.index = index;
	}

	private void parseLevel(INIFile iniFile, int index) {
		
		// # -----------------------------
		// # prvni level par 1 s asistenci
		// # -----------------------------
		//
		// level-0-assistance=true
		// level-0-played=1.2.2012
		// level-0-taps=53
		// level-0-time=62:20
		// level-0-par=1
		//
		// # legenda k mape:
		// # ---------------
		// # M - mimo hraci pole
		// # O - hraci pole
		// # 1..9 - cisla 1..9 urcuji poradi rozehravani
		//
		// level-0-row-0= M M M M M
		// level-0-row-1= M O O O M
		// level-0-row-2= M O 1 O M
		// level-0-row-3= M O O O M
		// level-0-row-4= M M M M M
		// level-0-row-5= M M M M M
		//
		// # ------------------------------
		//
		
		this.assistance = iniFile.getKeyBoolean("level-" + index + "-assistance");
		this.lastPlayed = iniFile.getKeyString("level-"+index+"-played");
		this.taps = iniFile.getKeyInt("level-"+index+"-taps");
		this.time = iniFile.getKeyString("level-"+index+"-time");
		this.par = iniFile.getKeyInt("level-"+index+"-par");
		
		System.out.println("index = " + index + ", ");
		
		for (int i = 0; i < 6; i++) {
			System.out.print(i+",");
			String row = iniFile.getKeyString("level-"+index+"-row-"+i).trim();
			
			if (row != null) {
				String[] vals = row.split(" ");
			
				if (vals.length == 5) {
			
					for (int j = 0; j < 5; j++) {
			
						// M - pole
						// O - dobra kytka
						// X - spot a pod nim dobra kytka
						
						String s = vals[j];
						
						if (s.equals("M")) {
							map[i][j] = -1;
						}
						else
						if (s.equals("O")) {
							map[i][j] = 0;
						}
						else {
							map[i][j] = 0;
							int n = Integer.parseInt(s) - 1;
							spots[n] = new Point(i,j);
						}
					}
				}
			}
		}
	}
	
	public void saveLevel(INIFile iniFile) {
		
		iniFile.setString("level-"+index+"-played",this.lastPlayed);
		iniFile.setString("level-"+index+"-taps",this.taps+"");
		iniFile.setString("level-"+index+"-time",this.time);
		
	}

	public void setData(String date) {
		this.lastPlayed = date;
	}

	public void setTime(String time2) {
		this.time = time2;		
	}

	public void setTaps(int taps2) {
		this.taps = taps2;		
	}

	public int[][] getTable() {

			return this.map;
	}
	

	public Point[] getSpots() {
		return this.spots;
	}

	public int getTapCount() {
		// TODO Auto-generated method stub
		return taps;
	}

	public String getPlayTime() {
		
		return time;
	}

	public int getPar() {
		return par;
	}

	
}

package cz.gug.hackathon.manhattan;

import android.util.Log;

public class Player {
	
	public static final String TAG = Player.class.getSimpleName();
	public String name;
	public int completedInTaps;
	public int completedInSeconds;
	private String stringForm;

	private static String delimiter = "=====";
	
	public Player(String highscore) {
	
		stringForm = highscore;
		String[] tokens = highscore.split(delimiter);
		Log.d(TAG, "Tokens:");
		for (int i = 0; i < tokens.length; i++) {
			Log.d(TAG, tokens[i]);
		}
		
		this.name = tokens[0];
		this.completedInTaps = Integer.parseInt(tokens[1]);
		this.completedInSeconds = Integer.parseInt(tokens[2]);;
		
	}
	
	public Player(String name, int numTaps, int numSeconds) {
		this.name = name;
		this.completedInTaps = numTaps;
		this.completedInSeconds = numSeconds;
	}
	
	public Player(String name, int numTaps) {
		this.name = name;
		this.completedInTaps = numTaps;
		this.completedInSeconds = 10000;
	}
	
	@Override
	public String toString() {
		return stringForm; 
	}
}

package cz.gug.hackathon.manhattan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.gug.hackaton.manhattan.actors.GameLevel;
import cz.gug.hackaton.manhattan.actors.INIFile;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class WeedCrusherApp extends Application {

	
	public static WeedCrusherApp instance;
	
	private static final String TAG = WeedCrusherApp.class.getSimpleName();
	public SharedPreferences prefs;
	private DataTable dataTable;
	private int numRows, numCols;
	public static int highscoreMaxPlayers = 5;
	public static List<Player> highscoresList = new ArrayList<Player>();
	
	
	private int currentLevel = -1;
	private int medalCount = -1;
	private GameLevel[] levels = new GameLevel[40];
	private INIFile data;
	
	public DataTable getDataTable() {
		return dataTable;
	}

	@Override
	public void onCreate() { //
		super.onCreate();
		
		WeedCrusherApp.instance = this;
		
		numRows = 6;
		numCols = 5;
		
		dataTable = new DataTable(numRows, numCols);
		Log.d(TAG, "Before getSharedPrefs()");
		prefs = PreferenceManager.getDefaultSharedPreferences(this); 
		Log.d(TAG, "After getSharedPrefs()");
		Log.d(TAG, "===========================================");
		for (int i = 0; i < highscoreMaxPlayers; i++) {
			highscoresList.add(new Player(prefs.getString("player" + (i+1), "Marco Carola=====" + 7 * (i + 2) + "=====" + (60 * (10 - i)))));
			Log.d(TAG, highscoresList.get(i).toString());
		}	
		Collections.sort(highscoresList, new PlayerTapsComparable());
		Log.d(TAG, "===========================================");
		Log.d(TAG, "Sorted:");
		for (int i = 0; i < highscoreMaxPlayers; i++) {			
			Log.d(TAG, highscoresList.get(i).toString());
		}
		Log.d(TAG, "===========================================");
		Log.i(TAG, "onCreated");
		
		data = new INIFile(this, INIFile.LEVEL_FILE);
		data.loadFileExternal(this, INIFile.LEVEL_FILE);
		
		if (data.getData() == null) {
			
			data.loadFileInternal(this, R.raw.levels);
			data.saveFile();
		} else {
			
			System.out.println("EXTERNAL DATA LOAD");
			
		}
		
		currentLevel = data.getKeyInt("current-level");
		medalCount = data.getKeyInt("medal-count");
		
		String lines[] = data.getData();
	
		if (lines != null) {
			for (int i = 0; i < lines.length; i++) {
				Log.i(TAG, lines[i]);
			}
		}
		
		for (int i = 0; i < 36; i++) {
			GameLevel gl = new GameLevel(data, i);	
			levels[i] = gl;
		}
		
		// script engines list
		
		
		
		
	}
	
	
	public GameLevel getLevel(int index) {
		return levels[index];
	}
	
	public void setMedalCount(int medalCount) {
		this.medalCount = medalCount;
	}

	
	public void setLevelData(int index,String date,String time,int taps) {
		
		GameLevel g = levels[index];
		
		g.setData(date);
		g.setTime(time);
		g.setTaps(taps);
		
		levels[index] = g;
		
	}
	
	
	public void savaUserData() {
		for (int i = 0; i < 36; i++) {
			levels[i].saveLevel(data);
		}
		data.setString("medal-count", medalCount+"");
		data.setString("current-level", currentLevel+"");
		data.saveFile();
	}
	
	public int getCurrentLevel() {
		
		return currentLevel;
		
	}
	

	@Override
	public void onTerminate() { //
		super.onTerminate();
		
		SharedPreferences.Editor editor = prefs.edit();
	    
		for (int i = 0; i < highscoreMaxPlayers; i++) {
			editor.putString("player" + (i + 1), highscoresList.get(i).toString());
		}

	    editor.commit();
		
		Log.i(TAG, "onTerminated");
	}

	public int getMedalCount() {
		// TODO Auto-generated method stub
		return medalCount;
	}

	public void setNextLevel() {
		
		currentLevel++;
		
		if (currentLevel > 39) {
			currentLevel = 39;
		}
	}	
	
	public void addMedal() {
		
		medalCount++;
		
		if (medalCount > 6) {
			medalCount = 6;
		}
			
	}

	
}

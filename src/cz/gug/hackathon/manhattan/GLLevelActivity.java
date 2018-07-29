package cz.gug.hackathon.manhattan;

import android.os.Bundle;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import cz.gug.hackaton.manhattan.actors.GLGarden;
import cz.gug.hackaton.manhattan.actors.GLLevelMenu;

public class GLLevelActivity extends AndroidApplication {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		WeedCrusherApp app = (WeedCrusherApp)getApplication();
		int currentMode = 0;
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null) {
			currentMode = extras.getInt("currentMode");
		}
		
		GLLevelMenu gg = new GLLevelMenu(app,app,currentMode);
		initialize(gg, false );
		
	}
}


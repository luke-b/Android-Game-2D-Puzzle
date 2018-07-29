package cz.gug.hackathon.manhattan;

import android.os.Bundle;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import cz.gug.hackaton.manhattan.actors.GLGarden;

public class GLGameActivity extends AndroidApplication {
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GLGarden gg = new GLGarden();
		initialize(gg, false );
		
	}
}


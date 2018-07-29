package cz.gug.hackathon.manhattan;

import cz.gug.hackathon.manhattan.R;
import cz.gug.hackaton.manhattan.actors.GLLevelMenu;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private MediaPlayer mp;
	private WeedCrusherApp app;

	private void initPlayer() {
		mp = MediaPlayer.create(getApplicationContext(), R.raw.theme);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		app = (WeedCrusherApp)getApplication();

		// dataTable = new DataTable(3, 2);
		// Log.d(TAG, "====================================================");
		// dataTable.shuffle(0);
		// Log.d(TAG, "====================================================");

		final Button start = (Button) findViewById(R.id.start_new_button);
		start.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			
				//	Intent intent = new Intent(v.getContext(), GLGameActivity.class);
				Intent intent = new Intent(v.getContext(), GLLevelActivity.class);
				intent.putExtra("currentMode", GLLevelMenu.SELECTION_MODE);
				startActivity(intent);

			}
		});
		
		final Button highscores = (Button) findViewById(R.id.highscores_button);
        highscores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(v.getContext(), HighscoresActivity.class);
            	startActivity(intent);
            }
        });

		final Button about = (Button) findViewById(R.id.about_button);
		about.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AboutActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume() {

		super.onResume();
		initPlayer();
		if (!mp.isPlaying()) {
			mp.start();
		}

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mp.isPlaying()) {
			mp.stop();
		}
		mp.release();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

}
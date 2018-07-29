package cz.gug.hackathon.manhattan;

import cz.gug.hackathon.manhattan.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class InGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.ingame);
		
		    
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	
	
}

package cz.gug.hackaton.manhattan.actors;

import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cz.gug.hackathon.manhattan.GLGameActivity;
import cz.gug.hackathon.manhattan.GLLevelActivity;
import cz.gug.hackathon.manhattan.WeedCrusherApp;

public class GLLevelMenu  implements ApplicationListener, InputProcessor {

	public final static int SELECTION_MODE = 100;
	public final static int RESULT_MODE = 101;
	public final static int STATS_MODE = 102;
	
	private int currentMode = SELECTION_MODE;
	
	
	private Texture texture;
	private SpriteBatch batch;
	private int viewWidth;
	private int viewHeight;
	private float angle = 0f;
	private int selectedLevel = 0;
	
	private GLLevelButton buttons[] = new GLLevelButton[5*8];
	private WeedCrusherApp app;
	private int starCount = 100;
	private float starX[] = new float[starCount];
	private float starY[] = new float[starCount];
	private int starType[] = new int[starCount];
	private float starAngle = 0f;
	
	private Context context;
	

	private int tapCount;
	private String playTime;
	private int medalCount;
	private int gamePar;
	
	
	public GLLevelMenu(WeedCrusherApp app, Context context, int currentMode) {
		this.currentMode = currentMode;
		this.context = context;
		app = app;
		
		GameLevel l = app.getLevel(app.getCurrentLevel());
		
		tapCount = l.getTapCount();
		playTime = l.getPlayTime();
		medalCount = app.getMedalCount();
		gamePar = l.getPar();
		
		if (currentMode == RESULT_MODE) {  // move to next level
			
			WeedCrusherApp.instance.setNextLevel();
			
			if (tapCount < gamePar) {
				WeedCrusherApp.instance.addMedal();
			}
			
			WeedCrusherApp.instance.savaUserData();
		}
		
	}

	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	private void drawPanel(float x,float y,float width,float height) {
		
		// 12
		// 14
		
		batch.draw(texture, x, y,  //screen coords
				17,17,   //center of rotation
				35,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				11*35, 512-14*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		
		starAngle += 10;
		for (int i = 0;  i < starCount; i++ ) {
			
			batch.draw(texture, starX[i], starY[i],  //screen coords
					17,17,   //center of rotation
					35,35,  //image size 
					1f,1f, //scaleX factor, scaleY factor
					starAngle+(360/10)*i, //rotation in degrees [0..360]
					(10+starType[i])*35, 512-10*35, //texture window offset x,y pixels
					35, 35,  //texture window size w,h pixels
					false, false);  //flip vert. horiz.
			
			starY[i] -= 3f;
			starX[i] += Math.random()*3-1.5;
			
			if (starX[i] < -35) starX[i] = viewWidth;
			if (starX[i] > viewWidth) starX[i] = -35;
			
			if (starY[i] < -35) starY[i] = viewHeight;
		}
		
		
		batch.draw(texture, x+width-35, y,  //screen coords
				17,17,   //center of rotation
				35,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				13*35, 512-14*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		
		batch.draw(texture, x, y-height-35,  //screen coords
				17,17,   //center of rotation
				35,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				11*35, 512-12*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		
		batch.draw(texture, x+width-35, y-height-35,  //screen coords
				17,17,   //center of rotation
				35,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				13*35, 512-12*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		
		batch.draw(texture, x+35, y-height,  //screen coords
				17,17,   //center of rotation
				width-70,height,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				12*35, 512-13*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		batch.draw(texture, x+35, y-height-35,  //screen coords
				17,17,   //center of rotation
				width-70,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				12*35, 512-12*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		batch.draw(texture, x+35, y,  //screen coords
				17,17,   //center of rotation
				width-70,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				12*35, 512-14*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		
		batch.draw(texture, x, y-height,  //screen coords
				17,17,   //center of rotation
				35,height,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				11*35, 512-13*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		
		batch.draw(texture, x+width-35, y-height,  //screen coords
				17,17,   //center of rotation
				35,height,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				13*35, 512-13*35, //texture window offset x,y pixels
				35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
	}
	
	
	private void renderDialog(float cx,float cy,String time,String taps,String par,int medals) {
		
		
		batch.draw(texture, 0, 0,  //screen coords
				0,0,   //center of rotation
				viewWidth,viewHeight,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				2, 2, //texture window offset x,y pixels
				4, 4,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		drawPanel(cx-200, cy+180, 400, 340);
			
		
		batch.draw(texture, cx-4*35, cy+120+25,  //screen coords
				0,0,   //center of rotation
				8*35,3*35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				0*35, 512-11*35, //texture window offset x,y pixels
				8*35, 3*35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		
		for (int i = 0; i < 5; i++) {
			
			if (i + 1 > medalCount) {
				batch.setColor(1,1,1,0.1f);
			} else {
				batch.setColor(1,1,1,1);
			}
			
			
			batch.draw(texture, cx-4*35+i*55, cy+60,  //screen coords
					0,0,   //center of rotation
					2*35,3*35,  //image size 
					1f,1f, //scaleX factor, scaleY factor
					0, //rotation in degrees [0..360]
					12*35, 512-5*35, //texture window offset x,y pixels
					2*35, 3*35,  //texture window size w,h pixels
					false, false);  //flip vert. horiz.
		}
		
		batch.setColor(1,1,1,1);
		
		
	/*	batch.draw(texture, cx-4*35, cy+120-2*35,  //screen coords
				0,0,   //center of rotation
				8*35,3*35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				0*35, 512-11*35, //texture window offset x,y pixels
				8*35, 3*35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		*/
		
		batch.draw(texture, cx-110, cy,  //screen coords
				17,17,   //center of rotation
				2*35,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				8*35, 512-11*35, //texture window offset x,y pixels
				2*35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		drawCString(tapCount+"",cx-110+3*35,cy);
		
		
		batch.draw(texture, cx-110, cy-35,  //screen coords
				17,17,   //center of rotation
				2*35,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				8*35, 512-10*35, //texture window offset x,y pixels
				2*35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		drawCString(gamePar+"",cx-110+3*35,cy-35);
		
		batch.draw(texture, cx-110, cy-2*35,  //screen coords
				17,17,   //center of rotation
				2*35,35,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				10*35, 512-11*35, //texture window offset x,y pixels
				2*35, 35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		drawCString(playTime,cx-110+3*35,cy-2*35);
		
		
		batch.draw(texture, cx-110+40, cy-5*35,  //screen coords
				17,17,   //center of rotation
				3*35,2*35,  //image size 
				1.4f,1.4f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				8*35, 512-7*35, //texture window offset x,y pixels
				3*35, 2*35,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
	}
	
    private void drawCString(String str,float x,float y) {
		
		
		float w = str.length()*25;
		
		float off = x;
		int pos = 0;
		
		for (int i = 0; i < str.length(); i++) {
			pos += drawChar(str.charAt(i), pos+off+5, y);
		}
		
	}
	
	private int drawChar(char ch,float x,float y) {
		
		String chars = "0123456789.:";
		int xp[] = new int[]{0*35,1*35,2*35,3*35,4*35,5*35,6*35,7*35,0*35,1*35,2*35,3*35};
		int yp[] = new int[]{512-14*35,
							 512-14*35,
							 512-14*35,
							 512-14*35,
							 512-14*35,
							 512-14*35,
							 512-14*35,
							 512-14*35,
							 512-13*35,
							 512-13*35,
							 512-13*35,512-13*35};
		
		int index = chars.indexOf(ch);
		
		if (index != -1) {
		
			batch.draw(texture, x-17f, y,  //screen coords
							17, 17,   //center of rotation
							34, 34,  //image size 
							1f,1f, //scaleX factor, scaleY factor
							0, //rotation in degrees [0..360]
							xp[index], yp[index], //texture window offset x,y pixels
							34, 34,  //texture window size w,h pixels
							false, false);  //flip vert. horiz.
		
		}
		
		return 20;
	}
	
	
	private void setSelectedButton(int newIndex) {
		
		if (selectedLevel != newIndex) {
			selectedLevel = newIndex;
			int newType =0;
			for (int i = 0; i < buttons.length; i++) {
				
				boolean b = false;
				if (selectedLevel == i) {
					b = true;
				}
				
				if (b) {
					buttons[i].setType(1);
					newType=2;
				} else {
					buttons[i].setType(newType);
				}
			}
		
		}
	}
	
	public boolean touchDown(int x, int yy, int arg2, int arg3) {
	
		int y = viewHeight - yy;
		
		System.out.println("touchDown");
		
		switch (currentMode) {
	
			case SELECTION_MODE:
				int btnIndex = -1;
		
				for (int i = 0; i < buttons.length; i++) {
					if (buttons[i].wasHit(x, y)) {
						btnIndex = i;
					}
				}
		
			//	if (btnIndex != -1) {
			//		setSelectedButton(btnIndex);
			//	}
				
				if (btnIndex != -1 && btnIndex == WeedCrusherApp.instance.getCurrentLevel()) {
					callGameActivity(btnIndex);
				}
				
			break;
			
			case RESULT_MODE:
				currentMode = SELECTION_MODE;
			break;
		
		}
		
		return true;
	}
	
	private void callGameActivity(int btnIndex) {
		//	Intent intent = new Intent(v.getContext(), GLGameActivity.class);
		Intent intent = new Intent(context, GLGameActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
	//	GameLevel level = app.getLevel(app.getCurrentLevel());
		
		intent.putExtra("currentLevel", WeedCrusherApp.instance.getCurrentLevel());
		context.startActivity(intent);
	}

	

	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public void create() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(this);
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void pause() {
		// TODO Auto-generated method stub
		
	}

	public void render() {
	
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		Gdx.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	
		
		batch.begin();
		
		batch.draw(texture, 0, 0,  //screen coords
							0, 0,   //center of rotation
							viewWidth , viewHeight,  //image size 
							1f,1f, //scaleX factor, scaleY factor
							0f, //rotation in degrees [0..360]
							5*35, 512-5*35, //texture window offset x,y pixels
							3*35, 5*35,  //texture window size w,h pixels
							false, false);  //flip vert. horiz.
		
		
		batch.draw(texture, viewWidth/2f-viewHeight*0.7f, viewHeight/2f-viewHeight*0.7f,  //screen coords
							viewHeight*0.7f, viewHeight*0.7f,   //center of rotation
							viewHeight*1.4f, viewHeight*1.4f,  //image size 
							1f,1f, //scaleX factor, scaleY factor
							-angle*3, //rotation in degrees [0..360]
							2*35, 512-5*35, //texture window offset x,y pixels
							3*35, 3*35,  //texture window size w,h pixels
							false, false);  //flip vert. horiz.
		
		
		float fac = (viewWidth*0.8f)/(8*35);
		float lw = (8*35)*fac;
		float lh = (3*35)*fac;
		float mar = viewWidth*0.1f;
		
		batch.draw(texture, viewWidth/2-lw/2, viewHeight-mar-lh,  //screen coords
							lw/2, lh/2,   //center of rotation
				lw, lh,  //image size 
				1f,1f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				0, 512-8*35, //texture window offset x,y pixels
				8*35-1, 3*35-1,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		
		angle += 0.1;
		
		for (int i = 0;  i < 40; i++) {
			GLLevelButton b = buttons[i];
			if (b != null) {
				b.renderButton(angle);
			}
		}
		
		
		if (currentMode == RESULT_MODE) {
			renderDialog(viewWidth/2, viewHeight/2, "06:30", "1000", "3",2);
		}
		
	//	drawFlowerString(0, 0, viewWidth, angle*10, false);
	//	drawFlowerString(0, viewHeight-70f, viewWidth, -angle*10, false);
		
		batch.end();
		
	
	}

	private void drawFlowerString(float sx,float sy,float width,float offset,boolean orientation) {
		
		int cnt = (int) (width/70) + 2;
		
		float p = -70f + (offset % 70);
		
		for (int i = 0; i < cnt; i++) {
			
			batch.draw(texture, sx + p, sy,  //screen coords
					35,35,   //center of rotation
					69, 69,  //image size 
					1f,1f, //scaleX factor, scaleY factor
					-offset*2, //rotation in degrees [0..360]
					0, 512-4*35, //texture window offset x,y pixels
					69, 69,  //texture window size w,h pixels
					false, false);  //flip vert. horiz.
			
			p += 70;
		}
		
	}
	
	
	public void resize(int w, int h) {
		texture = new Texture(Gdx.files.internal("data/atlas4.png"));
		batch = new SpriteBatch();
		
		this.viewWidth = w;
		this.viewHeight = h;
		
		for (int i = 0;  i < starCount; i++ ) {
			
			starX[i] = (float) (Math.random()*(viewWidth-70)+35);
			starY[i] = (float) (Math.random()*(viewHeight-70)+35);
			starType[i] = (int) (Math.random()*3+1);
			
		}
		
		
		float xoff = w/2 - (5*90)/2 + 35f;
		float yoff = h/2 - (10*85f)/2 + 35f;
		
		int lvl = WeedCrusherApp.instance.getCurrentLevel();
		
		for (int u = 0; u < 5; u++) {
			for (int v = 0; v < 8; v++) {
				int pos = v*5+u;
				int type = 0;
				if (pos == lvl) {
					type = 1;
				} else {
					if (pos > lvl) {
						type = 2;
					}
				}
			
				GLLevelButton gb = new GLLevelButton((v*5+u+1)+"", xoff+u*90f, viewHeight-yoff*3-v*85f, v/3, texture, batch);
				
				gb.setType(type);
				buttons[v*5+u] = gb;				
			}
		}
		
		setSelectedButton(this.selectedLevel);
	}

	public void resume() {
		// TODO Auto-generated method stub
		
	}

}

package cz.gug.hackaton.manhattan.actors;

import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.TweenCallback.EventType;
import aurelienribon.tweenengine.equations.Bounce;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cz.gug.hackathon.manhattan.DataTable;
import cz.gug.hackathon.manhattan.GLGameActivity;
import cz.gug.hackathon.manhattan.GLLevelActivity;
import cz.gug.hackathon.manhattan.R;
import cz.gug.hackathon.manhattan.WeedCrusherApp;
import cz.gug.hackaton.manhattan.actors.accessors.GLPlantAccessor;
import cz.gug.hackaton.manhattan.actors.accessors.PlantHolderAccessor;

public class GLGarden  implements ApplicationListener, InputProcessor {

	private final static int CLOUD_COUNT = 4;
	
	private Texture texture;
	private SpriteBatch batch;
	private float spotW;
	private float spotH;
	
	public static int COLS = 5;
	public static int ROWS = 6;
	
	GLFlower[][][] garden = new GLFlower[ROWS][COLS][2];  // two state objects per cell
	
	private float[][] clouds = new float[CLOUD_COUNT][5];   //xoffset,yoffset,dx,offset,alpha
	
	
	private int fence[][] = new int[ROWS*3+2][COLS*3+2];  // ohradka
	
	TweenManager manager = new TweenManager();
	
	private float viewWidth;
	private float viewHeight;
	private boolean isRunning = false;
	private boolean tapLock = false;
	private DataTable table;
	private int[][] stateImage = new int[ROWS][COLS];
	Rect viewDim;
	private long lastMillis = System.currentTimeMillis();
	
	private MediaPlayer mp;
	private float yOffset;
	
	private long startTime;
	
	double offset = 0.0;
	
	private float gamePerc = 0f;
	private float newPerc = 0f;

	private int flowerCountTotal;
	
	
	private String playTime;
	private int tapCount;
	
	public void create() {
		Gdx.input.setInputProcessor(this);
	    startTime = System.currentTimeMillis();
	    
	    tapCount = 0;
	}

	public void dispose() {
		texture.dispose();
	}

	public void pause() {
	}

	
	
	private void generateFence() {
		
		int pattern[][] = new int[][] {
										{ 0,-1, 0,
										  0,19, 0,
										  0, 0, 0,
										},
										{ 0, 0, 0,
										  0,23,-1,
										  0, 0, 0,
										},
										{ 0, 0, 0,
										  0,25, 0,
										  0,-1, 0,
										},
										{ 0, 0, 0,
										 -1,21, 0,
										  0, 0, 0,
										}
									  };
	
		
		for (int rc = 0; rc < ROWS*3; rc++) {
			for (int cc = 0; cc < COLS*3; cc++) {

				int bc = getBlockCount(rc,cc);
				
				if (bc <= 5 && bc > 0) {
					fence[rc][cc] = 1;
				} else fence[rc][cc] = -1;
				
			}
		}
		
	
		for (int rc = 0; rc < ROWS*3; rc++) {
			for (int cc = 0; cc < COLS*3; cc++) {
			
				if (fence[rc][cc] != -1) {

					if (getStateImageBlock(rc-1, cc) == -1)
						fence[rc][cc] = 10;
					
					if (getStateImageBlock(rc+1, cc) == -1)
						fence[rc][cc] = 11;
					
					if (getStateImageBlock(rc, cc-1) == -1)
						fence[rc][cc] = 8;
					
					if (getStateImageBlock(rc, cc+1) == -1)
						fence[rc][cc] = 9;
					
					
					//corners
					
					if (getStateImageBlock(rc-1, cc-1) == -1 &&
						getStateImageBlock(rc, cc-1) == -1 &&
						getStateImageBlock(rc-1, cc) == -1
							) {
						fence[rc][cc] = 4;
					}
					
					if (getStateImageBlock(rc-1, cc+1) == -1 &&
							getStateImageBlock(rc, cc+1) == -1 &&
							getStateImageBlock(rc-1, cc) == -1
								) {
							fence[rc][cc] = 5;
					}
					
					if (getStateImageBlock(rc+1, cc-1) == -1 &&
					    getStateImageBlock(rc+1, cc) == -1 &&
						getStateImageBlock(rc, cc-1) == -1
								) {
							fence[rc][cc] = 6;
					}
					
					if (getStateImageBlock(rc+1, rc+1) == -1 &&
						    getStateImageBlock(rc, cc+1) == -1 &&
							getStateImageBlock(rc+1, cc) == -1
									) {
								fence[rc][cc] = 7;
						}
					
					
					
					if (getStateImageBlock(rc-1, cc-1) == -1 &&
						    getStateImageBlock(rc-1, cc) != -1 &&
							getStateImageBlock(rc, cc-1) != -1
									) {
								fence[rc][cc] = 3;
						}
					
					if (getStateImageBlock(rc-1, cc+1) == -1 &&
						    getStateImageBlock(rc-1, cc) != -1 &&
							getStateImageBlock(rc, cc+1) != -1
									) {
								fence[rc][cc] = 2;
						}
					
					
					if (getStateImageBlock(rc+1, cc+1) == -1 &&
						    getStateImageBlock(rc+1, cc) != -1 &&
							getStateImageBlock(rc, cc+1) != -1
									) {
								fence[rc][cc] = 0;
						}
					
					
					if (getStateImageBlock(rc+1, cc-1) == -1 &&
						    getStateImageBlock(rc, cc-1) != -1 &&
							getStateImageBlock(rc+1, cc) != -1
									) {
								fence[rc][cc] = 1;
						}
					
				}
			}
		}
		
	
	}
	
	
	
	
	
	public void placePattern(int ro,int co,int[] pattern) {
	
		for (int rx = 0; rx < 5; rx++) {
			for (int cx = 0; cx < 5; cx++) {
				
				if (rx+ro > 0 && rx+ro < ROWS*3 &&
				    cx+co > 0 && cx+co < COLS*3) {
					
					if (pattern[rx*5+cx] != -1) {
						fence[rx+ro][cx+co] = pattern[cx+rx*5];
					}
				}
			}
		}
	}
	
	public int getBlockCount(int rox,int cox) {
		int block  = 0;
		
		try {
	
			if (getStateImageBlock(rox-1,cox-1) == -1) block++;
			if (getStateImageBlock(rox,cox-1) == -1) block++;
			if (getStateImageBlock(rox+1,cox-1) == -1) block++;
			if (getStateImageBlock(rox+1,cox) == -1) block++;
			if (getStateImageBlock(rox+1,cox+1) == -1) block++;
			if (getStateImageBlock(rox,cox+1) == -1) block++;
			if (getStateImageBlock(rox-1,cox+1) == -1) block++;
			if (getStateImageBlock(rox-1,cox) == -1) block++;
			
			if (getStateImageBlock(rox,cox) == -1) block = 0;
			
			if (getStateImageBlock(rox-1,cox) == -1 &&
			    getStateImageBlock(rox+1,cox) == -1) block=0;
			
			
			if (getStateImageBlock(rox,cox-1) == -1 &&
				getStateImageBlock(rox,cox+1) == -1) block=0;
			
		
		} catch (IndexOutOfBoundsException e) {
		}
		
		return block;
	}
	
	public int getStateImageBlock(int ro,int co) {
		
		try {
			
			if (ro < 0 || co < 0) {
				return -1;
			}
			
			int rox = ro/3;
			int cox = co/3;
			
			return stateImage[rox][cox];
			
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
		
	}
	
	
	public void placeFence(int ro,int co,int v) {
		
		if (ro < 0) return;
	    if (ro > 3*ROWS) return;
		if (co < 0) return;
		if (co > 3*COLS) return;
		
		fence[ro][co] = v;
	}
	
	
	public boolean patternMatch(int ro,int co,int[] pattern) {
		
		int match = 0;
		int mask = 0;
		
		if (getFenceSpot(ro+0,co+0)==-1 &&
			getFenceSpot(ro+1,co+0)==-1 &&
			getFenceSpot(ro+1,co+1)==-1 &&
			getFenceSpot(ro+0,co+1)==-1){
			return false;
		}
		
		if (pattern[0] == -1) {
			mask++;
			if (pattern[0] == getFenceSpot(ro+0,co+0)) {
				match++;
			}
		}
		
		if (pattern[1] == -1) {
			mask++;
			if (pattern[1] == getFenceSpot(ro+1,co+0)) {
				match++;
			}
		}
		
		if (pattern[2] == -1) {
			mask++;
			if (pattern[2] == getFenceSpot(ro+0,co+1)) {
				match++;
			}
		}
		
		if (pattern[3] == -1) {
			mask++;
			if (pattern[3] == getFenceSpot(ro+1,co+1)) {
				match++;
			}
		}
		
		if (mask==match) {
			return true;
		}
		
		
		return false;
	}
	
	
	public int getFenceSpot(int rw,int cl) {
		
		int rw1 = rw / 3;
		int cl1 = cl / 3;
		
		if (rw1 < 0) return -1;
		if (rw1 > 5) return -1;
		if (cl1 < 0) return -1;
		if (cl1 > 4) return -1;
		
		return stateImage[rw1][cl1];		
	}
	
	
	
	public void drawChar(char ch,float x,float y,float yscale) {
	
		String chars = "0123456789:";
		int chSize = 35;
		
		int chIndex = chars.indexOf(ch);
		
		if (chIndex != -1) {
		
		
			batch.draw(texture, x, Coords.instance.getY(y, 1f),  //screen coords
					  chSize/2, chSize/2,   //center of rotation
					  chSize , chSize,  //image size 
					  1f,yscale, //scaleX factor, scaleY factor
					  0f, //rotation in degrees [0..360]
					  chIndex*chSize, 512-chSize, //texture window offset x,y pixels
					  chSize, chSize,  //texture window size w,h pixels
					  false, false);  //flip vert. horiz.
		
		}
	}
	
	public void drawString(float x, float y,String str,double d) {

		double off = d;
		for (int i = 0;  i < str.length(); i++) {
			
			drawChar(str.charAt(i), x+i*25, y, (float)(Math.abs(Math.sin(off))*0.3+1));
			
			off += Math.PI/4;
		}
	}
	
	public void drawScale1(float x,float y,float ww,
						   float sx1,float ex1,float y1,
						   float sx2,float ex2,float y2,
						   float sx3,float ex3,float y3,
						   float hh) {
		
		float dLeft = ex1-sx1;
		float dRight = ex3-sx3;
		float dMid = ex2-sx2;
		
		float dBody = ww-(dLeft+dRight);
		if (dBody < 0) dBody=0;
		
		
		batch.draw(texture, x, Coords.instance.getY(y, 1f),  //screen coords
				  0, 0,   //center of rotation
				  (int)dLeft , (int)hh,  //image size 
				  1f,1f, //scaleX factor, scaleY factor
				  0f, //rotation in degrees [0..360]
				  (int)sx1, (int)y1, //texture window offset x,y pixels
				  (int)dLeft, (int)hh,  //texture window size w,h pixels
				  false, false);  //flip vert. horiz.
	
		batch.draw(texture, x+dLeft, Coords.instance.getY(y, 1f),  //screen coords
				  0, 0,   //center of rotation
				  (int)dBody , (int)hh,  //image size 
				  1f,1f, //scaleX factor, scaleY factor
				  0f, //rotation in degrees [0..360]
				  (int)sx2, (int)y2, //texture window offset x,y pixels
				  (int)dMid, (int)hh,  //texture window size w,h pixels
				  false, false);  //flip vert. horiz.
		
		batch.draw(texture, x+dLeft+dBody-1, Coords.instance.getY(y, 1f),  //screen coords
				  0, 0,   //center of rotation
				  (int)dRight , (int)hh,  //image size 
				  1f,1f, //scaleX factor, scaleY factor
				  0f, //rotation in degrees [0..360]
				  (int)sx3, (int)y3, //texture window offset x,y pixels
				  (int)dRight, (int)hh,  //texture window size w,h pixels
				  false, false);  //flip vert. horiz.
		
	}
					
	
	
	public void drawClock(float x,float y,long startTime,double d) {
		
		batch.draw(texture, x+10, Coords.instance.getY(y+35, 1f),  //screen coords
				  35, 35,   //center of rotation
				  70 , 70,  //image size 
				  1f,1f, //scaleX factor, scaleY factor
				  0f, //rotation in degrees [0..360]
				  385, 512-70, //texture window offset x,y pixels
				  70, 70,  //texture window size w,h pixels
				  false, false);  //flip vert. horiz.
		
		batch.draw(texture, x+33, Coords.instance.getY(y+20, 1f),  //screen coords
				  12, 20,   //center of rotation
				  35 , 35,  //image size 
				  1f,1f, //scaleX factor, scaleY factor
				  (float)d*-50, //rotation in degrees [0..360]
				  350+70+35, 512-70, //texture window offset x,y pixels
				  35, 35,  //texture window size w,h pixels
				  false, false);  //flip vert. horiz.
		
		int secs = (int) ((System.currentTimeMillis()-startTime)/1000);
		
		String mins = secs/60+"";
		if (mins.length() == 1) mins = "0" + mins;
		
		String scs = secs % 60+"";
		if (scs.length() == 1) scs = "0" + scs;
		
		String time = mins + ":" + scs;
		
		this.playTime = time;
		
		drawString(x+75, y+35-17, time, d);		
	}
	
	
	public void drawProgressBar(float x,float y,float width,float perc,double off) {
		
		drawScale1(x,y,width,
				   0f,20f,441f,
				   19f,28f,441f,
				   151f,175f,441f,
				   35f);
		
		
		float pSize = (width-20)*perc;
		
		if (perc != 0f) {
		
			drawScale1(x+10,y-7,pSize,
						219f,229f,447f,
						229f,233f,447f,
						364f,375f,447f,
						22f);
		
		}
		
		
		batch.draw(texture, x-70, Coords.instance.getY(y+55, 1f),  //screen coords
				 52, 52,   //center of rotation
				  104 , 104,  //image size 
				  1f,1f, //scaleX factor, scaleY factor
				  (perc<0.5f?(float)(Math.sin(offset)*20):0f), //rotation in degrees [0..360]
				  (perc<0.5f?0:1*104), 2*104, //texture window offset x,y pixels
				  100, 100,  //texture window size w,h pixels
				  false, false);  //flip vert. horiz.
		
		batch.draw(texture, x+width-40, Coords.instance.getY(y+55, 1f),  //screen coords
				 52, 52,   //center of rotation
				  104 , 104,  //image size 
				  1f,1f, //scaleX factor, scaleY factor
				  (perc>0.5f?(float)(Math.sin(offset)*20):0f), //rotation in degrees [0..360]
				  (perc>0.5f?1*104:2*104), (perc>0.5f?1*104:2*104), //texture window offset x,y pixels
				  100, 100,  //texture window size w,h pixels
				  false, false);  //flip vert. horiz.
		
	}
	
	
	public void render() {
		int deltaMillis = (int) (System.currentTimeMillis() - lastMillis );
		manager.update(deltaMillis);
		lastMillis = System.currentTimeMillis();
		//batch
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		Gdx.gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
	
		
		batch.begin();
		
		
		batch.draw(texture, 0, Coords.instance.getY(viewHeight-6*spotH, 1f),  //screen coords
	            52, 52,   //center of rotation
	            viewWidth , viewHeight-6*spotH,  //image size 
	            1f,1f, //scaleX factor, scaleY factor
	            0f, //rotation in degrees [0..360]
	            3*103+1, 2*103, //texture window offset x,y pixels
	            101, 102,  //texture window size w,h pixels
	            false, false);  //flip vert. horiz.
		
			
	
		offset += 0.1;
		
		if (garden != null && table != null ) {
			for (int c = 0; c < COLS; c++) {
				
				batch.draw(texture, c*spotW, Coords.instance.getY(viewHeight-6*spotH, 1f),  //screen coords
			            52, 52,   //center of rotation
			            spotW, spotH,  //image size 
			            1f,1f, //scaleX factor, scaleY factor
			            0f, //rotation in degrees [0..360]
			            2*103, 3*103, //texture window offset x,y pixels
			            101, 102,  //texture window size w,h pixels
			            false, false);  //flip vert. horiz.
				
				for (int r = 0; r < ROWS; r++) {
					
					int val = stateImage[r][c];
					
					if (val == -1) {
						
						batch.draw(texture, c*spotW, Coords.instance.getY(r*spotH+yOffset, 1f),  //screen coords
					            52, 52,   //center of rotation
					            spotW, spotH*2+2,  //image size 
					            1f,1f, //scaleX factor, scaleY factor
					            0f, //rotation in degrees [0..360]
					            3*103+1, 0, //texture window offset x,y pixels
					            101,204,  //texture window size w,h pixels
					            false, false);  //flip vert. horiz.
						
					} else {
				
						batch.draw(texture, c*spotW-1, Coords.instance.getY(r*spotH+yOffset, 1f),  //screen coords
					            52, 52,   //center of rotation
					            spotW+1, spotH,  //image size 
					            1f,1f, //scaleX factor, scaleY factor
					            0f, //rotation in degrees [0..360]
					            1,3*103+1,  //texture window offset x,y pixels
					            101,101,  //texture window size w,h pixels
					            false, false);  //flip vert. horiz.
						
					}
					
					
					float fx = spotW/3;
					float fy = spotH/3;
				
					for (int ic = 0; ic <  3; ic++) {
						for (int ir = 0; ir < 3; ir++) {
							
							int ival = fence[ir+r*3][ic+c*3];
							
							int tx = ival%2;
							int ty = ival/2;
							float f = 34;
							
							if (ival != -1) {
							
								
								batch.draw(texture, (ic+c*3)*fx-1, Coords.instance.getY((ir+r*3-2)*fy+yOffset, 1f),  //screen coords
							            52, 52,   //center of rotation
							            fx+1, fy,  //image size 
							            1f,1f, //scaleX factor, scaleY factor
							            0f, //rotation in degrees [0..360]
							            2*103+(int)(tx*f), (int)(ty*f), //texture window offset x,y pixels
							            (int)f,(int)f,  //texture window size w,h pixels
							            false, false);  //flip vert. horiz.
								
							} 
						}
					}
					
					
				}
			}
			
			
			
		
			
			
			
			for (int c = 0; c < COLS; c++) {
				for (int r = 0; r < ROWS; r++) {
					int val = stateImage[r][c];
					if (val == 0) {  // good plant 
						garden[r][c][0].render(batch);
					}
					if (val == 1) {  // bad plant
						garden[r][c][1].render(batch);
					}
				}
			}

		}
		
		
		// corn fiedl - overlapping
		
		// c*xwidth, r*xheight+yOffset
		
	
		
		
	
		
		
		
		
		//xoffset,yoffset,dx,offset,alpha
		
		for (int i = 0; i < CLOUD_COUNT; i++) {
			
			
			batch.setColor(1, 1, 1, clouds[i][4]);
			batch.draw(texture, clouds[i][0], Coords.instance.getY(viewHeight-6.5f*spotH-clouds[i][1], 1f),  //screen coords
		            52, 52,   //center of rotation
		            spotW*2, spotH*2,  //image size 
		            1f+(float)(Math.sin(offset+clouds[i][3])*0.1f),1f+(float)(Math.cos(offset+clouds[i][3])*0.1f), //scaleX factor, scaleY factor
		            0f, //rotation in degrees [0..360]
		            3*103, 3*103+2, //texture window offset x,y pixels
		            102, 100,  //texture window size w,h pixels
		            false, false);  //flip vert. horiz.
			batch.setColor(1, 1, 1, 1);
			
			clouds[i][0] += clouds[i][2];
			
			if (clouds[i][2] < 0) {  //left
				
				if (clouds[i][0] < -spotW*2) {
					clouds[i][0] = viewWidth;
				}
				
			} else { //right
				
				if (clouds[i][0] > viewWidth) {
					clouds[i][0] = -spotW*2;
				}
				
			}
		}
		
		//drawString(50, 50, "00:60", offset);
		
		drawClock(viewWidth/2-125, viewHeight*0.1f, startTime, offset);  //proporcni Y umisteni
		
		
		if (newPerc != gamePerc) {
			
			float dx = (newPerc-gamePerc)*0.1f;
			
			gamePerc += dx;			
		}
        drawProgressBar(viewWidth*0.2f, viewHeight*0.22f, viewWidth*0.6f, gamePerc, offset);		
		
	//	batch.draw(texture, 0f, 0f);
		
		batch.end();
	}

	public void resize(int w, int h) {
		Coords.instance.setScreen(w, h);
		this.spotW = w / 5;
		this.spotH = this.spotW;
		
	    viewWidth = w;
	    viewHeight = h;
	  
		texture = new Texture(Gdx.files.internal("data/atlas2.png"));
		batch = new SpriteBatch();
	    initGarden();
		initFlowers();
		
		//xoffset,yoffset,dx,offset,alpha
		for (int i = 0; i < CLOUD_COUNT; i++) {
			clouds[i][0] = (float)(Math.random()*viewWidth);
			clouds[i][1] = (float)((i-1)*spotH*0.5f);
			clouds[i][2] = (float)((Math.random()*4 + 2)- 3);
			clouds[i][3] = (float)(Math.random()*Math.PI);
			clouds[i][4] = (float)(0.3f + Math.random()*0.2);
		}
	}

	public void resume() {
	}

	
	
	private void checkTap(float x,float y) {
		
		if (garden != null ) {
			
			for (int c = 0; c < COLS; c++) {
				for (int r = 0; r < ROWS; r++) {
					
					if (garden[r][c][0] != null) {
					
						if (garden[r][c][0].wasCrushed(x, y)) {
							System.out.println("tapped u:" + c +  ", v:" + r);
							handleTap(r, c);
							this.tapCount++;
						
							if (mp != null) {
								if (!mp.isPlaying()) {
									mp.start();
								}
							}
						}
					
					}
				}
			}
		}		
	}
	
	
	private void handleTap(int rr,int cc) {
		
		// save previous state
		// process turn
		// compere new and previous state
		// fire animations
		
		System.out.println("Garden.handleTap( row " + rr + ", col" + cc + ")");
		
		if (!tapLock && table != null) {
			tapLock = true;

			final int[][] oldState = new int[ROWS][COLS];
			
			for (int c = 0; c < COLS; c++) 
				for (int r = 0; r < ROWS; r++) {
					oldState[r][c] = table.getValues(r, c);
				}
			
			table.tap(rr, cc);
			
			final int[][] newState = new int[ROWS][COLS];
			
			for (int c = 0; c < COLS; c++) 
				for (int r = 0; r < ROWS; r++) {
					newState[r][c] = table.getValues(r, c);
				}
			
			// -- compare states --
			
			int maxDelay = 0;
			
			Timeline tl = Timeline.createSequence();
			tl.ensurePoolCapacity(100);
			tl = tl.beginParallel();
			
			for (int c = 0; c < COLS; c++) 
				for (int r = 0; r < ROWS; r++) {

					if (newState[r][c] != oldState[r][c]) {
						
						System.out.println("difference at row " + r + ", col " + c);
		
						//get distance to tap origin
						int dx = r-rr;
						int dy = c-cc;
						int l = (int)Math.sqrt(dx*dx+dy*dy);
						
						int delayFactor = l * 200; // compute animation start delay based on distance to tap origin
						
					
						System.out.println("difference at row " + r + ", col " + c + ", d:" + delayFactor);
						
						if (maxDelay < delayFactor) maxDelay = delayFactor;
						
						// init animation
						
						int oldType = oldState[r][c];
						
					
						
						if (oldType != -1) {
						
							manager.killTarget(garden[r][c][oldType]);
							garden[r][c][oldType].setCrush(0f);
							
							final int tr = r;
							final int tc = c;
							final int df = delayFactor;
							
							tl = tl.push(Tween.to(garden[r][c][oldType], GLPlantAccessor.CRUSH, 500)
						    .target(1)
							.delay(delayFactor)
							.ease(Bounce.INOUT)
							.delay(100)
							.addCallback(EventType.COMPLETE, 
									new TweenCallback() {
								public void onEvent(EventType arg0, BaseTween arg1) {
									
									stateImage[tr][tc] = newState[tr][tc];
									
									manager.killTarget(garden[tr][tc][newState[tr][tc]]);
									garden[tr][tc][newState[tr][tc]].setCrush(1f);
																									
								}
							}	
							));
							
							tl = tl.push(Tween.to(garden[tr][tc][newState[tr][tc]], GLPlantAccessor.CRUSH, 500)
									.target(0)
									.delay(delayFactor+600)
									.ease(Bounce.OUT)
									.delay(500)
									);
							
							
						}
					}
				}
			
			//tl = tl.repeat(1,200);
			
			tl = tl.end();
			tl.start(manager);
			
			// schedule tap unlock after last animation finished
			Tween.call(new TweenCallback() {
				public void onEvent(EventType arg0, BaseTween arg1) {
				
					
					tapLock = false;
					
					int flowerCount = 0;
					for (int u = 0; u < 5; u++) {
						for (int v = 0; v < 6; v++) {
							if (stateImage[v][u] == 0) {
								flowerCount++;
							}
						}
					}
					
					GLGarden.this.newPerc = (float)flowerCount/(float)flowerCountTotal;
					
					GLGarden.this.evalGame();
				
				}
			}).delay(maxDelay+1000).start(manager); 
			
			
		}
		
	}
	

	
	
	
	protected void evalGame() {
		
		if (newPerc == 1f) {
			
			WeedCrusherApp app = WeedCrusherApp.instance;
			
			Date d = new Date();
			String dt = d.getDay() + "." + d.getMonth() + "." + d.getYear();
			
			app.setLevelData(app.getCurrentLevel(), dt, playTime, tapCount);
			
			
			Intent intent = new Intent(WeedCrusherApp.instance, GLLevelActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			intent.putExtra("currentMode", GLLevelMenu.RESULT_MODE);
			WeedCrusherApp.instance.startActivity(intent);
			
		}
	}

	private void initFlowers() {
	
	//	initPlayer();
		Tween.registerAccessor(GLFlower.class, new GLPlantAccessor());
	
	}
	
	private void initGarden() {
		
	
		this.isRunning = true;
		
		if (table == null) {
			table = new DataTable(COLS,ROWS);
			
			WeedCrusherApp app = WeedCrusherApp.instance;
			
			table.initLevel(app.getLevel(app.getCurrentLevel()));
		}
		
		this.viewDim = new Rect(0,0,(int)viewWidth,(int)viewHeight);
		
		float xwidth = viewWidth/COLS;
		float xheight = xwidth; 
		
		yOffset = viewHeight - xheight*(ROWS-1);
		flowerCountTotal=0;
		for (int c = 0; c < COLS; c++) {
			System.out.println();
			for (int r = 0; r < ROWS; r++) {
				
				
				stateImage[r][c] = table.getValues(r, c);
				System.out.println("row = " + r);
				System.out.println("col = " + c);
				
				if (stateImage[r][c] != -1) {
					
					flowerCountTotal++;
					
					//good flower
					garden[r][c][0] = new GLFlower(texture,c*xwidth, r*xheight+yOffset, xwidth, xheight, 10,12,1,4,4,0,12,13,5,12,3);
					//bad flower
					garden[r][c][1] = new GLFlower(texture,c*xwidth,  r*xheight+yOffset, xwidth, xheight,9,10,1,4,4,0,12,13,8,10,3);
				} else {
					garden[r][c][0] = null;
					garden[r][c][1] = null;					
				}
				
			  
			}
		}
		
		generateFence();
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

	public boolean touchDown(int x, int y, int pointer, int button) {
		
		this.checkTap(x, y);
			
		return true;
	}

	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
}

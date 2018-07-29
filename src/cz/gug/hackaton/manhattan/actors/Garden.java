package cz.gug.hackaton.manhattan.actors;

import java.util.Iterator;

import cz.gug.hackathon.manhattan.DataTable;
import cz.gug.hackathon.manhattan.R;
import cz.gug.hackaton.manhattan.actors.accessors.PlantHolderAccessor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
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
import aurelienribon.tweenengine.equations.Back;

public class Garden extends View {
	
	
	public static int COLS = 5;
	public static int ROWS = 6;
	
	Flower[][][] garden = new Flower[ROWS][COLS][2];  // two state objects per cell
	
	
	TweenManager manager = new TweenManager();
	
	private Handler handler = new Handler();
	
	private float viewWidth;
	private float viewHeight;
	private boolean isRunning = false;
	
	boolean tapLock = false;
	
	private Thread thread = null;
	
	private DataTable table;
	
	private int[][] stateImage = new int[ROWS][COLS];
	
	
	Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	Rect backDim = new Rect(0,0,back.getWidth(),back.getHeight());
	Rect viewDim;
	Paint backPaint = new Paint();
	
	private MediaPlayer mp;

	private void initPlayer() {
		mp = MediaPlayer.create(this.getContext(), R.raw.grow);
	}

	
	
	Runnable mUpdate = new Runnable() {
		  private long lastMillis = System.currentTimeMillis();

		  public void run() {
		  
			int deltaMillis = (int) (System.currentTimeMillis() - lastMillis );
			manager.update(deltaMillis);
			lastMillis = System.currentTimeMillis();
			
		    Garden.this.invalidate();
		    if (isRunning) {
		    	handler.postDelayed(mUpdate, 10);
		    }
		  }
		};
	
	
	public Garden(Context context) {
		super(context);
		initFlowers();
	}

	public Garden(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initFlowers();
	}

	public Garden(Context context, AttributeSet attrs) {
		super(context, attrs);
		initFlowers();
	}
	
	
	
	private void checkTap(float x,float y) {
		
		if (garden != null ) {
			
			for (int c = 0; c < COLS; c++) {
				for (int r = 0; r < ROWS; r++) {
					
					if (garden[r][c][0].wasCrushed(x, y)) {
						System.out.println("tapped u:" + c +  ", v:" + r);
						handleTap(r, c);
						
						if (!mp.isPlaying()) {
							mp.start();
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
							
							tl = tl.push(Tween.to(garden[r][c][oldType], PlantHolderAccessor.CRUSH, 500)
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
							
							tl = tl.push(Tween.to(garden[tr][tc][newState[tr][tc]], PlantHolderAccessor.CRUSH, 500)
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
					Garden.this.tapLock = false;
				
				}
			}).delay(maxDelay+1000).start(manager); 
			
		}
		
	}
	

	
	
	
	private void initFlowers() {
	
		initPlayer();
		
		
		
		Tween.registerAccessor(Flower.class, new PlantHolderAccessor());

		 final View touchView = findViewById(R.id.ingame_main);
		    touchView.setOnTouchListener(new View.OnTouchListener() {
		        
		        public boolean onTouch(View v, MotionEvent event) {
		        	if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        		System.out.println("Touch coordinates : " +
		                String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
		           
		                checkTap(event.getX(),event.getY());
		        	}
		           
		                return true;
		        }
			
		    });
		   
		
		ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
		  viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    public void onGlobalLayout() {
		      Garden.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		      viewWidth = Garden.this.getWidth();
		      viewHeight = Garden.this.getHeight();
		      initGarden();
		    }
		  });
		}
	}
	
	private void initGarden() {
		
		thread = new Thread(mUpdate);
		thread.start();
		this.isRunning = true;
		
		if (table == null) {
			table = new DataTable(COLS,ROWS);
			table.shuffle(5);
		}
		
		this.viewDim = new Rect(0,0,(int)viewWidth,(int)viewHeight);
		
		float xwidth = viewWidth/COLS;
		float xheight = xwidth; 
		
		float yOffset = viewHeight - xheight*ROWS;
		
		for (int c = 0; c < COLS; c++) {
			for (int r = 0; r < ROWS; r++) {
			//	garden[r][c][0] = new NicePlant(c*xwidth, r*xheight+yOffset, xwidth, xheight, null, null, null, null, null, null);
			//	garden[r][c][1] = new NastyPlant(c*xwidth, r*xheight+yOffset, xwidth, xheight, null, null, null, null, null, null);
				
				garden[r][c][0] = new BitmapFlower(c*xwidth, r*xheight+yOffset, xwidth, xheight,R.drawable.goodhead1,R.drawable.goodbody1,R.drawable.goodleaf1,R.drawable.goodleaf2,R.drawable.goodback1, this.getContext());
				garden[r][c][1] = new BitmapFlower(c*xwidth, r*xheight+yOffset, xwidth, xheight,R.drawable.badhead1,R.drawable.badbody1,R.drawable.badleaf1,R.drawable.badleaf2,R.drawable.badback1, this.getContext());
				
				
			    stateImage[r][c] = table.getValues(r, c);
			}
		}
	}

	
	protected void onDraw(Canvas canvas) {
		
		if (viewDim != null) {
			canvas.save();
			canvas.drawBitmap(back, backDim, viewDim, backPaint);
			canvas.restore();
		}
	
		if (garden != null && table != null ) {
			for (int c = 0; c < COLS; c++) {
				for (int r = 0; r < ROWS; r++) {
					
					int val = stateImage[r][c];
					
					if (val == 0) {  // good plant 
						garden[r][c][0].render(canvas);
					}
					
					if (val == 1) {  // bad plant
						garden[r][c][1].render(canvas);
					}
					
				}
			}
		}
	}
}

package cz.gug.hackaton.manhattan.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;

public class GLFlower extends PlantHolder implements GLRenderable {

	private final static int TEXTURE_WINDOW_WIDTH = 103;
	
	protected int flowerHead;
	protected int flowerBody;
	protected int flowerLeaf1;
	protected int flowerLeaf2;
	protected int cursor;
	protected int background;
	
		
	protected float idleFactor;
	protected float alphaFactor;
	protected float crushFactor=0;
	protected float growFactor;
	protected float cursorAlphaFactor;
	protected float cursorIdleFactor;
	protected Texture texture;

	private int selector;

	private int alterFace;

	private int eyes;

	private int shadow;
	private int greenHead;
	
	
	
	public GLFlower() {
		super();
	}

	public GLFlower(Texture texture,float xpos, float ypos, float holder_width,float holder_height,
			      int flowerHead,int greenHead,int flowerBody,int flowerLeaf1,
			      int flowerLeaf2,int cursor,int background,int selector,int alterFace,int eyes,int shadow ) {
		
		super(xpos, ypos, holder_width, holder_height);
		
		this.background = background;
		this.flowerBody = flowerBody;
		this.flowerHead = flowerHead;
		this.flowerLeaf1 = flowerLeaf1;
		this.flowerLeaf2 = flowerLeaf2;
		this.selector = selector;
		this.alterFace = alterFace;
		this.eyes = eyes;
		this.shadow = shadow;
		this.cursor = cursor;
		this.greenHead = greenHead;
		
	
		this.texture = texture;
		
	}

	
	public void render(SpriteBatch batch) {
		
		float zoomFactor = holder_width/TEXTURE_WINDOW_WIDTH;   // *xsize to adjust screen difference
		Coords c = Coords.instance;
		
		
		//batch.draw(texture, xpos, ypos, 52f,52f, 100f, 100f, 1f,1f, 45f, 1*104, 2*104, 100, 100, false, false);  
		
			
	
		// background 
		
		batch.setColor(1,1,1, 1);
		
		float bx = c.getX(xpos,1f);
		float by = c.getY(ypos,1f);
		
		int tx = c.getTexU(this.background)*TEXTURE_WINDOW_WIDTH;
		int ty = c.getTexV(this.background)*TEXTURE_WINDOW_WIDTH;
		/*
		batch.draw(texture, bx, by,  //screen coords
				            holder_width/2f, holder_height/2f,   //center of rotation
				            holder_width , holder_height,  //image size 
				            1f,1f, //scaleX factor, scaleY factor
				            0f, //rotation in degrees [0..360]
				            tx+2, ty+2, //texture window offset x,y pixels
				            TEXTURE_WINDOW_WIDTH-4, TEXTURE_WINDOW_WIDTH-4,  //texture window size w,h pixels
				            false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		*/
	


		batch.setColor(1,1,1, crushFactor*0.5f);
		batch.draw(texture, bx, by,  //screen coords
	            holder_width/2f, holder_height/2f,   //center of rotation
	            holder_width , holder_height,  //image size 
	            0.5f + crushFactor*0.5f, 0.5f + crushFactor*0.5f, //scaleX factor, scaleY factor
	            0f, //rotation in degrees [0..360]
	            c.getTexU(this.selector)*TEXTURE_WINDOW_WIDTH+1, c.getTexV(this.selector)*TEXTURE_WINDOW_WIDTH, //texture window offset x,y pixels
	            TEXTURE_WINDOW_WIDTH-2, TEXTURE_WINDOW_WIDTH-1,  //texture window size w,h pixels
	            false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		
		
		batch.setColor(1,1,1, 0.5f + crushFactor*0.5f);
		batch.draw(texture, c.getX(xpos,1f), c.getY(ypos-holder_height/3.5f,1f),  //screen coords
	            holder_width/2f, holder_height/2f,   //center of rotation
	            holder_width , holder_height,  //image size 
	            1f+crushFactor*0.5f,1f, //scaleX factor, scaleY factor
	            0f, //rotation in degrees [0..360]
	            (c.getTexU(this.shadow)+1)*TEXTURE_WINDOW_WIDTH+1, c.getTexV(this.shadow)*TEXTURE_WINDOW_WIDTH, //texture window offset x,y pixels
	            TEXTURE_WINDOW_WIDTH-2, TEXTURE_WINDOW_WIDTH-1,  //texture window size w,h pixels
	            false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		
	
		
		// flowerBody 
		batch.setColor(1,1,1,1-crushFactor);
		batch.draw(texture, c.getX(xpos,1f), c.getY(ypos-holder_height/3,1f),  //screen coords
				            holder_width/2f, holder_height/2f,   //center of rotation
				            holder_width , holder_height,  //image size 
				            1f,1f, //scaleX factor, scaleY factor
				            0f, //rotation in degrees [0..360]
				            c.getTexU(flowerBody)*TEXTURE_WINDOW_WIDTH, c.getTexV(flowerBody)*TEXTURE_WINDOW_WIDTH, //texture window offset x,y pixels
				            TEXTURE_WINDOW_WIDTH-2, TEXTURE_WINDOW_WIDTH-2,  //texture window size w,h pixels
				            false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		
		// flowerLeaf1
		batch.setColor(1,1,1,1);
		batch.draw(texture, c.getX(xpos,1f)-(holder_width/6)*crushFactor, c.getY(ypos-holder_height/3+(holder_width/2)*crushFactor,1f),  //screen coords
				            holder_width/2f, holder_height/2f,   //center of rotation
				            holder_width , holder_height,  //image size 
				            1f,1f, //scaleX factor, scaleY factor
				            180f*crushFactor, //rotation in degrees [0..360]
				            c.getTexU(flowerLeaf1)*TEXTURE_WINDOW_WIDTH, c.getTexV(flowerLeaf1)*TEXTURE_WINDOW_WIDTH, //texture window offset x,y pixels
				            TEXTURE_WINDOW_WIDTH-2, TEXTURE_WINDOW_WIDTH-2,  //texture window size w,h pixels
				            false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		
	
		// flowerLeaf2 
		batch.setColor(1,1,1, 1);
		batch.draw(texture, c.getX(xpos,1f)+(holder_width/6)*crushFactor, c.getY(ypos-holder_height/3+(holder_width/2)*crushFactor,1f),  //screen coords
				            holder_width/2f, holder_height/2f,   //center of rotation
				            holder_width , holder_height,  //image size 
				            1f,1f, //scaleX factor, scaleY factor
				            180f*crushFactor, //rotation in degrees [0..360]
				            c.getTexU(flowerLeaf2)*TEXTURE_WINDOW_WIDTH, c.getTexV(flowerLeaf2)*TEXTURE_WINDOW_WIDTH, //texture window offset x,y pixels
				            TEXTURE_WINDOW_WIDTH-2, TEXTURE_WINDOW_WIDTH-2,  //texture window size w,h pixels
				            true, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		
		// cursor 
		batch.setColor(1,1,1, 1);
		batch.draw(texture, c.getX(xpos,1f), c.getY(ypos-holder_height/3,1f),  //screen coords
				            holder_width/2f, holder_height/2f,   //center of rotation
				            holder_width , holder_height,  //image size 
				            1f,1f, //scaleX factor, scaleY factor
				            0f, //rotation in degrees [0..360]
				            c.getTexU(cursor)*TEXTURE_WINDOW_WIDTH, c.getTexV(cursor)*TEXTURE_WINDOW_WIDTH, //texture window offset x,y pixels
				            TEXTURE_WINDOW_WIDTH-2, TEXTURE_WINDOW_WIDTH-2,  //texture window size w,h pixels
				            false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		
		// flower head 
		batch.setColor(1,1,1, 1);
		batch.draw(texture, c.getX(xpos,1f), c.getY(ypos-holder_height/3+(holder_height/3)*crushFactor,1f),  //screen coords
						    holder_width/2f, holder_height/2f,   //center of rotation
						    holder_width , holder_height,  //image size 
						    1f,1f, //scaleX factor, scaleY factor
						    -90f*crushFactor, //rotation in degrees [0..360]
						    c.getTexU(flowerHead)*TEXTURE_WINDOW_WIDTH, c.getTexV(flowerHead)*TEXTURE_WINDOW_WIDTH, //texture window offset x,y pixels
						    TEXTURE_WINDOW_WIDTH-2, TEXTURE_WINDOW_WIDTH-2,  //texture window size w,h pixels
						    false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		
		/*
		batch.setColor(1,1,1, crushFactor*0.8f);
		batch.draw(texture, c.getX(xpos,1f), c.getY(ypos-holder_height/3+(holder_height/3)*crushFactor,1f),  //screen coords
						    holder_width/2f, holder_height/2f,   //center of rotation
						    holder_width , holder_height,  //image size 
						    1f,1f, //scaleX factor, scaleY factor
						    -90f*crushFactor, //rotation in degrees [0..360]
						    c.getTexU(greenHead)*TEXTURE_WINDOW_WIDTH+3, c.getTexV(greenHead)*TEXTURE_WINDOW_WIDTH+2, //texture window offset x,y pixels
						    TEXTURE_WINDOW_WIDTH-6, TEXTURE_WINDOW_WIDTH-2,  //texture window size w,h pixels
						    false, false);  //flip vert. horiz.
		batch.setColor(1, 1, 1, 1);
		*/
	}
	
	public float getX() {
		return this.xpos;
	}

	public float getY() {
	
		return this.ypos;
	}

	public float getIdle() {
		
		return idleFactor;
	}

	public float getAlpha() {
	
		return alphaFactor;
	}

	public float getCrush() {
		
		return crushFactor;
	}

	public float getGrow() {
	
		return growFactor;
	}

	public void setX(float x) {
		
		this.xpos = x;
	}

	public void setY(float y) {
		
		this.ypos = y;
	}

	public void setIdle(float idleFactor) {
	
		this.idleFactor = idleFactor;
	}

	public void setAlpha(float alphaFactor) {

		this.alphaFactor = alphaFactor;
		
	}

	public void setCrush(float crushFactor) {
	
		this.crushFactor = crushFactor;
	}

	public void setGrow(float growFactor) {
		
		this.growFactor = growFactor;
		
	}

	public float getCursorAlpha() {
		
		return cursorAlphaFactor;
	}

	public float getCursorIdle() {
	
		return cursorIdleFactor;
	}

	public void setCursorAlpha(float f) {
		this.cursorAlphaFactor = f;		
	}

	public void setCursorIdle(float f) {
	
		this.cursorIdleFactor = f;
	}

	
	
}

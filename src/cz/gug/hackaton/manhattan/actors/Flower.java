package cz.gug.hackaton.manhattan.actors;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;

public class Flower extends PlantHolder implements Renderable {
		
	protected Picture flowerHead  = new Picture();
	protected Picture flowerBody  = new Picture();
	protected Picture flowerLeaf1  = new Picture();
	protected Picture flowerLeaf2  = new Picture();
	protected Picture cursor  = new Picture();
	protected Picture background  = new Picture();
	
		
	protected float idleFactor;
	protected float alphaFactor;
	protected float crushFactor=0;
	protected float growFactor;
	protected float cursorAlphaFactor;
	protected float cursorIdleFactor;
	
	public Flower() {
		super();
	}

	public Flower(float xpos, float ypos, float holder_width,float holder_height,
			      Picture flowerHead,Picture flowerBody,Picture flowerLeaf1,
			      Picture flowerLeaf2,Picture cursor,Picture background ) {
		
		super(xpos, ypos, holder_width, holder_height);
	
		
		// init dummy pictures
		
		//flower head
		Canvas fc1 = this.flowerHead.beginRecording(60, 60);
		Paint fp1 = new Paint();
		fp1.setColor(Color.RED);
		fc1.drawCircle(30, 30, 12, fp1);
		for (int i = 0; i < 8; i++) {
			fc1.drawCircle((float)(30+Math.cos(i*Math.PI/4)*15), 
					       (float)(30+Math.sin(i*Math.PI/4)*15), 5, fp1);
		}
		fp1.setColor(Color.YELLOW);
		fc1.drawCircle(30, 30, 10, fp1);
		this.flowerHead.endRecording();
		
		//flower body
		fc1 = this.flowerBody.beginRecording(5, 30);
		fp1.setColor(Color.GREEN);
		fc1.drawRect(new Rect(0,0,3,30), fp1);
		this.flowerBody.endRecording();
		
		//flowerLeaf1
		fc1 = this.flowerLeaf1.beginRecording(15, 15);
		fc1.drawCircle(7, 7, 7, fp1);
		this.flowerLeaf1.endRecording();
		
		//flowerLeaf1
		fc1 = this.flowerLeaf2.beginRecording(15, 15);
		fc1.drawCircle(7, 7, 7, fp1);
		this.flowerLeaf2.endRecording();
		
        //cursor
		fc1 = this.cursor.beginRecording(30, 20);
		fp1.setColor(Color.WHITE);
		fp1.setAlpha(100);
		fc1.drawArc(new RectF(0,0,30,20), 0, 360, false, fp1);
		this.cursor.endRecording();
		
		//background
		fc1 = this.background.beginRecording(30, 30);
		fp1.setColor(Color.TRANSPARENT);
		fc1.drawRoundRect(new RectF(0,0,30,30), 8, 8, fp1);
		this.background.endRecording();
		
		if (flowerHead != null)
				this.flowerHead = flowerHead;
		
		if (flowerBody != null)
				this.flowerBody = flowerBody;
		
		if (flowerLeaf1 != null)
			this.flowerLeaf1 = flowerLeaf1;
		
		if (flowerLeaf2 != null)
			this.flowerLeaf2 = flowerLeaf2;
		
		if (cursor != null)
				this.cursor = cursor;
		
		if (background != null)
				this.background = background;
	}

	
	public void render(Canvas canvas) {
		
		canvas.save();
	//	Paint p = new Paint();
	//	p.setAlpha((int)(255f*alphaFactor));
	//	canvas.saveLayer(0, 0, 0, 0, p, 0);
		canvas.translate(this.xpos, this.ypos);
		
		canvas.save();
		canvas.translate(holder_width/2-background.getHeight()/2, holder_height-background.getHeight()/2);
	    canvas.drawPicture(background);
		canvas.restore();
		
		canvas.save();
		//canvas.saveLayerAlpha(new RectF(0,0,30,20), 20, 0);
		canvas.translate(holder_width/2-15, holder_height-10);
		canvas.scale(crushFactor+1, 1, 15, 10);
		canvas.drawPicture(cursor);
		canvas.restore();
		
		canvas.save();
		canvas.translate(holder_width/2-flowerBody.getWidth()/2, holder_height-flowerBody.getHeight());
		canvas.drawPicture(flowerBody);
		canvas.restore();
		
		canvas.save();
		canvas.translate(holder_width/2-(flowerLeaf1.getWidth()+2)-10*crushFactor, holder_height-35);
		canvas.drawPicture(flowerLeaf1);
		canvas.restore();
		
		canvas.save();
		canvas.translate(holder_width/2+2+10*crushFactor, holder_height-25);
		canvas.drawPicture(flowerLeaf2);
		canvas.restore();
		
		canvas.save();
		canvas.translate(holder_width/2-flowerHead.getWidth()/2, holder_height-80+30*crushFactor);
		
		canvas.rotate(180*crushFactor, flowerHead.getWidth()/2, flowerHead.getHeight()/2);
		canvas.scale(1,1-crushFactor/3, flowerHead.getWidth()/2, flowerHead.getHeight()/2);
		
		canvas.drawPicture(flowerHead);
		canvas.restore();
		
		canvas.restore();
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

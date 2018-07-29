package cz.gug.hackaton.manhattan.actors;

import cz.gug.hackathon.manhattan.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;

public class BitmapFlower extends Flower {
	

	private int flowerHeadId;
	private int flowerBodyId;
	private int flowerLeaf1Id;
	private int flowerLeaf2Id;
	private int backgroundId;
	
	
	Bitmap flowerHeadBitmap,flowerBodyBitmap,flowerLeaf1Bitmap,flowerLeaf2Bitmap,flowerBackgroundBitmap;
	Rect flowerHeadDim,flowerBodyDim,flowerLeaf1Dim,flowerLeaf2Dim,flowerBackgroundDim;
	
	//Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.background);
	//Rect backDim = new Rect(0,0,back.getWidth(),back.getHeight());
	//Rect viewDim;
	//Paint backPaint = new Paint();

	public BitmapFlower(float xpos, float ypos, float holder_width,
			float holder_height, int flowerHeadId, int flowerBodyId,
			int flowerLeaf1Id, int flowerLeaf2Id, int backgroundId,Context context)   {
		super();
		
		this.xpos = xpos;
		this.ypos = ypos;
		this.holder_width = holder_width;
		this.holder_height = holder_height;
	
		this.flowerHeadId = flowerHeadId;
		this.flowerBodyId = flowerBodyId;
		this.flowerLeaf1Id = flowerLeaf1Id;
		this.flowerLeaf2Id = flowerLeaf2Id;
		this.backgroundId = backgroundId;
		
		flowerHeadBitmap =  BitmapFactory.decodeResource(context.getResources(), this.flowerHeadId);
		flowerHeadDim = new Rect(0,0,flowerHeadBitmap.getWidth(),flowerHeadBitmap.getHeight());
		
		flowerBodyBitmap =  BitmapFactory.decodeResource(context.getResources(), this.flowerBodyId);
		flowerBodyDim = new Rect(0,0,flowerBodyBitmap.getWidth(),flowerBodyBitmap.getHeight());
		
		flowerLeaf1Bitmap =  BitmapFactory.decodeResource(context.getResources(), this.flowerLeaf1Id);
		flowerLeaf1Dim =  new Rect(0,0,flowerLeaf1Bitmap.getWidth(),flowerLeaf1Bitmap.getHeight());
		
		flowerLeaf2Bitmap =  BitmapFactory.decodeResource(context.getResources(), this.flowerLeaf2Id);
		flowerLeaf2Dim =  new Rect(0,0,flowerLeaf2Bitmap.getWidth(),flowerLeaf2Bitmap.getHeight());
		
		flowerBackgroundBitmap =  BitmapFactory.decodeResource(context.getResources(), this.backgroundId);
		flowerBackgroundDim =  new Rect(0,0,flowerBackgroundBitmap.getWidth(),flowerBackgroundBitmap.getHeight());
		
		Canvas c;
		Paint p = new Paint();
		
	    c = flowerHead.beginRecording(flowerHeadBitmap.getWidth(), flowerHeadBitmap.getHeight());
	    c.drawBitmap(flowerHeadBitmap, flowerHeadDim, flowerHeadDim, p);
	    flowerHead.endRecording();
	    
	    c = flowerBody.beginRecording(flowerBodyBitmap.getWidth(), flowerBodyBitmap.getHeight());
	    c.drawBitmap(flowerBodyBitmap, flowerBodyDim, flowerBodyDim, p);
	    flowerBody.endRecording();
   	    
	    c = flowerLeaf1.beginRecording(flowerLeaf1Bitmap.getWidth(), flowerLeaf1Bitmap.getHeight());
	    c.drawBitmap(flowerLeaf1Bitmap, flowerLeaf1Dim, flowerLeaf1Dim, p);
	    flowerLeaf1.endRecording();
	    
	    c = flowerLeaf2.beginRecording(flowerLeaf2Bitmap.getWidth(), flowerLeaf2Bitmap.getHeight());
	    c.drawBitmap(flowerLeaf2Bitmap, flowerLeaf2Dim, flowerLeaf2Dim, p);
	    flowerLeaf2.endRecording();
	    	    
	    c = background.beginRecording(flowerBackgroundBitmap.getWidth(), flowerBackgroundBitmap.getHeight());
	    c.drawBitmap(flowerBackgroundBitmap, flowerBackgroundDim, flowerBackgroundDim, p);
	    background.endRecording();
	    
	    
		
	}
	
	

}

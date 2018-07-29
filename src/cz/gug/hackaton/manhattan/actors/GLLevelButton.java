package cz.gug.hackaton.manhattan.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GLLevelButton {
	
	private Texture texture;
	private float x;
	private float y;
	private String label;
	private int color;
	private SpriteBatch batch;
	private boolean selected = false;
	
	private int type = 0;   //0-finished,1-open,2-closed
	
	public GLLevelButton(String label,float x,float y,int color,Texture texture,SpriteBatch batch) {
		
		this.x = x;
		this.y = y;
		this.label = label;
		this.texture = texture;
		this.color = color;
		this.batch = batch;
		
	}
	
	private void drawRomanString(String str,float x,float y) {
		
		
		float w = str.length()*25;
		
		float off = x-w/2;
		int pos = 0;
		
		for (int i = 0; i < str.length(); i++) {
			pos += drawChar(str.charAt(i), pos+off+5, y-15f);
		}
		
	}
	
	private int drawChar(char ch,float x,float y) {
		
		String chars = "0123456789.";
		int xp[] = new int[]{0*35,1*35,2*35,3*35,4*35,5*35,6*35,7*35,0*35,1*35,2*35};
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
							 512-13*35};
		
		int index = chars.indexOf(ch);
		
		if (index != -1) {
		
			batch.draw(texture, x-17f, y,  //screen coords
							17, 17,   //center of rotation
							34, 34,  //image size 
							1.5f,1.5f, //scaleX factor, scaleY factor
							0, //rotation in degrees [0..360]
							xp[index], yp[index], //texture window offset x,y pixels
							34, 34,  //texture window size w,h pixels
							false, false);  //flip vert. horiz.
		
		}
		
		return 25;
	}
	
	void renderButton(float angle) {
		
		if (type == 2) {
			batch.setColor(1, 1, 1, 0.5f);
		} else {
			batch.setColor(1, 1, 1, 1);
		}
		
		
		
		
		if (type == 1) {
			
			float vl = (float)(Math.sin(angle)*0.5+0.5);
			
			batch.setColor(1, 1, 1, vl);
			batch.draw(texture, x-30f, y-30f,  //screen coords
					35, 35,   //center of rotation
					70, 70,  //image size 
					1.9f,1.9f, //scaleX factor, scaleY factor
					0, //rotation in degrees [0..360]
					4*70+1, 512-4*35, //texture window offset x,y pixels
					68, 69,  //texture window size w,h pixels
					false, false);  //flip vert. horiz.
				
			batch.setColor(1, 1, 1, 1);
		}
		
		batch.draw(texture, x-30f, y-30f,  //screen coords
				35, 35,   //center of rotation
				70, 70,  //image size 
				1.8f,1.8f, //scaleX factor, scaleY factor
				0, //rotation in degrees [0..360]
				(4+color)*70+1, 512-2*35, //texture window offset x,y pixels
				68, 69,  //texture window size w,h pixels
				false, false);  //flip vert. horiz.
		

		drawRomanString(label, x+10, y+5);
		
		
		batch.setColor(1, 1, 1, 1);
		
		switch (type) {
			
			case 0:
				batch.draw(texture, x-20f, y-50f,  //screen coords
						35, 35,   //center of rotation
						70, 70,  //image size 
						1.2f,1.2f, //scaleX factor, scaleY factor
						0, //rotation in degrees [0..360]
						1*70+1, 512-2*35, //texture window offset x,y pixels
						68, 69,  //texture window size w,h pixels
						false, false);  //flip vert. horiz.
			break;
			
			case 1:
				
				batch.draw(texture, x-25f, y+20f+(float)(Math.sin(angle*3)*10),  //screen coords
						35, 35,   //center of rotation
						70, 70,  //image size 
						1.2f,1.2f, //scaleX factor, scaleY factor
						0, //rotation in degrees [0..360]
						5*70+1, 512-2*70, //texture window offset x,y pixels
						68, 69,  //texture window size w,h pixels
						false, false);  //flip vert. horiz.
				
			break;
				
			case 2:
				batch.draw(texture, x-5f, y-50f,  //screen coords
						35, 35,   //center of rotation
						70, 70,  //image size 
						0.6f,0.6f, //scaleX factor, scaleY factor
						0, //rotation in degrees [0..360]
						0*70+1, 512-2*35, //texture window offset x,y pixels
						68, 69,  //texture window size w,h pixels
						false, false);  //flip vert. horiz.
			break;
			
		}
		
		
		
		
	}
	
	
	public boolean wasHit(float x,float y) {
		
		if (x > this.x-40 && x < this.x + 40 &&
		    y > this.y-40 && y < this.y + 40) {
			return true;
		}
		
		return false;
	}
	
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setSelected(boolean selected) {
		this.selected = false;
	}

}

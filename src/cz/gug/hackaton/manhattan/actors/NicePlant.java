package cz.gug.hackaton.manhattan.actors;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;

public class NicePlant extends Flower {

	public NicePlant(float xpos, float ypos, float holder_width,
			float holder_height, Picture flowerHead, Picture flowerBody,
			Picture flowerLeaf1, Picture flowerLeaf2, Picture cursor,
			Picture background) {
		super(xpos, ypos, holder_width, holder_height, flowerHead, flowerBody,
				flowerLeaf1, flowerLeaf2, cursor, background);

		
		Canvas fc1 = this.flowerHead.beginRecording(60, 60);
		Paint fp1 = new Paint();
		fp1.setColor(Color.RED);
		fc1.drawCircle(30, 30, 15, fp1);
		for (int i = 0; i < 8; i++) {
			fc1.drawCircle((float)(30+Math.cos(i*Math.PI/4)*15), 
					       (float)(30+Math.sin(i*Math.PI/4)*15), 5, fp1);
		}
		fp1.setColor(Color.YELLOW);
		fc1.drawCircle(30, 30, 12, fp1);
		this.flowerHead.endRecording();
	}

}

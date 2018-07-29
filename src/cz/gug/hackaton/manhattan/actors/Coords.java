package cz.gug.hackaton.manhattan.actors;

public class Coords {
	
	public static Coords instance = new Coords();
	
	private float width;
	private float height;
	
	public void setScreen(float width,float height) {
		this.width = width;
		this.height = height;
	}
	
	
	public float getX(float x, float adjust) {
		return x * adjust;
	}
	
	public float getY(float y, float adjust) {
		return (height-y) * adjust;
		//return y;
	}
	
	public int getTexU(int index) {
		return (index % 4);
	}
	
	public int getTexV(int index) {
		return (index / 4); 
	}

}

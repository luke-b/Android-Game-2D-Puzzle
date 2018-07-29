package cz.gug.hackaton.manhattan.actors.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import cz.gug.hackaton.manhattan.actors.Flower;
import cz.gug.hackaton.manhattan.actors.GLFlower;

public class GLPlantAccessor implements TweenAccessor<GLFlower> {

	public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int IDLE = 3;
    public static final int ALPHA = 4;
    public static final int CRUSH = 5;
    public static final int GROW = 6;
    public static final int CURSOR_ALPHA = 7;
    public static final int CURSOR_IDLE = 8;
	
	
	public int getValues(GLFlower target, int tweenType, float[] returnValues) {
		switch (tweenType) {
			case POSITION_X: returnValues[0] = (float) target.getX(); return 1;
         	case POSITION_Y: returnValues[0] = (float) target.getY(); return 1;
         	case IDLE: returnValues[0] = (float) target.getIdle(); return 1;
         	case ALPHA: returnValues[0] = (float) target.getAlpha(); return 1;
         	case CRUSH: returnValues[0] = (float) target.getCrush(); return 1;
         	case GROW: returnValues[0] = (float) target.getGrow(); return 1;
         	case CURSOR_ALPHA: returnValues[0] = (float) target.getCursorAlpha(); return 1;
         	case CURSOR_IDLE: returnValues[0] = (float) target.getCursorIdle(); return 1;
         	default: assert false; return -1;
		}
	}

	
	public void setValues(GLFlower target, int tweenType, float[] newValues) {
		switch (tweenType) {
			case POSITION_X: target.setX(newValues[0]); break;
			case POSITION_Y: target.setY(newValues[0]); break;
			case IDLE: target.setIdle(newValues[0]); break;
			case ALPHA: target.setAlpha(newValues[0]); break;
			case CRUSH: target.setCrush(newValues[0]); break;
			case GROW: target.setGrow(newValues[0]); break;
			case CURSOR_ALPHA: target.setCursorAlpha(newValues[0]); break;
			case CURSOR_IDLE: target.setCursorIdle(newValues[0]); break;
		    default: assert false; break;
		}
	}

}


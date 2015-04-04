package game.sprite;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class shownums extends AnglePhysicsEngine{
	int value;
	AngleSpriteLayout num;
	AngleVector pos;
	public shownums(int val,AngleSpriteLayout nums,AngleVector startpos) {
		super(20);
		num = nums;
		
	}
	
}

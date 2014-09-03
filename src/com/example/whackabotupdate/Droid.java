package com.example.whackabotupdate;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;


/**
 * Class which represents the state of a droid
 * @author ins208
 *
 */
public class Droid implements View.OnClickListener
{
    /**
     * Convenience links to drawables
     */
	private static final int HAPPY_IMG = R.drawable.happy;
	private static final int MAD_IMG = R.drawable.angry;
	
	/**
	 * How long to run the animation
	 */
	private static final int ANIMATION_DURATION=500;
	
	/**
	 * Button used to display the droid
	 */
	private ImageButton button;
	
	/**
	 * Animation to play when the droid becomes happy
	 */
	private Animation happyAnimation;
	
	/**
	 * State tracking
	 */
	private boolean isAngry = false;
	private boolean frozen = true; //Frozen droids do not accept taps or change their state
	
	
	public Droid(ImageButton button)
	{
		this.button = button;
		
		button.setOnClickListener(this);
		
		//Set animation to rotate about the View's center from 0->360 degrees
		this.happyAnimation = new RotateAnimation(0.0f,359.9f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		this.happyAnimation.setDuration(ANIMATION_DURATION);
	}
	
	/**
	 * Update droid state to reflect it being angry. Only become angry if not already
	 * angry, and return whether or not the state was changed
	 */
	public boolean becomeAngry()
	{
	    if(this.isAngry)
	    {
	        return false;
	    }
		this.isAngry = true;
		
		this.button.setImageResource(MAD_IMG);
		return true;
	}
	
	/**
	 * Make droid happy. Only change state if currently angry, and return whether the state changed
	 * @return
	 */
	public boolean becomeHappy()
	{
		if(this.isAngry)
		{
			this.isAngry = false;
			
			this.button.setImageResource(HAPPY_IMG);
			this.button.startAnimation(this.happyAnimation);
			return true;
		}
		return false;
	}

	/**
	 * Handle a click on the droid. Only respond if the view is not frozen
	 */
	@Override
	public void onClick(View v) 
	{
		if(!this.frozen)
		{
			this.becomeHappy();
		}
	}
	
	public void reset()
	{
		this.isAngry = false;
		
		this.button.setImageResource(HAPPY_IMG);
		
		this.frozen = false;
	}
	
	public boolean isAngry()
	{
	    return this.isAngry;
	}
	
	public void pause()
	{
		this.frozen = true;
	}

	public void resume()
	{
		this.frozen = false;
	}
}

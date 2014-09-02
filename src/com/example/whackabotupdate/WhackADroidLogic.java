package com.example.whackabotupdate;

import java.util.ArrayList;

class WhackADroidLogic extends GameLogic
{
	public static final int MAX_ANGRY_DROIDS=5;
	public static final int DECAY_RATE=10;

	private ArrayList<Droid> all_droids;
	
	private int num_ticks=0;
	
	public WhackADroidLogic(ArrayList<Droid> droids, GameContext context)
	{
		super(context);
		
		this.all_droids = droids;
	}
	
	/***
	 * Tell all droids to stop receiving input events
	 */
	public void pause()
	{
		for (Droid droid : this.all_droids) 
		{
			droid.pause();
		}
	}
	
	/**
	 * Tell all droids to resume receiving input events
	 */
	public void resume()
	{
	    
		for (Droid droid : this.all_droids) 
		{
			droid.resume();
		}
	}
	
	/**
	 * Reset the game. Set defaults for all droids, reset tick rate
	 */
	public void reset()
	{
		for (Droid droid : this.all_droids) 
		{
			droid.reset();
		}
		
		this.num_ticks = 0;
		this.tick_rate = DEFAULT_TICK_RATE;
	}
	
	/***
	 * Run game. Should be repeatedly called by the game scheduler at this.tick_rate ms
	 */
	public void runLogic() 
	{
		boolean popped = false;
		int attempts = 0;
		
		//Choose a random droid and tell it to become angry. Since angry droids
		// are not tracked separately, we'll try up to 8 times to find a non-angry
		// droid
		while(attempts++ < 8 && !popped)
		{
			int index =  (int)Math.floor(Math.random() * 16);
			Droid droid = this.all_droids.get(index);
			if(!droid.isAngry())
			{
				popped = true;
				droid.becomeAngry();
				this.num_ticks++;
			}
		}
		
		int angry_count = 0;
		
		for (Droid current_droid : this.all_droids) 
		{
			if(current_droid.isAngry())
			{
				angry_count++;
			}
		}
		
		if(angry_count > MAX_ANGRY_DROIDS)
		{
			this.context.game_ended();
		}
		else
		{
			this.tick_rate -= DECAY_RATE;
		}
	}

	public int getCurrentScore() 
	{
		return this.num_ticks*10;
	}	
}
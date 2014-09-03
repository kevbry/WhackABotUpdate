package com.example.whackabotupdate;

/**
 * Abstract class to represent game logic. Scheduled and run by GameContext
 * @author ins208
 *
 */
public abstract class GameLogic implements Runnable
{
    /**
     * tick_rate is the current rate at which the game logic runs, DEFAULT_TICK_RATE is the rate at which it starts
     */
	public static final int DEFAULT_TICK_RATE=1400;

	protected int tick_rate=DEFAULT_TICK_RATE;
	
	protected GameContext context;

	public GameLogic(GameContext context) 
	{
		this.context = context;
	}
	
	public abstract int getCurrentScore();
	public abstract void runLogic();
	public abstract void pause();
	public abstract void resume();
	
	public int getRequestedTickRate()
	{
		return this.tick_rate;
	}
	
	public void reset()
	{
		this.tick_rate = DEFAULT_TICK_RATE;
	}
	
	/**
	 * Run() is called by the handler at intervals. This is the point at which the game logic should be updated
	 */
	public final void run()
	{
		this.runLogic();
		this.context.tick_complete();
	}
}

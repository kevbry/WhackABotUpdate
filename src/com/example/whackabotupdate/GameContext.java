package com.example.whackabotupdate;

/**
 * Interface to allow logic handler to communicate with the activity
 * @author Kevin Bryant
 *
 */
public interface GameContext 
{
	public void game_ended();
	public void tick_complete();
}

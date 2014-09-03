package com.example.whackabotupdate;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class WhackADroidFragment extends Fragment implements GameContext, HighScoreSubmissionDelegate, InputEventProvider 
{
	
	public static final String SCORE_FORMAT="Score: %d";
	
	private boolean is_paused=true;
	
	//tickHandler is used to schedule updates to the game logic
	private Handler tickHandler;
	//Called at intervals by the tick handler to update game logic
	private GameLogic game_logic;
	private HighScoreDialog high_score_dialog;
	
	private TextView score;
	private Button pause_button;
	private Button score_button;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
	    //Because Handler is created in the UI thread its runnables will run async in the UI thread
	    this.tickHandler = new Handler(); 

	    //Gather views
	    View rootView = inflater.inflate(R.layout.fragment_whack_abot, container,false);
	    
	    this.score = (TextView)rootView.findViewById(R.id.score);
        this.pause_button = (Button)rootView.findViewById(R.id.pause_game);
        this.score_button = (Button)rootView.findViewById(R.id.submit_score);
        
        //Set up game logic
        this.game_logic = new WhackADroidLogic(this.getDroidList(rootView), this);
        
        this.high_score_dialog = HighScoreDialog.newInstance(this.game_logic.getCurrentScore(), this);
        
        return rootView;
	}
	
	@Override
    public void onPause()
	{
		this.pauseGame();
		super.onPause();
	}
	
	@Override
    public void onStop()
	{
		this.pauseGame();
		super.onStop();
	}
	
	private ArrayList<Droid> getDroidList(View rootView)
	{
	    //Create droid instances for each ImageButton on screen. Each droid contains a
	    //reference to the imageview which represents it; this can cause problems on rotation where
	    //the imageviews are destroyed, so we just disable rotation for now.
		ArrayList<Droid> droids = new ArrayList<Droid>();
		
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot1)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot2)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot3)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot4)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot5)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot6)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot7)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot8)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot9)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot10)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot11)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot12)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot13)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot14)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot15)));
		droids.add(new Droid((ImageButton)rootView.findViewById(R.id.robot16)));
		
		return droids;
	}

	public void togglePause(View actionView)
	{
		
		if(this.is_paused) //Game should become active
		{
			this.resumeGame();
		}
		else //Game should become paused
		{
			this.pauseGame();
		}
	}
	 
	/**
	 * Pause the game. Set state attributes, stop callbacks to the game logic
	 */
	private void pauseGame()
	{
		this.is_paused = true;
		
		this.pause_button.setText(R.string.resume);
		//tickHandler is used to schedule the next update of the game logic. 
		this.tickHandler.removeCallbacks(this.game_logic);
		
		this.game_logic.pause();
	}
	
	/**
	 * Resume the game. Update state attributes, restart game loop updates
	 */
	private void resumeGame()
	{
		this.is_paused = false;
		
		this.tickHandler.removeCallbacks(this.game_logic);

		this.pause_button.setText(R.string.pause);
		
		this.game_logic.resume();
		
		this.tickHandler.postDelayed(this.game_logic, this.game_logic.getRequestedTickRate());
	}
	
	/**
	 * Reset game state.
	 */
	public void resetGame(View action)
	{		
		this.pauseGame();

		this.game_logic.reset();
		
		this.score_button.setVisibility(View.GONE);
		this.pause_button.setVisibility(View.VISIBLE);
		this.pause_button.setText(R.string.start);
		this.score.setText(String.format(SCORE_FORMAT, this.game_logic.getCurrentScore()));
	}

	/**
	 * Game over. Halt game, show loss message and update views
	 */
	@Override
	public void game_ended() 
	{
		this.pauseGame();
		Toast.makeText(this.getActivity().getBaseContext(), R.string.lose_message, Toast.LENGTH_LONG).show();
		
		this.score_button.setEnabled(true);
		this.score_button.setVisibility(View.VISIBLE);
		this.pause_button.setVisibility(View.GONE);
	}
	
	/**
	 * Displays high score dialog in response to button press
	 */
	public void showScoreDialog(View actionView)
	{
		this.score_button.setEnabled(false);
		this.high_score_dialog.setScore(this.game_logic.getCurrentScore());
		this.high_score_dialog.show(this.getChildFragmentManager(), "highScore");
	}

	
	/**
	 * Called by the game logic when the game loop has finished running. Used to re-schedule the next update to game logic
	 */
	@Override
	public void tick_complete() 
	{
		this.score.setText(String.format(SCORE_FORMAT, this.game_logic.getCurrentScore()));
		if(!this.is_paused)
		{
			//Reschedule next tick
			this.tickHandler.postDelayed(this.game_logic, this.game_logic.getRequestedTickRate());
		}	
	}

	@Override
	public void scoreSubmitted() 
	{
		Toast.makeText(this.getActivity().getBaseContext(), R.string.score_submitted, Toast.LENGTH_LONG).show();

		this.score_button.setVisibility(View.GONE);
		this.high_score_dialog.dismiss();
	}

}

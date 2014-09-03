package com.example.whackabotupdate;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class WhackABotActivity extends ActionBarActivity
{
    public InputEventProvider provider;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whack_abot);

        if (savedInstanceState == null)
        {
            WhackADroidFragment fragment = new WhackADroidFragment();
            this.provider = fragment;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, "wafrag").commit();
        }
        else
        {
            //Fragment instances are retained. Re-attach on rotation
            this.provider = (InputEventProvider)getSupportFragmentManager().findFragmentByTag("wafrag");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.whack_abot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * The following are shims to push touch events to the fragment and game manager
     * @param action
     */
    public void resetGame(View action)
    {  
        this.provider.resetGame(action);
    }
    
    public void togglePause(View actionView)
    {
        this.provider.togglePause(actionView);
    }
    
    public void showScoreDialog(View actionView)
    {
        this.provider.showScoreDialog(actionView);
    }

}

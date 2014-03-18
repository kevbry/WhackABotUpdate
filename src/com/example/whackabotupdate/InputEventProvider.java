package com.example.whackabotupdate;

import android.view.View;

public interface InputEventProvider
{
    public void togglePause(View actionView);
    public void resetGame(View actionView);
    public void showScoreDialog(View actionView);
}

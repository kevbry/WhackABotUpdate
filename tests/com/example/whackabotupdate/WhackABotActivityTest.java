package com.example.whackabotupdate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;


@RunWith(RobolectricTestRunner.class)
public class WhackABotActivityTest
{
    WhackABotActivity activity;
    ActivityController<WhackABotActivity> controller;
    
    @Before
    public void setup()
    {
        this.controller = Robolectric.buildActivity(WhackABotActivity.class);
        this.activity = this.controller.get();
    }
    
    @Test
    public void testThatTestsRun()
    {
        this.controller.create();
        assertTrue(this.activity != null);
    }
}

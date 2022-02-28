package com.example.team;

import android.content.Context;

<<<<<<< HEAD
=======
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
>>>>>>> f2d3e0f (commit)

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

<<<<<<< HEAD
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

=======
>>>>>>> f2d3e0f (commit)
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.team", appContext.getPackageName());
    }
}
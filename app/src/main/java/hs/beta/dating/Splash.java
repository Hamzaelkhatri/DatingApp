package hs.beta.dating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MotionLayout motionLayout = findViewById(R.id.motion_base);
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1)
            {
            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v)
            {
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i)
            {
                startActivity(new Intent(Splash.this,MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                Splash.this.finish();
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v)
            {
            }
        });
    }
}
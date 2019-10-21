package com.codestringz.roundedLayoutTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.codestringz.view.RoundCornerLayout;

public class MainActivity extends AppCompatActivity
{
    private RoundCornerLayout mRoundCornerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUIRef();
    }

    private void setUIRef()
    {
        //commit 2
        mRoundCornerLayout = findViewById(R.id.myRoundCornerLayout);

        SeekBar seekBar = findViewById(R.id.seekBar1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mRoundCornerLayout.setCornerRadius(progress);
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });

    }
}

package com.Ecosia.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Ecosia.R;
import com.Ecosia.model.AudioFile;
import com.Ecosia.services.EcosiaAudioPlayerService;
import com.Ecosia.utils.EcosiaAudioRetriever;

/**
 * Created by Mohamed Salama on 8/16/2016.
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button startButton = (Button) findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EcosiaAudioPlayerService.class);
                intent.setAction(EcosiaAudioPlayerService.ACTION_PLAY);
                AudioFile audioFile =
                        EcosiaAudioRetriever.
                                newInstance().
                                getRandomAudioFile(getApplicationContext());

                intent.putExtra(
                        EcosiaAudioPlayerService.EXTRA_MEDIA_PATH,
                        audioFile
                );

                startService(intent);
            }
        });


        Button stopButton = (Button) findViewById(R.id.btnStop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EcosiaAudioPlayerService.class);
                intent.setAction(EcosiaAudioPlayerService.ACTION_STOP);
                startService(intent);
            }
        });
    }
}

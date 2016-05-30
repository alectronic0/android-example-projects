package co.alectronic.videodemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaController mediaController = new MediaController(this);
        VideoView vidView = (VideoView) findViewById(R.id.videoView);

        mediaController.setAnchorView(vidView);
        vidView.setMediaController(mediaController);
        //vidView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny_mp4);
        vidView.setVideoPath(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4").toString());
        //vidView.setVideoPath("http://clips.vorwaerts-gmbh.de/big_buck_bunny.ogv");

        vidView.start();

    }
}

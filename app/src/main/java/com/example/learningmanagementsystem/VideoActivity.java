package com.example.learningmanagementsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learningmanagementsystem.R;

import org.lucasr.twowayview.widget.TwoWayView;

import java.io.File;

import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

Bundle bundle;
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    Intent intent = new Intent(VideoActivity.this,MainActivity.class);

                    intent.putExtras(bundle);
                    System.out.println(bundle);
                    startActivity(intent);

                    return true;
            }
            return super.onOptionsItemSelected(item);
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView textView = findViewById(R.id.textView3);
        VideoView videoView=findViewById(R.id.mVideoView);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        String name = bundle.getString("name");

        textView.setText(name);

        videoView.setVideoPath("http://10.0.2.2:8080/elearn/materials/1/media");

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);

        videoView.start();

    }


    /**
     * 获取本地路径
     *
     * @return
     */
    @NonNull
    private String getLocalPath() {
        return new File(Environment.getExternalStorageDirectory(), "vivo.mp4").getPath();
    }

}

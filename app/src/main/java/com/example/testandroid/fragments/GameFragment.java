package com.example.testandroid.fragments;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.testandroid.DrawView;
import com.example.testandroid.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.AUDIO_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
/**
 * This is the game fragment, shows the user a Game
 *
 */
public class GameFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.button) Button   btn;
    MediaPlayer mPlayer;

    SoundPool   soundPool;
    AudioManager    audioManager;
    int     soundMoney;
    boolean     loaded;

    private static final int streamType = AudioManager.STREAM_MUSIC;

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("DetailProductFragment/onCreateView", "Success");

        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @SuppressLint({"LongLogTag", "ClickableViewAccessibility"})
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });

        soundMoney = soundPool.load(getContext(), R.raw.money, 1);

        btn.setOnClickListener(this);
        btn.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521,PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

        Log.w("DetailProductFragment/onViewCreated", "Success");
    }

    public int playSound( )  {
        int streamId = 0;
        streamId = soundPool.play(soundMoney, 1, 1, 0, 0, 1);
        return streamId;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:  playSound(); DrawView.init(DrawView.MONEY+=100); break;
        }
    }
}

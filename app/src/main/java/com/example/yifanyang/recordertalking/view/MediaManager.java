package com.example.yifanyang.recordertalking.view;

import android.media.*;
import android.media.AudioManager;

import java.io.IOException;

/**
 * Created by yifanyang on 16/8/7.
 */

public class MediaManager {

    private static MediaPlayer nMediaPlayer;

    private static boolean isPause;
    public static void playSound(String filePath, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (nMediaPlayer == null){
            nMediaPlayer =new MediaPlayer();
            nMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    nMediaPlayer.reset();
                    return false;
                }
            });
        }else {
            nMediaPlayer.reset();
        }

        try {
            nMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            nMediaPlayer.setOnCompletionListener(onCompletionListener);
            nMediaPlayer.setDataSource(filePath);
            nMediaPlayer.prepare();
            nMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void pause(){
        if (nMediaPlayer !=null && nMediaPlayer.isPlaying()){
            nMediaPlayer.pause();
            isPause=true;
        }
    }

    public static void resume(){
        if (nMediaPlayer !=null &&isPause){
            nMediaPlayer.start();
            isPause=false;
        }
    }

    public static void release(){
        if (nMediaPlayer !=null ){
            nMediaPlayer.release();
            nMediaPlayer =null;
        }
    }

}

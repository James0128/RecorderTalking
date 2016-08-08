package com.example.yifanyang.recordertalking.view;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by yifanyang on 16/8/6.
 */

public class AudioManager {
    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;


    private static AudioManager mInstance;

    private boolean isPrepared;
    private AudioManager(String dir){

        mDir=dir;
    }
    /*
    * 准备完毕
    * */
    public interface  AudioStateListener{
        void  wellPrepared();
    }

    public AudioStateListener mListener;

    public void setOnAudioStateListener(AudioStateListener listener){
        mListener=listener;
    }

    public  static AudioManager getInstance(String dir){
        if (mInstance == null){}
        synchronized (AudioManager.class){
            if (mInstance==null){
                mInstance=new AudioManager(dir);
            }
        }

        return mInstance;
    }

    public void prepareAudio(){

        try {
            isPrepared = false;
            File dir = new File(mDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = generateFileName();
            File file = new File(dir, fileName);

            mCurrentFilePath =file.getAbsolutePath();

            mMediaRecorder = new MediaRecorder();
            // 设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            // 设置MediaRecorder的音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            // 设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // 准备录音
            mMediaRecorder.prepare();
            // 开始
            mMediaRecorder.start();
            // 准备结束
            isPrepared = true;
            if (mListener != null) {
                mListener.wellPrepared();
            }

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 随机生成文件的名称
    * */
    private String generateFileName() {
        return UUID.randomUUID().toString() +".amr";
    }

    public int getVoiceLevel(int maxlevel){

        if (isPrepared){
            try {
                // mMediaRecorder.getMaxAmplitude() 1~32767
                return maxlevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
            }
        }
        return 1;
    }

    public void release(){

        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder=null;
    }

    public void cancel(){

        //删除文件
       release();
        if (mCurrentFilePath != null){
            File file= new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath=null;
        }


    }
    public String getCurrentFilePath(){

        return mCurrentFilePath;
    }
}

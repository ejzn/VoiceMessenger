package com.csc579.android.vms;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class AudioRecorder {

  final MediaRecorder recorder = new MediaRecorder();
  final String path;
  private static final String TAG = "AudioRecorder";
  private static AudioRecorder audioRecorder;

 
  private AudioRecorder(String path) {
    this.path = sanitizePath(path);
  }
  
  public static AudioRecorder getAudioRecorder()
  {
	  if(audioRecorder == null){
		  audioRecorder = new AudioRecorder("/tmp/test2");
	  }
	  return audioRecorder;
  }

  private String sanitizePath(String path) {
    if (!path.startsWith("/")) {
      path = "/" + path;
    }
    if (!path.contains(".")) {
      path += ".3gp";
    }
    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
  }

  /**
   * Starts a new recording.
   */
  public void start() throws IOException {
	  Log.d(TAG,"starting voice recording");
    String state = android.os.Environment.getExternalStorageState();
    if(!state.equals(android.os.Environment.MEDIA_MOUNTED))  {
        throw new IOException("SD Card is not mounted.  It is " + state + ".");
    }

    // make sure the directory we plan to store the recording in exists
    File directory = new File(path).getParentFile();
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("Path to file could not be created.");
    }
    Log.d(TAG,"recording to" + path);
    
    Conversation.getConversation().setFilePathtoRec(path);

    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(path);
    recorder.prepare();
    recorder.start();
  }

  /**
   * Stops a recording that has been previously started.
   */
  public void stop() throws IOException {
	Log.d(TAG,"Stopping" + path);
    recorder.stop();
    recorder.release();
  }
  
  public void play() throws IOException {
	  Log.d(TAG,"Playing" + path);
	  MediaPlayer mp = new MediaPlayer();
	  mp.setDataSource(path);
	  mp.prepare();
	  mp.start();
  }

}
package com.csc579.android.vms;

import java.io.File;
import java.io.IOException;
import android.media.MediaRecorder;


public class VoiceCapture {

  MediaRecorder recorder = new MediaRecorder();
  
  String path;
  
  /* Create the new voice capture at the following location */
  public VoiceCapture(String path) {
	  System.out.print(path);
	  this.path = path;
  }

  public void start() throws IOException {

    // make sure the directory we plan to store the recording in exists
    File directory = new File(path).getParentFile();
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("Path to file could not be created.");
    }

    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(path);
    recorder.prepare();
    recorder.start();
  }

  // Stop recording the file //
  public void stop() throws IOException {
    recorder.stop();
    recorder.release();
  }

}
package com.ericsson.multimedia.recorder;

import java.io.File;
import java.net.MalformedURLException;

import com.ericsson.multimedia.recorder.configuration.VideoRecorderConfiguration;

public class videotest {
	
	public static void main(String[] args) throws MalformedURLException {
		// configuration 
		VideoRecorderConfiguration.setCaptureInterval(100); 
		// 20 frames/sec 
		VideoRecorderConfiguration.wantToUseFullScreen(true); 
		VideoRecorderConfiguration.setVideoDirectory(new File("/home/echindr/videotest")); 
		// home 
		VideoRecorderConfiguration.setKeepFrames(false); 
		// you can also change the x,y using VideoRecorderConfiguration.setCoordinates(10,20);

		
		
		VideoRecorder.start(); // EXECUTE ALL YOU WANT TO BE RECORDED 
		
		
		// video created System.out.println(videoPath); `
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	try {
							VideoRecorder.stop();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        }, 
		        30000 
		);

	}

	}

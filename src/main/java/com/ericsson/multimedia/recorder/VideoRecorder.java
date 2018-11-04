/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alejandro Gómez Morón
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ericsson.multimedia.recorder;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import com.ericsson.broadcast.ScreenCaptureClient;
import com.ericsson.multimedia.capture.ScreenCapture;
import com.ericsson.multimedia.recorder.configuration.VideoRecorderConfiguration;

/**
 * It models the video recorder.
 * 
 * @author Alejandro Gomez <agommor@gmail.com>
 *
 */
public class VideoRecorder {

    /**
     * Status of the recorder.
     */
    private static boolean recording = false;

    
        
    private static Thread currentThread;
    
   
    /**
     * Strategy to record using {@link Thread}.
     */
    private static final Thread getRecordThread() {
        return new Thread() {
            @Override
            public void run() {
                Robot rt;
                ScreenCapture capture;
                try {
                    rt = new Robot();
                    do {
                    	capture = new ScreenCapture(rt.createScreenCapture(new Rectangle(
                    			VideoRecorderConfiguration.getX(), VideoRecorderConfiguration.getY(),
                    			VideoRecorderConfiguration.getWidth(), VideoRecorderConfiguration.getHeight())));

                    	
                    	//Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    	BufferedImage screenCaptureImage = capture.getSource();

                    	Image cursor = ImageIO.read(new File("resources/images/cursor.png"));
                    	int x = MouseInfo.getPointerInfo().getLocation().x;
                    	int y = MouseInfo.getPointerInfo().getLocation().y;

                    	Graphics2D graphics2D = screenCaptureImage.createGraphics();
                    	graphics2D.drawImage(cursor, x, y, 16, 16, null); // cursor.gif is 16x16 size.
                    	         	
                    	ScreenCaptureClient.postScreen(capture,"echindr");

                    	Thread.sleep(VideoRecorderConfiguration.getCaptureInterval());
                    } while (recording);
                } catch (Exception e) {

                    System.out.println(e.getStackTrace());
                    recording = false;
                }
            }
        };
    }

    /**
     * We don't allow to create objects for this class.
     */
    private VideoRecorder() {

    }

    /**
     * It stops the recording and creates the video.
     * @return a {@link String} with the path where the video was created or null if the video couldn't be created.
     * @throws IOException 
     */
    public static void stop() throws IOException 
    {
        if (recording) 
        {
        	ScreenCaptureClient.close();
        	recording = false;
        	if (currentThread.isAlive()) {
        		currentThread.interrupt();
        	}
        }
    }

    /**
     * It starts recording (if it wasn't started before).
     * @param newVideoName with the output of the video.
     */
    public static void start() {
        if (!recording) {
            if (!VideoRecorderConfiguration.getTempDirectory().exists()) {
                VideoRecorderConfiguration.getTempDirectory().mkdirs();
            }
            calculateScreenshotSize();
            ScreenCaptureClient.init();
            recording = true;
            currentThread = getRecordThread();
            currentThread.start();
        }
    }

    /**
     * It calculates the screenshot size before recording. If the useFullScreen was defined, the width, height or x
     */
    private static void calculateScreenshotSize() {
        // if fullScreen was set, all the configuration will be changed back.
        if (VideoRecorderConfiguration.wantToUseFullScreen()) {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            VideoRecorderConfiguration.setWidth((int) size.getWidth());
            VideoRecorderConfiguration.setHeight((int) size.getHeight());
            VideoRecorderConfiguration.setCoordinates(0, 0);
        } else {
            // we have to check if x+width <= Toolkit.getDefaultToolkit().getScreenSize().getWidth() and the same for
            // the height
            if (VideoRecorderConfiguration.getX() + VideoRecorderConfiguration.getWidth() > Toolkit.getDefaultToolkit()
                    .getScreenSize().getWidth()) {
                VideoRecorderConfiguration.setWidth((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                        - VideoRecorderConfiguration.getX()));
            }
            if (VideoRecorderConfiguration.getY() + VideoRecorderConfiguration.getHeight() > Toolkit.getDefaultToolkit()
                    .getScreenSize().getHeight()) {
                VideoRecorderConfiguration.setHeight((int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                        - VideoRecorderConfiguration.getY()));
            }
        }
    }

  

}

/*
 * Copyright (c) 2012, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.ericsson.multimedia.recorder;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ScreenCaptureUI extends Application {

    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("Screen Capture");
    	GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(25, 25, 25, 25));

    	Label scenetitle = new Label("Click Start button to share screen");
    	//scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 10));
    	grid.add(scenetitle, 0, 0, 2, 1);


    	final Button btn = new Button("Start Capture");
    	HBox hbBtn = new HBox(10);
    	// hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	hbBtn.getChildren().add(btn);
    	grid.add(hbBtn, 0, 1, 2, 1);

    	Label userName = new Label("Copy link to share");
    	grid.add(userName, 0, 2, 2, 1);

    	TextField userTextField = new TextField();
    	userTextField.setEditable(false);
    	grid.add(userTextField,  0, 3, 2, 1);




    	btn.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent e) {
    			if("Start Capture".equalsIgnoreCase(btn.getText())) {
    				btn.setText("Stop Capture");
    				VideoRecorder.start();
    			}
    			else {
    				btn.setText("Start Capture");
    				try {
    					VideoRecorder.stop();
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				}
    			}

    		}
    	});

    	Scene scene = new Scene(grid, 300, 275);

    	scene.getStylesheets().add
    	(ScreenCaptureUI.class.getResource("screen.css").toExternalForm());

    	primaryStage.setScene(scene);
    	primaryStage.show();
    }

    public static void main(String[] args) {
    	launch(args);
    }

}
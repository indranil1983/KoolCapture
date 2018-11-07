
package com.ericsson.multimedia.recorder;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;

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

    @SuppressWarnings("restriction")
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
    	userTextField.setText(ShareUrl.generateShareUrl());



    	btn.setOnAction(new EventHandler<ActionEvent>() {

    		@Override
    		public void handle(ActionEvent e) {
    			if("Start Capture".equalsIgnoreCase(btn.getText())) {
    				btn.setText("Stop Capture");
    				try {
    					
						VideoRecorder.start();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    			else {
    				btn.setText("Start Capture");
    				try {    					
    					VideoRecorder.stop();
    				} catch (IOException e1) {
    					System.err.println("handle button "+e1);
    				} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}

    		}
    	});

    	Scene scene = new Scene(grid, 300, 275);

    	scene.getStylesheets().add(ScreenCaptureUI.class.getResource("/screen.css").toExternalForm());

    	primaryStage.setScene(scene);
    	primaryStage.getIcons().add(new javafx.scene.image.Image("/images/testng-logo.png"));
    	primaryStage.show();
    }

        
    public static void main(String[] args) {
    	launch(args);
    }
    
    @Override
    public void stop() throws Exception {
    	try {    					
			VideoRecorder.stop();
		} catch (IOException e1) {
			System.err.println("handle button "+e1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	super.stop();
    }
    
    

}
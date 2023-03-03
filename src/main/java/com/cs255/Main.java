package com.cs255;/*
CS-255 Getting started code for the assignment
I do not give you permission to post this code online
Do not post your solution online
Do not copy code
Do not use JavaFX functions or other libraries to do the main parts of the assignment (i.e. ray tracing steps 1-7)
All of those functions must be written by yourself
You may use libraries to achieve a better GUI
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.io.*;
import java.lang.Math.*;

import javafx.geometry.HPos;

public class Main extends Application {
    int Width = 500;
    int Height = 500;

    int red_col = 255;
    int green_col = 255; //just for the test example
    int blue_col = 255;
    int selectedSphere = 0;
    ArrayList<Sphere> spheres = new ArrayList<>();


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        spheres.add(new Sphere(new Vector(0, 0, 0), new Vector(0, 0, 1),
                new Vector(0, 0, 0), 100, new Vector(250, 250, -200)));
        spheres.add(new Sphere(new Vector(0, 0, 0), new Vector(0, 0, 1),
                new Vector(-50, -50, -50), 100, new Vector(250, 250, -200)));

        stage.setTitle("Ray Tracing");

        //We need 3 things to see an image
        //1. We create an image we can write to
        WritableImage image = new WritableImage(Width, Height);
        //2. We create a view of that image
        ImageView view = new ImageView(image);
        //3. Add to the pane (below)

        ComboBox cb = new ComboBox();
        for (int i = 0; i < spheres.size(); i++) {
            cb.getItems().add("Sphere " + (i + 1));
        }
        cb.setPromptText("Sphere 1");

        //Create the simple GUI
        Slider r_slider = new Slider(0, 255, red_col);
        Slider g_slider = new Slider(0, 255, green_col);
        Slider b_slider = new Slider(0, 255, blue_col);

        int x = 0,y = 0,z = 0;
        Slider xSlider = new Slider(0, 500, x);
        Slider ySlider = new Slider(0, 500, y);
        Slider zSlider = new Slider(0, 500, z);


        //Add all the event handlers
        cb.valueProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue observable, String old, String newVal) {
                        selectedSphere = Integer.parseInt(newVal.replaceAll("[^0-9]", "")) - 1;
                    }
                });

        r_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        spheres.get(selectedSphere).setRedCol(newValue.intValue());
                        Render(image);
                    }
                });
        g_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        spheres.get(selectedSphere).setGreenCol(newValue.intValue());
                        Render(image);
                    }
                });
        b_slider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        spheres.get(selectedSphere).setBlueCol(newValue.intValue());
                        //spheres.get(selectedSphere).setPos(-200, -200, -200);
                        Render(image);
                    }
                });

        //The following is in case you want to interact with the image in any way
        //e.g., for user interaction, or you can find out the pixel position for debugging
        view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(event.getX() + " " + event.getY());
            event.consume();
        });

        Render(image);

        GridPane root = new GridPane();
        root.setVgap(12);
        root.setHgap(12);

        //3. (referring to the 3 things we need to display an image)
        //we need to add it to the pane
        root.add(view, 0, 0);
        root.add(cb, 0, 1);
        root.add(r_slider, 0, 2);
        root.add(g_slider, 0, 3);
        root.add(b_slider, 0, 4);

        //Display to user
        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    public void Render(WritableImage image) {
        //Get image dimensions, and declare loop variables
        int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
        PixelWriter image_writer = image.getPixelWriter();

        double[] cols;


        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                cols = getCol(spheres, i, j);

                image_writer.setColor(i, j, Color.color(cols[0], cols[1], cols[2], 1.0));
            } // column loop
        } // row loop
    }


    public static double[] getCol(ArrayList<Sphere> spheres, int i, int j) {
        double maxCol = 0.0;
        double[] cols = {0.0, 0.0, 0.0};
        for (Sphere sphere : spheres) {
            double currentCol = sphere.getCol(i, j);
            if (currentCol > maxCol) {
                maxCol = currentCol;
                cols[0] = currentCol * sphere.getRedCol();
                cols[1] = currentCol * sphere.getGreenCol();
                cols[2] = currentCol * sphere.getBlueCol();
            }
        }
        return cols;
    }

    public static void main(String[] args) {
        launch();
    }

}
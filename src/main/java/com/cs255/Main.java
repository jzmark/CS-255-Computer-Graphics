package com.cs255;
/**
 * This is a class that allows the rendering of
 * Spheres on a stage with multiple sliders and
 * a dropdown menu to select which Sphere to control.
 *
 * @author Marek Jezinski 2017161, Gracjan Golebiewski 2018590
 * @version 1.1
 *
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.io.*;
import java.lang.Math.*;
import javafx.geometry.HPos;

public class Main extends Application {

    //Creating and setting the variables
    int Width = 500;
    int Height = 500;
    int red_col = 255;
    int green_col = 255;
    int blue_col = 255;
    int r = 100;
    int selectedSphere = 0;

    //Creating an Arraylist to store the Spheres
    ArrayList<Sphere> spheres = new ArrayList<>();


    /**
     * The start method that generates the stage where the
     * Spheres will be rendered, alongside buttons and
     * sliders. Multiple Spheres have to be added here in order
     * to render them.
     * @param stage
     * @throws FileNotFoundException
     */
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        spheres.add(new Sphere(new Vector(0, 0, 0), new Vector(0, 0, 1),
                new Vector(0, 0, 0), 100, new Vector(250, 250, -200)));
        spheres.add(new Sphere(new Vector(0, 0, 0), new Vector(0, 0, 1),
                new Vector(-50, -50, -50), 100, new Vector(250, 250, -200)));

        //Name of the stage
        stage.setTitle("Ray Tracing");
        WritableImage image = new WritableImage(Width, Height);
        ImageView view = new ImageView(image);

        ComboBox cb = new ComboBox();
        for (int i = 0; i < spheres.size(); i++) {
            cb.getItems().add("Sphere " + (i + 1));
        }
        cb.setPromptText("Sphere 1");

        Text txt1 = new Text("r, g, b:");
        Slider r_slider = new Slider(0, 255, red_col);
        Slider g_slider = new Slider(0, 255, green_col);
        Slider b_slider = new Slider(0, 255, blue_col);

        int x = 0,y = 0,z = 0;
        Text txt2 = new Text("x, y, z, radius:");
        Slider xSlider = new Slider(-250, 250, x);
        Slider ySlider = new Slider(-250, 250, y);
        Slider zSlider = new Slider(-250, 250, z);
        Slider rSlider = new Slider(0, 250, r);

        cb.valueProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue observable, String old, String newVal) {
                        selectedSphere = Integer.parseInt(newVal.replaceAll("[^0-9]", "")) - 1;
                        r_slider.valueProperty().setValue(spheres.get(selectedSphere).getRedCol() * 255.0);
                        g_slider.valueProperty().setValue(spheres.get(selectedSphere).getGreenCol() * 255.0);
                        b_slider.valueProperty().setValue(spheres.get(selectedSphere).getBlueCol() * 255.0);
                        xSlider.valueProperty().setValue(spheres.get(selectedSphere).getX());
                        ySlider.valueProperty().setValue(spheres.get(selectedSphere).getY());
                        zSlider.valueProperty().setValue(spheres.get(selectedSphere).getZ());
                        rSlider.valueProperty().setValue(spheres.get(selectedSphere).getRadius());
                    }
                });

        //Sliders for change of colour in the Spheres, and their positions/size
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
                        Render(image);
                    }
                });
        xSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        spheres.get(selectedSphere).setX(newValue.intValue());
                        Render(image);
                    }
                });
        ySlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        spheres.get(selectedSphere).setY(newValue.intValue());
                        Render(image);
                    }
                });
        zSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        spheres.get(selectedSphere).setZ(newValue.intValue());
                        Render(image);
                    }
                });
        rSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        spheres.get(selectedSphere).setRadius(newValue.intValue());
                        Render(image);
                    }
                });

        //The following is in case you want to interact with the image in any way
        //e.g., for user interaction, or you can find out the pixel position for debugging
        view.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            System.out.println(event.getX() + " " + event.getY());
            System.out.println(Double.isNaN(spheres.get(0).getCol((int) event.getX(), (int) event.getY())));
            System.out.println(spheres.get(0).getCol((int) event.getX(), (int) event.getY()));
            event.consume();
        });

        Render(image);

        GridPane root = new GridPane();
        root.setVgap(12);
        root.setHgap(12);

        //Adding created view and sliders/buttons to the Pane
        root.add(view, 0, 0);
        root.add(cb, 0, 1);
        root.add(txt1, 0, 2);
        root.add(r_slider, 0, 3);
        root.add(g_slider, 0, 4);
        root.add(b_slider, 0, 5);
        root.add(txt2, 0, 6);
        root.add(xSlider, 0, 7);
        root.add(ySlider, 0, 8);
        root.add(zSlider, 0, 9);
        root.add(rSlider, 0, 10);

        //Display to user
        Scene scene = new Scene(root, 900, 800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method renders image using the getCol method
     * in order to generate Spheres with chosen colour
     * @param image
     */
    public void Render(WritableImage image) {
        int w = (int) image.getWidth(), h = (int) image.getHeight(), i, j;
        PixelWriter image_writer = image.getPixelWriter();
        double[] cols;
        for (j = 0; j < h; j++) {
            for (i = 0; i < w; i++) {
                cols = getCol(spheres, i, j);
                image_writer.setColor(i, j, Color.color(cols[0], cols[1], cols[2], 1.0));
            }
        }
    }

    /**
     * This method is used to allow the user to modify the
     * colour of selected Sphere from the Arraylist.
     * There are 2 versions available that have different
     * effects on how the final render of Sphere
     * is shown.
     * @param spheres
     * @param i
     * @param j
     * @return
     */
    public static double[] getCol(ArrayList<Sphere> spheres, int i, int j) {
        double maxCol = 0.0;
        double[] cols = {0.0, 0.0, 0.0};
        /*for (Sphere sphere : spheres) {
            double currentCol = sphere.getCol(i, j);
            if (currentCol > maxCol) {
                maxCol = currentCol;
                cols[0] = currentCol * sphere.getRedCol();
                cols[1] = currentCol * sphere.getGreenCol();
                cols[2] = currentCol * sphere.getBlueCol();
            }
        }*/
        for(int index = 0; index < spheres.size(); index++) {
            double currentCol = spheres.get(index).getCol(i, j);
            if (maxCol == 0.0 || Double.isNaN(maxCol)) {
                maxCol = currentCol;
                cols[0] = currentCol * spheres.get(index).getRedCol();
                cols[1] = currentCol * spheres.get(index).getGreenCol();
                cols[2] = currentCol * spheres.get(index).getBlueCol();
            }
        }
        return cols;
    }

    public static void main(String[] args) {
        launch();
    }

}
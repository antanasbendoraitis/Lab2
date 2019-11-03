/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.Bendoraitis;

import edu.ktu.ds.lab2.gui.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;

/**
 *
 * Darbo atlikimo tvarka - čia yra pradinė klasė.
 * @author Antanas
 */
public class DemoExecution extends Application {
    public static void main(String[] args) {
        DemoExecution.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Locale.setDefault(Locale.US);//Suvienodiname skaičių formatus
        ManualTest.executeTest();
        MainWindow.createAndShowGui(primaryStage);
    }
    
}

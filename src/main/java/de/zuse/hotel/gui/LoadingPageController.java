package de.zuse.hotel.gui;

import java.net.URL;

import de.zuse.hotel.util.ZuseCore;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoadingPageController implements Initializable
{
    @FXML
    AnchorPane anchor;
    Thread loadingThread;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        loadingThread = new Thread(() ->
        {
            Gui.getInstance().startLoading();
            //after finish loading now move to the main window
            loadMainScene();
        });

        loadingThread.start();
    }

    public void loadMainScene()
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), anchor);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.5);

        fadeTransition.setOnFinished(value ->
        {
            try
            {
                loadingThread.join();
                JavaFxUtil.closeCurrentStage();
                JavaFxUtil.loadNewWindow(getClass().getResource("MainWindow.fxml"),null,null);
            } catch (Exception e)
            {
                if (ZuseCore.DEBUG_MODE)
                    e.printStackTrace();
            }
        });

        fadeTransition.play();
    }


}
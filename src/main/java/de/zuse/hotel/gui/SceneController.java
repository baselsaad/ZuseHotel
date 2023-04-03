package de.zuse.hotel.gui;

import de.zuse.hotel.core.HotelCore;
import de.zuse.hotel.util.ZuseCore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController implements ControllerApi
{
    private static final String BUTTON_SELECTED_STYLE_NAME = "on_button_selected";
    public AnchorPane background;
    public VBox menuBar;

    @FXML
    private Button guestBtnId;
    @FXML
    private Button roomsBtnId;
    @FXML
    private Button settingsBtnId;

    @FXML
    private Button dashboardBtnId;

    @FXML
    private BorderPane borderPane;


    public SceneController()
    {
    }

    public void onClickDashboardBtn(ActionEvent event) throws IOException
    {
        //TODO(Basel): another way to call onStart
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Node node = fxmlLoader.load();
        ((ControllerApi) fxmlLoader.getController()).onStart();
        HotelCore.get().setCurrentScene(fxmlLoader.getController());
        borderPane.setCenter(node);
        onSwitchPanel(dashboardBtnId);
    }

    public void onClickRoomBtn(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Rooms.fxml"));
        Node node = fxmlLoader.load();
        ((ControllerApi) fxmlLoader.getController()).onStart();
        HotelCore.get().setCurrentScene(fxmlLoader.getController());

        borderPane.setCenter(node);
        onSwitchPanel(roomsBtnId);
    }

    public void onClickGuestBtn(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Guest.fxml"));
        Node node = fxmlLoader.load();
        ((ControllerApi) fxmlLoader.getController()).onStart();
        HotelCore.get().setCurrentScene(fxmlLoader.getController());
        borderPane.setCenter(node);
        onSwitchPanel(guestBtnId);
    }

    public void onClickSettingsBtn(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Node node = fxmlLoader.load();
        borderPane.setCenter(node);
        onSwitchPanel(settingsBtnId);
    }

    public void resetButtonsStyle()
    {
        //reset
        dashboardBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);
        settingsBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);
        roomsBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);
        guestBtnId.getStyleClass().removeAll(BUTTON_SELECTED_STYLE_NAME);

        dashboardBtnId.setStyle("");
        settingsBtnId.setStyle("");
        roomsBtnId.setStyle("");
        guestBtnId.setStyle("");
    }

    private void onSwitchPanel(Button selectedBtn)
    {
        resetButtonsStyle();
        selectedBtn.getStyleClass().add(BUTTON_SELECTED_STYLE_NAME);
        selectedBtn.setStyle("-fx-cursor: hand; -fx-text-fill: #ffffff;"); //disable hovor effect
    }

    @FXML
    void handleBookRoomButtonAction(ActionEvent event) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ((ControllerApi) fxmlLoader.getController()).onStart();
        Stage stage = new Stage();
        stage.setTitle("Book a room");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); //default, for closing th pop up window
        stage.show();
        stage.resizableProperty().setValue(false);
    }

    @Override
    public void onStart()
    {
        background.getStylesheets().add(SettingsController.getCorrectStylePath("background.css"));
        menuBar.getStylesheets().add(SettingsController.getCorrectStylePath("NavMenu.css"));

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Node node = fxmlLoader.load();
            ((ControllerApi) fxmlLoader.getController()).onStart();
            HotelCore.get().setCurrentScene(fxmlLoader.getController());
            borderPane.setCenter(node);
            onSwitchPanel(dashboardBtnId);
        } catch (Exception e)
        {
            if (ZuseCore.DEBUG_MODE)
                e.printStackTrace();
        }
    }

    @Override
    public void onUpdate()
    {
    }
}

package it.unicam.cs.pa2021.jVectorRacetrack.view;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewMatchController {

    private boolean fileSelectedFlag = false;
    private boolean playerNumSelectedFlag = false;
    private boolean okFlag = false;

    @FXML
    private Label lblTackFile;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnPlay;

    @FXML
    private ComboBox<Integer> cBoxPlayerNum;

    @FXML
    private Button btnChoseTrack;

    @FXML
    public void handleCancel(ActionEvent event) {
        doClose(event);
    }

    @FXML
    public void handlePlay(ActionEvent event) {
        okFlag = true;
        doClose(event);
    }

    @FXML
    public void handlePlayerNum(ActionEvent event) {
        playerNumSelectedFlag = true;
        disablePlayButton();
    }

    @FXML
    public void handleTrackFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if (f!=null){
            lblTackFile.setText(f.getAbsolutePath());
            fileSelectedFlag = true;
            disablePlayButton();
        }
    }

    private void disablePlayButton() {
        btnPlay.setDisable(!fileSelectedFlag||!playerNumSelectedFlag);
    }

    private void doClose(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
        lblTackFile.setText("Choose a track file");
        cBoxPlayerNum.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        disablePlayButton();
    }

    public boolean isOk() {
        return okFlag;
    }

    public int getPlayerNumber() {
        return cBoxPlayerNum.getValue();
    }

    public String getTrackFile() {
        return lblTackFile.getText();
    }
}

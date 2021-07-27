package it.unicam.cs.pa2021.jVectorRacetrack.view;

import it.unicam.cs.pa2021.jVectorRacetrack.controller.*;
import it.unicam.cs.pa2021.jVectorRacetrack.model.player.PlayerStatus;
import it.unicam.cs.pa2021.jVectorRacetrack.model.player.PlayerUpdateListener;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.GridLocation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Set;

public class MainFXController implements PlayerUpdateListener<GridLocation>, GameUpdateListener {

    private GameController mvcController;


    @FXML
    public void handleNewMatch(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/setup.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));
            NewMatchController controller = loader.getController();
            stage.showAndWait();
            if (controller.isOk()) {
                mvcController = new BotMatchController(controller.getTrackFile(), controller.getPlayerNumber());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        this.mvcController.addListener(this);
    }

    @Override
    public void fireGameStatusChanged(GameStatus oldStatus, GameStatus newStatus) {

    }

    @Override
    public void firePlayerStatusChanged(PlayerStatus oldStatus, PlayerStatus newStatus) {

    }

    @Override
    public void fireAllNextPossiblePositions(Set<GridLocation> positions) {

    }

    @Override
    public void fireChosenNextPosition(GridLocation position) {

    }
}

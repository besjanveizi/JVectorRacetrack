package it.unicam.cs.pa2021.jVectorRacetrack.controller;

import it.unicam.cs.pa2021.jVectorRacetrack.model.player.Player;
import it.unicam.cs.pa2021.jVectorRacetrack.model.player.PlayerStatus;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.Cell;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.GridLocation;
import it.unicam.cs.pa2021.jVectorRacetrack.model.track.GridRacetrack;
import it.unicam.cs.pa2021.jVectorRacetrack.view.GameResults;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

/**
 * Represents the controller for the Vector Racetrack Pen and Paper Game.
 */
public abstract class GameController implements GameEngine {

    private final static Logger controllerLogger = Logger.getLogger(GameController.class.getName());

    private static final int MAX_TURNS = 500;
    private int turn = 1;

    private GameStatus status;
    private GameResults results;
    private final String trackFilePath;
    private GridRacetrack track;
    private final int numPlayers;
    protected final List<Player<GridLocation>> players;
    protected int currentPlayer = 0;

    public GameController(String trackFilePath, int numPlayers) {
        this.trackFilePath = trackFilePath;
        this.numPlayers = numPlayers;
        this.players = new LinkedList<>();
        setupLogger();
    }

    @Override
    public abstract void loadPlayers();

    @Override
    public final void newMatch() {
        try {
            loadRaceTrack(trackFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadPlayers();
        status = GameStatus.PLAYING;
        handleTurn();
    }

    private void handleTurn() {
        controllerLogger.info("======================================="
                + "\n\tGAME START"
                + "\n=======================================");
        while (!isEnded() && turn <= MAX_TURNS) {
            if (!currentPlayer().hasFinished()){
                try {
                    Cell<GridLocation> chosenPosition = currentPlayer().choseNextPosition();
                    if (!currentPlayer().isLegal(chosenPosition)) continue;

                    controllerLogger.info("\n____________________________" +
                            "\nTURN #"+turn +
                            "\nPLAYER: " + currentPlayer().getName() +
                            "\nSTATUS: " + currentPlayer().getStatus() +
                            "\nCURRENT: " + currentPlayer().getCurrentPosition());

                    currentPlayer().move(chosenPosition);

                    controllerLogger.info("\nCHOSEN: " + chosenPosition +
                            "\nSTATUS: " + currentPlayer().getStatus());
                    if (currentPlayer().isCrashed()) {
                        controllerLogger.info(" in " + chosenPosition);
                    }

                } catch (IndexOutOfBoundsException e) {
                    controllerLogger.info("\n____________________________" +
                            "\nTURN #"+turn +
                            "\nPLAYER: " + currentPlayer().getName() +
                            "\nCURRENT: " + currentPlayer().getCurrentPosition() +
                            "\nSTATUS: " + currentPlayer().getStatus() +
                            " trying to go outside the map");
                    changePlayer();
                    continue;
                }
            }
            checkIfGameEnded(players);
            changePlayer();
        }
        results = new GameResults(players);
    }

    private void loadRaceTrack(String trackFilePath) throws IOException {
        TrackTxtLoader loader = new TrackTxtLoader(trackFilePath);
        if (loader.isValid()) {
            track = new GridRacetrack(loader.getWidth(), loader.getHeight(), loader.getZones());
        }
        else handleInvalidFile(trackFilePath);
        if (!track.isValid()) handleInvalidTrack();
    }

    private void handleInvalidTrack() throws IOException {
        try {
            throw new IOException("Check if there is at least a starting point and a place to move!");
        } catch (IOException e) {
            controllerLogger.log(Level.WARNING, "Loaded track is not valid", e);
            throw e;
        }
    }

    private void handleInvalidFile(String trackFilePath) throws IOException {
        try {
            throw new IOException("File is not valid for playing the game: \n" +
                    "Check if it's not empty, the rows should be the same length");
        } catch (IOException e) {
            controllerLogger.log(Level.WARNING, "Failed loading track from '" + trackFilePath +"'", e);
            throw e;
        }
    }

    private Player<GridLocation> currentPlayer() {
        return players.get(currentPlayer);
    }

    @Override
    public final GameStatus status() {
        return status;
    }

    /**
     * Checks if the game is ended
     * @return true if the game is ended, otherwise false.
     * @see GameStatus
     */
    public final boolean isEnded() {
        return status() == GameStatus.END;
    }

    private void checkIfGameEnded(List<Player<GridLocation>> players) {
        if (players.stream().allMatch((p) -> p.getStatus()== PlayerStatus.FINISHED) || (turn == MAX_TURNS && currentPlayer == numPlayers-1))
            status = GameStatus.END;
    }

    private void changePlayer() {
        if (currentPlayer == numPlayers-1) turn++;
        this.currentPlayer = (currentPlayer+1) % numPlayers;
    }

    /**
     * Returns the track of the game.
     * @return the track of the game.
     */
    public final GridRacetrack getTrack() {
        return track;
    }

    /**
     * Returns the results of the game.
     * @return the <code>GameResults</code>.
     */
    public final GameResults getResults() {
        return results;
    }


    private static void setupLogger() {
        LogManager.getLogManager().reset();
        controllerLogger.setLevel(Level.ALL);
        setUpConsoleHandler();
        setUpFileHandler();
    }

    private static void setUpFileHandler() {
        try {
            FileHandler fh = new FileHandler("./src/main/resources/logFiles/game.log");
            fh.setLevel(Level.WARNING);
            controllerLogger.addHandler(fh);
        } catch (IOException e) {
            controllerLogger.log(Level.SEVERE, "File logger not working.", e);
        }
    }

    private static void setUpConsoleHandler() {
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.FINE);
        ch.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                return record.getMessage();
            }
        });
        controllerLogger.addHandler(ch);
    }
}

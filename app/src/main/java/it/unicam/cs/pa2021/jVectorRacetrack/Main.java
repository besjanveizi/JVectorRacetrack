package it.unicam.cs.pa2021.jVectorRacetrack;

import it.unicam.cs.pa2021.jVectorRacetrack.controller.BotMatchController;
import it.unicam.cs.pa2021.jVectorRacetrack.controller.GameController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final static Logger logger = Logger.getLogger(GameController.class.getName());

    private static String path = "../app/src/main/resources/tracks/simpleCircuit.txt";

    private static int numPlayers = 2;

    public static void main(String[] args) {
        setArguments(args);
        logger.info("Chosen track file: "+ path + "\n");
        logger.info("Chosen total number of players: "+ numPlayers + "\n");
        GameController controller = new BotMatchController(path, numPlayers);
        controller.newMatch();
        logger.log(Level.FINE, ""+ controller.getResults());
    }

    private static void setArguments(String[] args) {
        if (args.length == 2) {
            path = args[0];
            try {
                numPlayers = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e)
            {
                numPlayers = 2;
            }
        }
    }
}

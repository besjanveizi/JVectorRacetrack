package it.unicam.cs.pa2021.jVectorRacetrack;

import it.unicam.cs.pa2021.jVectorRacetrack.controller.BotMatchController;
import it.unicam.cs.pa2021.jVectorRacetrack.controller.GameController;

public class Main {

    private static String path = "../app/src/main/resources/tracks/simpleCircuit.txt";

    private static int numPlayers = 2;

    public static void main(String[] args) {
        setArguments(args);
        GameController controller = new BotMatchController(path, numPlayers);
        controller.newMatch();
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

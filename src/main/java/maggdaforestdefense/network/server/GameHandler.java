/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.network.server;

import java.util.HashMap;
import java.util.Vector;
import maggdaforestdefense.network.CommandArgument;
import maggdaforestdefense.network.server.serverGameplay.ServerGame;

import maggdaforestdefense.network.NetworkCommand;

/**
 *
 * @author David
 */
public class GameHandler {

    private static int currentGameId = 0;

    private HashMap<String, ServerGame> games;
    private Vector<CommandArgument> gamesAsArgs;

    public GameHandler() {
        games = new HashMap<>();
    }

    public void addGame(ServerGame game) {
        String id = getNextGameId();
        game.setGameId(id);
        games.put(id, game);
    }

    public NetworkCommand getGamesAsCommand() {
        gamesAsArgs = new Vector<>();
        games.forEach((String id, ServerGame game) -> {
            if (!game.isStarted()) {
                gamesAsArgs.add(new CommandArgument(id, game.getGameName()));
            }
        });

        CommandArgument[] argsArr = new CommandArgument[gamesAsArgs.size()];
        for (int i = 0; i < gamesAsArgs.size(); i++) {
            argsArr[i] = gamesAsArgs.get(i);
        }
        return new NetworkCommand(NetworkCommand.CommandType.SHOW_GAMES, argsArr);
    }

    public void addPlayerToGame(String gameId, Player player) {
        games.get(gameId).addPlayer(player);
    }

    public ServerGame getGame(String gameId) {
        return games.get(gameId);
    }

    public final static synchronized String getNextGameId() {
        currentGameId++;
        return String.valueOf(currentGameId);
    }

    public void removeGame(String id) {
        games.remove(id);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.network.client;

import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import maggdaforestdefense.gameplay.Game;
import maggdaforestdefense.network.NetworkCommand;
import maggdaforestdefense.storage.Logger;
import sun.rmi.runtime.Log;

/**
 *
 * @author David
 */
public class ClientCommandHandler extends Thread {

    private BufferedReader input;
    private LinkedBlockingQueue<NetworkCommand> queue;
    private Queue<NetworkCommand> workingQueue;

    private boolean isInGame = false;

    public ClientCommandHandler(BufferedReader in) {
        input = in;
        queue = new LinkedBlockingQueue<>();
        workingQueue = new LinkedList();

    }

    @Override
    public void run() {
        String line = "";
        try {
            while ((line = input.readLine()) != null) {
                if (NetworkCommand.testForKeyWord(line)) {
                    queue.add(NetworkCommand.fromString(line));
                }
                if (!isInGame) {        // IF GAME IS RUNNING: HANDLES COMMANDS WITH 60FPS IN GAMETHREAD; IF NOT IN GAME: HANDLES COMMANDS AS SOON AS THEY ARRIVE IN COMMAND HANDLER THREAD (THIS)
                    handleInput();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleInput() {
        queue.drainTo(workingQueue);
        while (workingQueue.size() != 0) {
            handleCommand(workingQueue.poll());
        }

    }

    private void handleCommand(NetworkCommand command) {
        //Logger.logClient("Command handled: " + command.toString());

        switch (command.getCommandType()) {
            case PERMIT_CONNECTION:
                NetworkManager.getInstance().notifyForAnswer();
                break;
            case UPDATE_TEST:
                Game.getInstance().updateTestPosition(command.getNumArgument("x"), command.getNumArgument("y"));
                break;
        }
    }

    void setInGame(boolean b) {
        isInGame = b;
    }
}
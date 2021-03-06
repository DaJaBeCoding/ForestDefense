/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;
import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.spawning.MobWave;
import maggdaforestdefense.network.server.serverGameplay.spawning.Spawnable;

/**
 *
 * @author David
 */
public class MobWavesLoader {

    private static File file;

    public static Vector<MobWave> loadMobWaves() {
        try {
            Vector<MobWave> retVect = new Vector<>();

            InputStream stream = GameImage.class.getClassLoader().getResourceAsStream("maggdaforestdefense/config/mob.waves");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            Vector<Spawnable> currentWaveVect = new Vector<>();
            while (reader.ready()) {
                String currentLine = reader.readLine();
                if (currentLine == "" || currentLine == null) {
                    continue;
                } else if (currentLine.toUpperCase().contains("NEXT")) {
                    retVect.add(new MobWave(currentWaveVect));
                    currentWaveVect = new Vector<>();
                } else {
                    String[] splitted = currentLine.split(" ");
                    int amount = 1;
                    double delay = Double.parseDouble(splitted[0]);
                    GameObjectType type;
                    switch (splitted[1].toUpperCase()) {
                        case "BORKENKAEFER":
                            type = GameObjectType.M_BORKENKAEFER;
                            break;
                        case "HIRSCHKAEFER":
                            type = GameObjectType.M_HIRSCHKAEFER;
                            break;
                        case "SCHWIMMKAEFER":
                            type = GameObjectType.M_SCHWIMMKAEFER;
                            break;
                        case "LAUFKAEFER":
                            type = GameObjectType.M_WANDERLAUFER;
                            break;
                        case "WASSERLAEUFER":
                            type = GameObjectType.M_WASSERLAEUFER;
                            break;
                        case "BLATTLAUS":
                            type = GameObjectType.M_BLATTLAUS;
                            break;
                        default:
                            Logger.logServer("ERROR! UNSUPPORTED MOB TYPE IN MOB.WAVES: " + splitted[1]);
                            throw new UnsupportedOperationException();
                    }
                    if (splitted.length > 2) {
                        amount = Integer.parseInt(splitted[2]);
                    }

                    currentWaveVect.add(new Spawnable(type, delay));
                    if (amount > 1) {
                        for (int i = 0; i < amount - 1; i++) {
                            currentWaveVect.add(new Spawnable(type, 0.01));
                        }
                    }
                }

            }

            return retVect;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

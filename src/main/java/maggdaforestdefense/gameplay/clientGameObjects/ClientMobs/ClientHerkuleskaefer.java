/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.clientGameObjects.ClientMobs;

import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.mobs.Mob;
import maggdaforestdefense.storage.GameImage;

/**
 *
 * @author David
 */
public class ClientHerkuleskaefer extends ClientBug{
    public ClientHerkuleskaefer(int id, double x, double y, double hp, Mob.MovementType movement) {
        super(id, x, y, hp, movement, GameObjectType.M_HERKULESKAEFER, GameImage.MOB_BUG_1);
    }
}

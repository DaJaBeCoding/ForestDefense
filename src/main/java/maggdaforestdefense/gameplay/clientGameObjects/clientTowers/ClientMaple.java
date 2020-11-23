/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.clientGameObjects.clientTowers;

import javafx.scene.image.Image;
import maggdaforestdefense.network.NetworkCommand;
import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.MapCell;
import maggdaforestdefense.network.server.serverGameplay.UpgradeSet;
import maggdaforestdefense.network.server.serverGameplay.towers.Maple;
import maggdaforestdefense.network.server.serverGameplay.towers.Spruce;
import maggdaforestdefense.network.server.serverGameplay.towers.Tower;
import maggdaforestdefense.storage.GameImage;

/**
 *
 * @author DavidPrivat
 */
public class ClientMaple extends ClientTower{
    public final static Tower.RangeType RANGE_TYPE = Maple.RANGE_TYPE;
    public ClientMaple(int id, int xIndex, int yIndex, double growingTime) {
        super(id, GameImage.TOWER_MAPLE_1, GameObjectType.T_MAPLE, UpgradeSet.MAPLE_SET, xIndex, yIndex, Maple.DEFAULT_RANGE, Maple.DEFAULT_HEALTH, growingTime);
        setPreserveRatio(true);
        setFitHeight(100);
        
        setLayoutX(xIndex*MapCell.CELL_SIZE);
        setLayoutY(yIndex*MapCell.CELL_SIZE);
    }

    @Override
    public void update(NetworkCommand updateCommand) {
        healthPoints = updateCommand.getNumArgument("hp");
        healthBar.update(xPos + 0.5*MapCell.CELL_SIZE, yPos, healthPoints);
        
        if(updateCommand.containsArgument("image")) {
            setImage(GameImage.values()[(int)updateCommand.getNumArgument("image")].getImage());
            updateGrowing(updateCommand.getNumArgument("timeLeft"));
        } else {
            if(!isMature) {
                isMature = true;
            }
        }
    }
    
    @Override
    public void setTier(int tier) {
        tier ++;
        Image image;
        switch(tier) {
            case 4:
                image = GameImage.TOWER_MAPLE_4.getImage();
                break;
            case 3:
                image = GameImage.TOWER_MAPLE_3.getImage();
                break;
            case 2:
                image = GameImage.TOWER_MAPLE_2.getImage();
                break;
            case 1: default:
                image = GameImage.TOWER_MAPLE_1.getImage();
                break;
        }
        
        setImage(image);
        upgradeMenu.setTreeImage(image);
    }
    
    @Override
    public Tower.RangeType getRangeType() {
        return RANGE_TYPE;
    }
}

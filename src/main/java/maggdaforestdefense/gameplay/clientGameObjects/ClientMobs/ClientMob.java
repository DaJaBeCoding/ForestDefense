/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.clientGameObjects.ClientMobs;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import maggdaforestdefense.gameplay.Game;
import maggdaforestdefense.gameplay.HealthBar;
import maggdaforestdefense.gameplay.InformationBubble;
import maggdaforestdefense.gameplay.clientGameObjects.ClientGameObject;
import maggdaforestdefense.gameplay.clientGameObjects.ViewOrder;
import maggdaforestdefense.network.server.serverGameplay.EffectSet;
import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.Map;
import maggdaforestdefense.network.server.serverGameplay.MapCell;
import maggdaforestdefense.network.server.serverGameplay.mobs.Mob;
import maggdaforestdefense.storage.GameImage;
import maggdaforestdefense.storage.Logger;
import maggdaforestdefense.util.GameMaths;

/**
 *
 * @author DavidPrivat
 */
public abstract class ClientMob extends ClientGameObject {

    protected HealthBar healthBar;
    protected DropShadow shadow;

    protected double shadowOffset = 5;

    protected Mob.MovementType movementType;
    protected double size;

    private double oldHealth;

    // Shadow offsets
    public final static double SHADOW_OFFSET_DIG_MULT = 0;

    public final static double SHADOW_OFFSET_WALK_MULT = 0.2;

    public final static double SHADOW_OFFSET_FLY_MULT = 0.54;
   
    public final static double ANGLE_OFFSET_RAD = 0.1 * Math.PI;

    public ClientMob(int id, GameImage image, GameObjectType type, double x, double y, double maxHealth, Mob.MovementType movement, double size) {
        super(id, image, type, x, y);
        if(x < 0 || x > Map.MAP_SIZE*MapCell.CELL_SIZE || y < 0 || y > Map.MAP_SIZE*MapCell.CELL_SIZE) {
            Logger.logClient("x: " + x + "  y: " + y + "      out of map!");
        }
        this.size = size;
        movementType = movement;
        healthBar = new HealthBar(maxHealth, GameImage.DISPLAY_HEALTH_BOX, GameImage.DISPLAY_HEALTH_BAR_MOB, size);

        Game.getInstance().getGameScreen().getGamePlayGroup().getChildren().add(healthBar);

        shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.2));
        shadow.setBlurType(BlurType.GAUSSIAN);
        setEffect(shadow);

        double fitWidth = getImage().getWidth();
        double fitHeight = getImage().getHeight();
        setPreserveRatio(true);
        if (fitWidth > fitHeight) {
            setFitWidth(size);
            setFitHeight(fitHeight * (size / fitWidth));
        } else {
            setFitHeight(size);
            setFitWidth(fitWidth * (size / fitHeight));
        }

        setViewOrder(ViewOrder.MOB);

        oldHealth = maxHealth;
    }
    


    protected void updateShadow() {
        switch (movementType) {
            case DIG:
                shadowOffset = SHADOW_OFFSET_DIG_MULT * size;
                setOpacity(0.4);
                break;
            case WALK:
                shadowOffset = SHADOW_OFFSET_WALK_MULT * size;
                setOpacity(1);
                break;
            case FLY:
                shadowOffset = SHADOW_OFFSET_FLY_MULT * size;
                setOpacity(1);
                break;
        }
        double direction = (getRotate() / 360) * 2 * Math.PI;
        
        //shadowOffsetX = 0;
        shadow.setOffsetX(-Math.sin(ANGLE_OFFSET_RAD - direction) * shadowOffset);
        shadow.setOffsetY(Math.cos(ANGLE_OFFSET_RAD - direction) * shadowOffset);
    }

    protected void handleEffects(EffectSet set) {
        if (set.isActive(EffectSet.EffectType.SENSITIVE)) {
            addColoredShadow(5, Color.YELLOW);
        } else if (set.isActive(EffectSet.EffectType.GOLDED)) {
            addColoredShadow(8, Color.ORANGE);
        } else {
            addColoredShadow(0, Color.TRANSPARENT);
        }
    }

    protected void updateHealth(double h) {

        healthBar.update(xPos + (getFitWidth() * 0.5d), yPos, h);
        if (oldHealth != h) {
            int hpDiff = (int) (h - oldHealth);
            if (hpDiff != 0) {
                Game.addGamePlayNode(new InformationBubble(String.valueOf(hpDiff), InformationBubble.InformationType.MOB_HP, xPos, yPos));
            }
        }

        oldHealth = h;

    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    @Override
    public void onRemove() {
        if (Game.getInstance().getGameScreen().getGamePlayGroup().getChildren().contains(healthBar)) {
            Game.getInstance().getGameScreen().getGamePlayGroup().getChildren().remove(healthBar);
        }

    }
    
    @Override
    public void addColoredShadow(double radius, Color c) {
        if (!(shadow.getInput() instanceof DropShadow && ((DropShadow) shadow.getInput()).getColor().equals(c))) {
            shadow.setInput(new DropShadow(radius, c));
        }
    }

}

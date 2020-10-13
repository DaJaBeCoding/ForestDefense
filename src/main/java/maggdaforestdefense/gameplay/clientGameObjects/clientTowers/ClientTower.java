/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.clientGameObjects.clientTowers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import maggdaforestdefense.gameplay.ClientMapCell;
import maggdaforestdefense.gameplay.EssenceButton;
import maggdaforestdefense.gameplay.Game;
import maggdaforestdefense.gameplay.HealthBar;
import maggdaforestdefense.gameplay.clientGameObjects.ClientGameObject;
import maggdaforestdefense.gameplay.ingamemenus.GrowingWaitingMenu;
import maggdaforestdefense.gameplay.ingamemenus.UpgradeMenu;
import maggdaforestdefense.network.CommandArgument;
import maggdaforestdefense.network.NetworkCommand;
import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.MapCell;
import maggdaforestdefense.network.server.serverGameplay.UpgradeSet;
import maggdaforestdefense.storage.GameImage;
import maggdaforestdefense.storage.Logger;
import maggdaforestdefense.network.client.NetworkManager;

/**
 *
 * @author DavidPrivat
 */
public abstract class ClientTower extends ClientGameObject{
    protected int xIndex, yIndex, range;
    protected UpgradeMenu upgradeMenu;
    protected GrowingWaitingMenu growingMenu;
    protected final UpgradeSet upgradeSet;
    
    protected HealthBar healthBar;
    protected double healthPoints;
    
    protected double growingTime;
    
    protected boolean isMature = false;
    
    protected ClientMapCell mapCell;
    
    protected DropShadow receiveEssenceShadow;
    protected PauseTransition removeEssenceShadowAnimation;
    
    protected EssenceButton essenceButton;
    
    public ClientTower(int id, GameImage image, GameObjectType type, UpgradeSet upgrades, int xIndex, int yIndex, int range, double health, double growingTime) {
        super(id, image, type, xIndex * MapCell.CELL_SIZE, yIndex * MapCell.CELL_SIZE);
        mapCell = Game.getInstance().getGameScreen().getMap().getCells()[xIndex][yIndex];
        
          this.upgradeSet = upgrades;
        upgradeMenu = new UpgradeMenu(this);
        growingMenu = new GrowingWaitingMenu(image, upgradeMenu.getTreeName(), growingTime);
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.range = range;
        this.growingTime = growingTime;
        
        healthPoints = health;
        healthBar = new HealthBar(healthPoints, GameImage.DISPLAY_HEALTH_BOX, GameImage.DISPLAY_HEALTH_BAR_TOWER, 80);
        
           
        essenceButton = new EssenceButton(this);
        Game.getInstance().getGameScreen().getGamePlayGroup().getChildren().addAll(healthBar, essenceButton);
      
        receiveEssenceShadow = new DropShadow();
        receiveEssenceShadow.setColor(Color.rgb(163, 73, 164));
        receiveEssenceShadow.setBlurType(BlurType.GAUSSIAN);
        receiveEssenceShadow.setSpread(1);
        removeEssenceShadowAnimation = new PauseTransition(Duration.seconds(5));
        removeEssenceShadowAnimation.setOnFinished((ActionEvent e)->{
            setEffect(null);
        });
        
        setOpacity(0.4);
        
     
       

    }
    
    protected void updateGrowing(double timeLeft) {
        growingMenu.update(timeLeft);
        if(isMature != true && timeLeft == 0) {
            isMature = true;
            mapCell.notifyTreeMature();
            setOpacity(1);
        }
    }
    
    public void doReceiveEssenceAnimation() {
        setEffect(receiveEssenceShadow);
        removeEssenceShadowAnimation.play();
        setOpacity(1);
        healthBar.setOpacity(1);
        
    }
    
    public void essenceNeeded() {
        showEssenceButton();
        setOpacity(0.2);
        healthBar.setOpacity(0.2);
    }
    
    
    public int getXIndex() {
        return xIndex;
    }
    
    public int getYIndex() {
        return yIndex;
    }
    
    public UpgradeMenu getUpgradeMenu() {
        return upgradeMenu;
    }
    
    public GrowingWaitingMenu getGrowingWaitingMenu() {
        return growingMenu;
    }
    
    public int getRange() {
        return range;
    }
    
    public UpgradeSet getUpgradeSet() {
        return upgradeSet;
    }
    
    @Override
    public void onRemove(){
        Game.getInstance().getGameScreen().safeRemoveGameplayNode(healthBar);
        Game.getInstance().getGameScreen().safeRemoveGameplayNode(essenceButton);
    }

    public void buyUpgrade(int tier, int type) {
        upgradeMenu.buyUpgrade(tier,type);
    }
    
    public abstract void setTier(int tier);

    public void requestEssence() {
        NetworkManager.getInstance().sendCommand(new NetworkCommand(NetworkCommand.CommandType.REQUEST_ESSENCE_TOWER, new CommandArgument[]{new CommandArgument("id", id)}));
    }
    
    public void showEssenceButton() {
        essenceButton.show();
    }

    public void hideEssenceButton() {
        essenceButton.hide();
    }

    

    

    

    
}

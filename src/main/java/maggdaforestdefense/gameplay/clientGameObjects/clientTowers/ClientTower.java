/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.clientGameObjects.clientTowers;

import java.util.Vector;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import maggdaforestdefense.gameplay.ClientMapCell;
import maggdaforestdefense.gameplay.EssenceButton;
import maggdaforestdefense.gameplay.Game;
import maggdaforestdefense.gameplay.HealthBar;
import maggdaforestdefense.gameplay.InformationBubble;
import maggdaforestdefense.gameplay.clientGameObjects.ClientGameObject;
import maggdaforestdefense.gameplay.clientGameObjects.ViewOrder;
import maggdaforestdefense.gameplay.ingamemenus.ContentBox;
import maggdaforestdefense.gameplay.ingamemenus.GrowingWaitingMenu;
import maggdaforestdefense.gameplay.ingamemenus.UpgradeMenu;
import maggdaforestdefense.gameplay.playerinput.ActiveSkillActivator;
import maggdaforestdefense.network.CommandArgument;
import maggdaforestdefense.network.NetworkCommand;
import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.MapCell;
import maggdaforestdefense.network.server.serverGameplay.UpgradeSet;
import maggdaforestdefense.storage.GameImage;
import maggdaforestdefense.storage.Logger;
import maggdaforestdefense.network.client.NetworkManager;
import maggdaforestdefense.network.server.serverGameplay.ActiveSkill;
import maggdaforestdefense.network.server.serverGameplay.EffectSet;
import maggdaforestdefense.network.server.serverGameplay.Upgrade;
import maggdaforestdefense.network.server.serverGameplay.towers.Tower.RangeType;
import maggdaforestdefense.util.Handler;
import maggdaforestdefense.util.NodeSizer;

/**
 *
 * @author DavidPrivat
 */
public abstract class ClientTower extends ClientGameObject {

    protected RangeType rangeType;
    protected int xIndex, yIndex;
    protected double range;
    protected UpgradeMenu upgradeMenu;
    protected GrowingWaitingMenu growingMenu;
    protected final UpgradeSet upgradeSet;

    protected HealthBar healthBar;
    protected double healthPoints, oldHealthPoints = -1;

    protected double growingTime;

    protected boolean isMature = false;

    protected ClientMapCell mapCell;

    protected DropShadow receiveEssenceShadow;
    protected PauseTransition removeEssenceShadowAnimation;

    protected EssenceButton essenceButton;

    protected Vector<ActiveSkillActivator> activeSkillActivators;
    


    public ClientTower(int id, GameImage image, GameObjectType type, UpgradeSet upgrades, int xIndex, int yIndex, double range, double health, double growingTime, RangeType rangeType) {
        super(id, image, type, xIndex * MapCell.CELL_SIZE, yIndex * MapCell.CELL_SIZE);
        mapCell = Game.getInstance().getGameScreen().getMap().getCells()[xIndex][yIndex];

        this.upgradeSet = upgrades;
        upgradeMenu = new UpgradeMenu(this);
        growingMenu = new GrowingWaitingMenu(image, upgradeMenu.getTreeName(), growingTime);
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.range = range;
        this.growingTime = growingTime;
        this.rangeType = rangeType;


        setPreserveRatio(true);
        setFitHeight(100);
        NodeSizer.setCenterOfImageView(this, (xIndex + 0.5) * MapCell.CELL_SIZE, (yIndex + 0.5) * MapCell.CELL_SIZE);

        healthPoints = health;
        healthBar = new HealthBar(healthPoints, GameImage.DISPLAY_HEALTH_BOX, GameImage.DISPLAY_HEALTH_BAR_TOWER, 80);

        essenceButton = new EssenceButton(this);
        Game.getInstance().getGameScreen().getGamePlayGroup().getChildren().addAll(healthBar, essenceButton);

        receiveEssenceShadow = new DropShadow();
        receiveEssenceShadow.setColor(Color.rgb(163, 73, 164));
        receiveEssenceShadow.setBlurType(BlurType.GAUSSIAN);
        receiveEssenceShadow.setSpread(1);
        removeEssenceShadowAnimation = new PauseTransition(Duration.seconds(5));
        removeEssenceShadowAnimation.setOnFinished((ActionEvent e) -> {
            setEffect(null);
        });

        setOpacity(0.4);

        setViewOrder(ViewOrder.TOWER);

        activeSkillActivators = new Vector<>();

        onRemove.add(() -> {
            Game.getInstance().getGameScreen().safeRemoveGameplayNode(healthBar);
            Game.getInstance().getGameScreen().safeRemoveGameplayNode(essenceButton);
            activeSkillActivators.forEach((ActiveSkillActivator a) -> {
                Game.getInstance().getGameScreen().safeRemoveGameplayNode(a);
                Game.getInstance().getGameScreen().safeRemoveGameplayNode(a.getCooldownIndicator());
            });
        });
        setMouseTransparent(true);
        
    }

    public void addActiveSkill(ActiveSkillActivator a) {
        activeSkillActivators.add(a);
        a.setVisible(false);
        Game.getInstance().getGameScreen().getGamePlayGroup().getChildren().add(a);
        relayoutActiveSkills();
        a.setVisible(true);
    }

    private void relayoutActiveSkills() {
        
        double rotAdd = 0;
        switch(activeSkillActivators.size()) {

            case 2: 
                rotAdd = 0;
                break;
            case 1: case 3:
                rotAdd = 0.5 * Math.PI;
                break;
            default:
                
                rotAdd = Math.PI*0.5;
                
        }
        for (int i = 0; i < activeSkillActivators.size(); i++) {
            ActiveSkillActivator currentActiveSkill = activeSkillActivators.get(i);

            double addX = MapCell.CELL_SIZE * 0.4 * Math.cos(rotAdd + 2 * Math.PI * ((double) i / (double) activeSkillActivators.size()));
            double addY = MapCell.CELL_SIZE * 0.4 * Math.sin(rotAdd + 2 * Math.PI * ((double) i / (double) activeSkillActivators.size()));

            currentActiveSkill.relocateCenter(((xIndex + 0.5) * MapCell.CELL_SIZE) + addX, ((yIndex + 0.5) * MapCell.CELL_SIZE) + addY);
        }
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    protected void updateGrowing(double timeLeft) {
        growingMenu.update(timeLeft);
        if (isMature != true && timeLeft == 0) {
            isMature = true;
            mapCell.notifyTreeMature();
            setOpacity(1);
            onMatured();
        }
    }
    
    public void updateSimplePermaStacks(NetworkCommand command) {   // OVERRIDABLE
        
    }

    protected void onMatured() {        // OVERRIDABLE

    }

    public void performActiveSkill(ActiveSkill skill) {      // OVERRIDABLE

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

    public double getRange() {
        return range;
    }

    public UpgradeSet getUpgradeSet() {
        return upgradeSet;
    }
    
    protected void updateHealth(double h) {

        healthBar.update(xPos + 0.5*MapCell.CELL_SIZE, yPos, h);
        if(oldHealthPoints == -1) {
            oldHealthPoints = h;
        }
        if(oldHealthPoints != h) {
            int hpDiff = (int)(h - oldHealthPoints);
            if(hpDiff != 0) {
            Game.addGamePlayNode(new InformationBubble(String.valueOf(hpDiff), InformationBubble.InformationType.TREE_HP, xPos, yPos));
            }
        }
        
        oldHealthPoints = h;
        
    }

    protected void handleEffects(EffectSet set) {
        if (set.isActive(EffectSet.EffectType.MAPLE_CHARGED)) {
            addColoredShadow(15, Color.RED);
        } else if (set.isActive(EffectSet.EffectType.MAPLE_ESCALATION)) {
            addColoredShadow(15, Color.DARKRED);
        } else if(set.isActive(EffectSet.EffectType.OAK_ERHAERTUNG)) {
            addColoredShadow(20, Color.SILVER);
        } else {
            addColoredShadow(0, Color.TRANSPARENT);
        }
    }

    public void buyUpgrade(int tier, int type) {    // Overridable
        upgradeMenu.buyUpgrade(tier, type);
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
    
    public double getCenterX() {
        return (0.5d + xIndex) * MapCell.CELL_SIZE;
    }
    
    public double getCenterY() {
        return (0.5d + yIndex) * MapCell.CELL_SIZE;
    }

    public void clearLorbeerTrading() {
        upgradeMenu.clearLorbeerTrading();
    }

    public void addLorbeerTrade(Upgrade upgrade) {
        upgradeMenu.addLorbeerTrade(upgrade);
    }
    
    protected void addBoxToMenu(ContentBox box) {
        upgradeMenu.getTreePane().getChildren().add(box);
    }
    
    protected class SimpleStacksBox extends ContentBox {
        private Label label;
        private String stacksName, suffix;
        
        public SimpleStacksBox(String stacksName, double startVal, String suffix) {
            label = new Label();
            this.stacksName = stacksName;
            this.suffix = suffix;
            updateLabel(startVal);
            getChildren().add(label);
        }
        
        public SimpleStacksBox(String stacksName, double startVal) {
            this(stacksName, startVal, "");
        }
        
        public void updateLabel(double newVal) {
            label.setText(stacksName + newVal + suffix);
        }
    }
    
    protected void generateSimplePermaStackInformationBubble() {
        Game.generateInformationBubble("+", InformationBubble.InformationType.SIMPLE_PERMA_STACKS, xPos + (MapCell.CELL_SIZE/2), yPos);
    }

}

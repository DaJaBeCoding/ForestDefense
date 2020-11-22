/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.ingamemenus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import maggdaforestdefense.gameplay.Game;
import maggdaforestdefense.gameplay.clientGameObjects.clientTowers.ClientTower;
import maggdaforestdefense.network.CommandArgument;
import maggdaforestdefense.network.NetworkCommand;
import maggdaforestdefense.network.client.NetworkManager;
import maggdaforestdefense.network.server.serverGameplay.Upgrade;
import maggdaforestdefense.storage.GameImage;
import maggdaforestdefense.storage.Logger;
import maggdaforestdefense.util.NodeSizer;

/**
 *
 * @author DavidPrivat
 */
public class BuyUpgradeButton extends StackPane {

    public static final double SIZE = 100;
    private Upgrade upgrade;
    private ImageView upgradeIcon, lockedIcon, checkIcon, notAvailableIcon;
    private CLICK_STATES clickState;
    private ClientTower tower;
    private PrizeLabel prizeLabel;
    private final int tier, type;
    private boolean bought, locked, tierBought;

    public BuyUpgradeButton(ClientTower owner, Upgrade upgrade, boolean locked, int tier, int type) {
        
        this.upgrade = upgrade;
        this.tier = tier;
        this.type = type;
        tower = owner;
        
       

        upgradeIcon = new ImageView(upgrade.getIcon());
        upgradeIcon.setFitHeight(SIZE);
        upgradeIcon.setFitWidth(SIZE);
        
        

        lockedIcon = new ImageView(GameImage.MENUICON_LOCK.getImage());
        lockedIcon.setFitHeight(SIZE / 2);
        lockedIcon.setPreserveRatio(true);
        
        checkIcon = new ImageView(GameImage.MENUICON_CHECK_GREEN.getImage());
        checkIcon.setPreserveRatio(true);
        checkIcon.setFitWidth(SIZE/2);
        
        notAvailableIcon = new ImageView(GameImage.MENUICON_NOT_AVAILABLE.getImage());
        notAvailableIcon.setPreserveRatio(true);
        notAvailableIcon.setFitWidth(SIZE/1.3);
        
        this.locked = locked;
        bought = false;
        tierBought = false;


        
        prizeLabel = new PrizeLabel(upgrade.getPrize());
        
        setBackground(new Background(new BackgroundFill(Color.DARKGREEN, new CornerRadii(20), Insets.EMPTY)));
        
        VBox vbox = new VBox(upgradeIcon, prizeLabel);
        vbox.setAlignment(Pos.CENTER);

        getChildren().addAll(vbox, lockedIcon, checkIcon, notAvailableIcon);
        
        clickState = CLICK_STATES.USUAL;

        update();
        
        new NodeSizer<ImageView>(upgradeIcon, SIZE, SIZE, true);
        new NodeSizer<ImageView>(checkIcon, SIZE/2, SIZE/2, true);
        new NodeSizer<ImageView>(lockedIcon, SIZE/2, SIZE/2, true);
        new NodeSizer<ImageView>(notAvailableIcon, SIZE/2, SIZE/2, true);


        setOnMousePressed((MouseEvent e) -> {
            if (clickState == CLICK_STATES.USUAL) {

                clickState = CLICK_STATES.CLICKED;
                update();
                
                
               
            }
        });
        
        setOnMouseReleased((MouseEvent e)->{
            if(clickState == CLICK_STATES.CLICKED) {
                clickState = CLICK_STATES.USUAL;
                
                
                update();
            }
        });
        
        setOnMouseClicked((MouseEvent e)->{
            tower.getUpgradeMenu().getSelectedUpgradeBox().setUpgrade(this, isBuyable());
        });

    }
    
    public boolean isBuyable() {
        if(locked || bought || tierBought) return false;
        return Game.getInstance().getCoins() >= upgrade.getPrize();
    }

    public void update() {

        switch (clickState) {
            case USUAL:
                setEffect(new ColorAdjust(0, 0, 0, 0));
                break;
            case CLICKED:
                setEffect(new ColorAdjust(0, 0, -0.5 , 0));
                break;           
        }
        if(locked) {
            upgradeIcon.setOpacity(0.2);
            bought = false;
            tierBought = false;
        } else if(bought) {
            upgradeIcon.setOpacity(1);
            locked = false;
            tierBought = false;
        } else if(tierBought) {
            upgradeIcon.setOpacity(0.2);
            locked = false;
            bought = false;
        } else {
            upgradeIcon.setOpacity(1);
        }
        lockedIcon.setVisible(locked);
        checkIcon.setVisible(bought);
        notAvailableIcon.setVisible(tierBought);
    
    }
    
    public void sendBuyOrder() {
        NetworkManager.getInstance().sendCommand(new NetworkCommand(NetworkCommand.CommandType.UPGRADE_BUTTON_CLICKED, new CommandArgument[]{
            new CommandArgument("id", tower.getGameObjectId()), 
            new CommandArgument("tier", tier), 
            new CommandArgument("type", type)}));
    }
    
    public CLICK_STATES getClickState() {
        return clickState;
    }

    public void setClickState(CLICK_STATES c) {
        clickState = c;
        update();
    }
    
    public void setBought(boolean b) {
        bought = b;
        update();
    }
    
    public void setLocked(boolean b) {
        locked = b;
        update();
    }
    
    public boolean isBought() {
        return bought;
    }
    
    public boolean isLocked() {
        return locked;
    }
    public Upgrade getUpgrade() {
        return upgrade;
    }
    
    public void setTierBought(boolean b) {
        tierBought = b;
        update();
    }
    
    public boolean isTierBought() {
        return tierBought;
    }
    
    

    public void updateCoins(double coins) {
        switch(clickState) {
            case USUAL: case CLICKED:
                prizeLabel.setBuyable(upgrade.getPrize() <= coins);
                break;
            default:
                prizeLabel.setBuyable(true);
                break;
        }
    }

    

    public static enum CLICK_STATES {
        USUAL,
        CLICKED;


    }
}

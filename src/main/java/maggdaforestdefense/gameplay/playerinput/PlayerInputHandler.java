/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.playerinput;

import javafx.scene.input.MouseEvent;
import maggdaforestdefense.gameplay.ClientMap;
import maggdaforestdefense.gameplay.ClientMapCell;
import maggdaforestdefense.gameplay.Game;
import maggdaforestdefense.gameplay.GameScreen;
import maggdaforestdefense.gameplay.ingamemenus.SideMenu;
import maggdaforestdefense.menues.MenuManager;
import maggdaforestdefense.storage.Logger;

/**
 *
 * @author DavidPrivat
 */
public class PlayerInputHandler {

    private SelectionSqare selectionSquare;
    private SelectionClickedSquare selectionClickedSquare;
    private RangeIndicator rangeIndicator;
    private ClientMap map;

    private static PlayerInputHandler instance;

    private double dragStartMouseX, dragStartMouseY, dragStartXInset, dragStartYInset;
    private boolean mousePressed = false;

    public PlayerInputHandler() {
        instance = this;

    }

    public void setMap(ClientMap map) {
        this.map = map;
        selectionSquare = new SelectionSqare();
        selectionClickedSquare = new SelectionClickedSquare();
        rangeIndicator = new RangeIndicator(map);
        Game.getInstance().getGameScreen().getGamePlayGroup().getChildren().addAll(rangeIndicator, selectionSquare, selectionClickedSquare);

    }

    public void mapCellClicked(ClientMapCell clickedCell) {
        Game.getInstance().getGameScreen().setNewContentSideMenu(clickedCell.getMenu());
        if (clickedCell.isPlanted()) {
            showRange(clickedCell);

        } else {
            rangeIndicator.setVisible(false);
        }

    }

    public static PlayerInputHandler getInstance() {
        return instance;
    }

    public void showRange(ClientMapCell cell) {
        if (cell.getCurrentTower() != null) {
            rangeIndicator.adjustRange(cell.getCurrentTower());

        }
    }

    public void setMousePressed(boolean b, MouseEvent e) {
        if (mousePressed == false && b == true) {
            dragStartMouseX = e.getSceneX();
            dragStartMouseY = e.getSceneY();
            dragStartXInset = Game.getInstance().getGameScreen().getXInset();
            dragStartYInset = Game.getInstance().getGameScreen().getYInset();
        }
        mousePressed = b;

    }

    public void mouseMoved(MouseEvent e) {
        if (mousePressed) {
            Game.getInstance().getGameScreen().setXInset(dragStartXInset + e.getSceneX() - dragStartMouseX);
            Game.getInstance().getGameScreen().setYInset(dragStartYInset + e.getSceneY() - dragStartMouseY);
        }
    }

}

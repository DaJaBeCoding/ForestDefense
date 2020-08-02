/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.gameplay.ingamemenus;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import maggdaforestdefense.network.server.serverGameplay.GameObjectType;
import maggdaforestdefense.network.server.serverGameplay.MapCell;
import maggdaforestdefense.network.server.serverGameplay.towers.Spruce;
import maggdaforestdefense.storage.GameImage;
import maggdaforestdefense.storage.Logger;

/**
 *
 * @author DavidPrivat
 */
public class PlantMenu extends VBox {

    private final static Font font = new Font(40);

    private MapCell.CellType cellType;

    private ImageView cellTypeView;

    private Label cellTypeLabel, addTowerLabel;

    private GridPane towerButtons;

    private int xIndex, yIndex;

    private VBox cellTypeBox, towerBox, buyTreeBorderBox;

    public PlantMenu(MapCell.CellType type, int x, int y) {
        cellType = type;
        xIndex = x;
        yIndex = y;

        //CellType
        cellTypeView = new ImageView();
        cellTypeView.setFitWidth(200);
        cellTypeView.setFitHeight(200);
        BorderPane celltTypeViewBorder = new BorderPane(cellTypeView);
        celltTypeViewBorder.setBorder(new Border(new BorderStroke(Color.web("022202"), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(5))));

        cellTypeLabel = new Label();
        cellTypeLabel.setFont(font);

        cellTypeBox = new VBox(cellTypeView, cellTypeLabel);
        cellTypeBox.setAlignment(Pos.CENTER);
        cellTypeBox.setBorder(new Border(new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
        cellTypeBox.setPadding(new Insets(20));

        //towers
        addTowerLabel = new Label("Add plant:");
        addTowerLabel.setFont(font);

        towerButtons = new GridPane();

        towerBox = new VBox(addTowerLabel, towerButtons);
        towerBox.setAlignment(Pos.CENTER);
        towerBox.setBorder(new Border(new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
        towerBox.setPadding(new Insets(20));

        // Buy tree
        buyTreeBorderBox = new VBox();
        buyTreeBorderBox.setAlignment(Pos.CENTER);
        buyTreeBorderBox.setBorder(new Border(new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
        buyTreeBorderBox.setPadding(new Insets(20));

        setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(cellTypeBox, towerBox, buyTreeBorderBox);

        updateTypeSpecific(type);
    }

    public void updateTypeSpecific(MapCell.CellType t) {
        try {
            switch (t) {
                case BASE:
                    cellTypeView.setImage(GameImage.MAP_CELL_BASE.getImage());
                    cellTypeLabel.setText("Base");  // LANGUAGE
                    break;
                case DIRT:
                    cellTypeView.setImage(GameImage.MAP_CELL_DIRT.getImage());
                    cellTypeLabel.setText("Dirt");
                    towerButtons.add(new PlantTowerButton(GameObjectType.T_SPRUCE, xIndex, yIndex, Spruce.DEFAULT_PRIZE, this), 0, 0);
                    break;
                case SAND:
                    cellTypeView.setImage(GameImage.MAP_CELL_SAND.getImage());
                    cellTypeLabel.setText("Sand");
                    break;
                case STONE:
                    cellTypeView.setImage(GameImage.MAP_CELL_STONE.getImage());
                    cellTypeLabel.setText("Stone");
                    break;
                case WATER:
                    cellTypeView.setImage(GameImage.MAP_CELL_WATER.getImage());
                    cellTypeLabel.setText("Water");
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCoins(double coins) {
        for (Node node : towerButtons.getChildren()) {
            if (node instanceof PlantTowerButton) {
                ((PlantTowerButton) node).updateCoins(coins);
            }
        }
    }

    public void setBuyTreeBox(PlantTowerButton.BuyTreeBox buyTreeBox) {
        buyTreeBorderBox.getChildren().clear();

        if (buyTreeBox == null) {
            safeAddNode(cellTypeBox);
            safeAddNode(towerBox);
            safeRemoveNode(buyTreeBorderBox);
        } else {

            buyTreeBorderBox.getChildren().add(buyTreeBox);

            safeRemoveNode(cellTypeBox);
            safeRemoveNode(towerBox);
            safeAddNode(buyTreeBorderBox);

        }

    }

    private void safeRemoveNode(Node node) {
        if (getChildren().contains(node)) {
            getChildren().remove(node);
        }
    }

    private void safeAddNode(Node node) {
        if (!getChildren().contains(node)) {
            getChildren().add(node);
        }
    }

}

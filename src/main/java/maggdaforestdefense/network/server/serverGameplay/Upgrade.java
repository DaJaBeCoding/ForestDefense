/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.network.server.serverGameplay;

import javafx.scene.image.Image;
import maggdaforestdefense.gameplay.Game;
import maggdaforestdefense.storage.GameImage;
import maggdaforestdefense.util.LanguageFetcher;

/**
 *
 * @author DavidPrivat
 */
public enum Upgrade {

    // SPRUCESPRUCE_1_6(GameImage.UPGRADE_SPRUCE_01_06.getImage(), "Lebenskristall", 50, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_1_6;})),
    SPRUCE_1_1(GameImage.UPGRADE_SPRUCE_01_01.getImage(), "Nadel Teilung", 100, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_1_1;})),
    SPRUCE_1_2(GameImage.UPGRADE_SPRUCE_01_02.getImage(), "Fichten-Monokultur", 100, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_1_2;})),
    SPRUCE_1_3(GameImage.UPGRADE_SPRUCE_01_03.getImage(), "R�stungsdurchdringende Nadeln", 100, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_1_3;})),
    SPRUCE_1_4(GameImage.UPGRADE_SPRUCE_01_04.getImage(), "H�her wachsen", 100, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_1_4;})),
    SPRUCE_1_5(GameImage.UPGRADE_SPRUCE_01_05.getImage(), "Nadelst�rkung", 100, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_1_5;})),
    SPRUCE_1_6(GameImage.UPGRADE_SPRUCE_01_06.getImage(), "Lebensraub", 100, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_1_6;})),
    SPRUCE_2_1(GameImage.UPGRADE_SPRUCE_02_01.getImage(), "Aufruestung", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_2_1;})),
    SPRUCE_2_2(GameImage.UPGRADE_SPRUCE_02_02.getImage(), "Fichten-Wut", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_2_2;})),
    SPRUCE_2_3(GameImage.UPGRADE_SPRUCE_02_03.getImage(), "Kritische Nadeln", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_2_3;})),
    SPRUCE_2_4(GameImage.UPGRADE_SPRUCE_02_04.getImage(), "Wurzelhieb", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_2_4;})),
    SPRUCE_2_5(GameImage.UPGRADE_SPRUCE_02_05.getImage(), "Erbarmungslose Fichte", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_2_5;})),
    SPRUCE_2_6(GameImage.UPGRADE_SPRUCE_02_06.getImage(), "Fichtenfreundschaft", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_2_6;})),
    SPRUCE_3_1(GameImage.UPGRADE_SPRUCE_03_01.getImage(), "Serienm�rder", 2000, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_3_1;})),
    SPRUCE_3_2(GameImage.UPGRADE_SPRUCE_03_02.getImage(), "Rasende Fichte", 2000, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_3_2;})),
    SPRUCE_3_3(GameImage.UPGRADE_SPRUCE_03_03.getImage(), "Riesenschreck", 2000, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_3_3;})),
    SPRUCE_3_4(GameImage.UPGRADE_SPRUCE_03_04.getImage(), "Fichtenforschung", 2000, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_3_4;})),
    SPRUCE_3_5(GameImage.UPGRADE_SPRUCE_03_05.getImage(), "Dominierende Nadeln", 2000, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_3_5;})),
    SPRUCE_3_6(GameImage.UPGRADE_SPRUCE_03_06.getImage(), "Aufruhr der Fichten", 2000, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_SPURCE_3_6;})),
    
    // MAPLE
    MAPLE_1_1(GameImage.UPGRADE_MAPLE_01_01.getImage(), "Ausbau", 150, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_1_1;})),
    MAPLE_1_2(GameImage.UPGRADE_MAPLE_01_02.getImage(), "Bund der Ahorne", 150, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_1_2;})),
    MAPLE_1_3(GameImage.UPGRADE_MAPLE_01_03.getImage(), "Tod den Gierigen", 150, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_1_3;})),
    MAPLE_2_1(GameImage.UPGRADE_MAPLE_02_01.getImage(), "Eskalation", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_2_1;})),
    MAPLE_2_2(GameImage.UPGRADE_MAPLE_02_02.getImage(), "Aufladen", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_2_2;})),
    MAPLE_2_3(GameImage.UPGRADE_MAPLE_02_03.getImage(), "Ersch�pfende Bl�tter", 500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_2_3;})),
    MAPLE_3_1(GameImage.UPGRADE_MAPLE_03_01.getImage(), "Zerschmetternde Bl�tter", 1500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_3_1;})),
    MAPLE_3_2(GameImage.UPGRADE_MAPLE_03_02.getImage(), "Gnadenlose Bl�tter", 1500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_3_2;})),
    MAPLE_3_3(GameImage.UPGRADE_MAPLE_03_03.getImage(), "Zerlegende Bl�tter", 1500, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_MAPLE_3_3;})),
    
    //LORBEER
    
    LORBEER_1_1(GameImage.UPGRADE_LORBEER_01_01.getImage(), "Weitreichende Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_1_1;})),
    LORBEER_1_2(GameImage.UPGRADE_LORBEER_01_02.getImage(), "Ertragreiche Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_1_2;})),
    LORBEER_1_3(GameImage.UPGRADE_LORBEER_01_03.getImage(), "Effizientere Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_1_3;})),
    LORBEER_1_4(GameImage.UPGRADE_LORBEER_01_04.getImage(), "Vorrats-Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_1_4;})),
    LORBEER_2_1(GameImage.UPGRADE_LORBEER_02_01.getImage(), "Brutale Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_2_1;})),
    LORBEER_2_2(GameImage.UPGRADE_LORBEER_02_02.getImage(), "Ernterausch", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_2_2;})),
    LORBEER_2_3(GameImage.UPGRADE_LORBEER_02_03.getImage(), "Massenproduktion", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_2_3;})),
    LORBEER_2_4(GameImage.UPGRADE_LORBEER_02_04.getImage(), "Wiederverwertung", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_2_4;})),
    LORBEER_3_1(GameImage.UPGRADE_LORBEER_03_01.getImage(), "Mechanische Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_3_1;})),
    LORBEER_3_2(GameImage.UPGRADE_LORBEER_03_02.getImage(), "Prestige Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_3_2;})),
    LORBEER_3_3(GameImage.UPGRADE_LORBEER_03_03.getImage(), "Kopfgeld Ernte", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_3_3;})),
    LORBEER_3_4(GameImage.UPGRADE_LORBEER_03_04.getImage(), "Tauschhandel", 10, (() -> {return Game.getInstance().language.UPGRADE_DESCRIPTION_LORBEER_3_4;}));


    private final Image icon;
    private final String name;
    private final int prize;
    private final LanguageFetcher languageFetcher;

    private Upgrade(Image i, String n, int p, LanguageFetcher descriptionFetcher) {
        icon = i;
        name = n;
        prize = p;
        languageFetcher = descriptionFetcher;
    }
    
    public Image getIcon() {
        return icon;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPrize() {
        return prize;
    }
    
    public String getDescription() {
        return languageFetcher.fetchLanguage();
    }
    
   
}

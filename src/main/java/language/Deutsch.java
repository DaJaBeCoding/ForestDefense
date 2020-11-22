/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

/**
 *
 * @author DavidPrivat
 */
public class Deutsch extends Language{

    public Deutsch() {
    // Tower names
    SPRUCE_NAME = "Fichte";
    MAPLE_NAME = "Ahorn";
    
    // Tower descriptions
    SPRUCE_DESCRIPTION = "Die Fichte schie�t einfache Nadelgeschosse, die 1 Gegner durchdringen k�nnen.";
    MAPLE_DESCRIPTION = "Der Ahorn schie�t Ringe aus Bl�ttern um sich, die Fl�chenschaden verursachen.";
        
        
    // Upgrade description
    UPGRADE_DESCRIPTION_SPURCE_1_1 = "Nadel Teilung: Trifft eine Nadel auf einen Gegner, teilt sich die Nadel in weitere Nadeln auf, die sich allerdings nicht weiter aufteilen.";
    UPGRADE_DESCRIPTION_SPURCE_1_2 = "Fichten-Monokultur: Je mehr Fichten auf der Karte sind, desto schneller schie�t die Fichte.";
    UPGRADE_DESCRIPTION_SPURCE_1_3 = "R�stungsdurchdringende Nadeln: Fichte verursacht mehr Schaden an Gegnern mit R�stung.";
    UPGRADE_DESCRIPTION_SPURCE_1_4 = "H�her Wachsen: Kann fliegende Gegner treffen.";
    UPGRADE_DESCRIPTION_SPURCE_1_5 = "Nadelst�rkung: Wenn eine Nadel einen Gegner trifft, f�gt sie dem Gegner dahinter mehr Schaden zu.";
    UPGRADE_DESCRIPTION_SPURCE_1_6 = "Lebensraub: Die Fichte regeneriert sich um einen Teil des verursachten Schadens.";
    
    UPGRADE_DESCRIPTION_SPURCE_2_1 = "Aufr�stung: Je mehr Gegner in der Reichweite der Fichte sind, desto mehr Nadeln schie�t die Fichte.";
    UPGRADE_DESCRIPTION_SPURCE_2_2 = "Fichten-Wut: Im Laufe einer Runde baut die Fichte f�r Treffer eine h�here Schussrate auf";
    UPGRADE_DESCRIPTION_SPURCE_2_3 = "Kritische Nadeln: Einige Nadeln treffen kritisch und teilen mehr Schaden aus.";
    UPGRADE_DESCRIPTION_SPURCE_2_4 = "Wurzelhieb: Kann mit den Wurzeln grabende Gegner angreifen.";
    UPGRADE_DESCRIPTION_SPURCE_2_5 = "Erbarmungslose Fichte: T�tet eine Nadel einen Gegner, so kann sie einen weiteren Gegner durchdringen.";
    UPGRADE_DESCRIPTION_SPURCE_2_6 = "Fichten-Freundschaft: Erh�ht die Lebensregeneration naher Fichten";
    
    UPGRADE_DESCRIPTION_SPURCE_3_1 = "Serienm�rder: Schie�t neue Nadeln pro get�tetem Gegner.";
    UPGRADE_DESCRIPTION_SPURCE_3_2 = "Rasende Fichte: Get�tete Gegner erh�hen die Schussrate permanent.";
    UPGRADE_DESCRIPTION_SPURCE_3_3 = "Riesenschreck: Zus�tzlicher Schaden wird abh�ngig vom verbleibenden Leben des Gegners hinzugef�gt.";
    UPGRADE_DESCRIPTION_SPURCE_3_4 = "Fichtenforschung: Die Fichte erkennt Schwachpunkte einger get�teter Gegner und erh�ht ihren Schaden gegen diese Gegnergruppe permanent.";
    UPGRADE_DESCRIPTION_SPURCE_3_5 = "Dominierende Nadeln: Je l�nger die Nadeln fliegen, desto mehr Schaden verursachen sie.";
    UPGRADE_DESCRIPTION_SPURCE_3_6 = "Aufruhr der Fichten: F�llt die Fichte unter 30% Leben, so opfern andere Fichten auf der Karte Leben, um die Fichte zu heilen";
}
    
}

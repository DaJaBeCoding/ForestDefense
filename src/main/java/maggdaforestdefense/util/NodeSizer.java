/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.util;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 *
 * @author DavidPrivat
 */
public class NodeSizer {


    public NodeSizer() {
 
    }

 
   
    public static double CALC_WIDTH_FROM_HEIGHT(ImageView i) {
        return i.getImage().getWidth() * (i.getFitHeight() / i.getImage().getHeight());
    }
    
    public static double CALC_HEIGHT_FROM_WIDTH(ImageView i) {
        return i.getImage().getHeight() * (i.getFitWidth() / i.getImage().getWidth());
    }
    
    public static void setCenterOfImageView(ImageView im, double layoutCenterX, double layoutCenterY) {
        double wOld = im.getImage().getWidth();
        double hOld = im.getImage().getHeight();
        
        double ratio = 0;
        if(hOld != 0) {
            ratio = wOld / hOld;
        }
        
        if(im.getFitWidth() != 0) {
            double wNew = im.getFitWidth();
            double hNew = wNew / ratio;
            im.setLayoutX(layoutCenterX - wNew/2.0d);
            im.setLayoutY(layoutCenterY - hNew/2.0d);
        } else if(im.getFitHeight() != 0) {
            double hNew = im.getFitHeight();
            double wNew = hNew * ratio;
            im.setLayoutX(layoutCenterX - wNew/2.0d);
            im.setLayoutY(layoutCenterY - hNew/2.0d);
        } else {
            im.setLayoutX(layoutCenterX - wOld/2.0d);
            im.setLayoutY(layoutCenterY - hOld/2.0d);
        }
    }
    
    public static void bindRegionToRegion(Region bindObject, Region bindTo) {
        bindObject.layoutXProperty().bind(Bindings.createDoubleBinding(() -> ((bindTo.getLayoutX() + bindTo.getWidth()/2.0d)  - bindObject.getWidth() / 2.0d), bindTo.layoutXProperty(), bindTo.widthProperty(), bindObject.widthProperty()));
        bindObject.layoutYProperty().bind(Bindings.createDoubleBinding(() -> ((bindTo.getLayoutY() + bindTo.getHeight()/2.0d)  - bindObject.getHeight() / 2.0d), bindTo.layoutYProperty(), bindTo.heightProperty(), bindObject.heightProperty()));
    }
    
    public static void bindRegionToImageView(Region bindObject, ImageView bindTo) {
        bindObject.layoutXProperty().bind(Bindings.createDoubleBinding(() -> ((bindTo.getLayoutX() + bindTo.getFitWidth()/2.0d)  - bindObject.getWidth() / 2.0d), bindTo.layoutXProperty(), bindTo.fitWidthProperty(), bindObject.widthProperty()));
        bindObject.layoutYProperty().bind(Bindings.createDoubleBinding(() -> ((bindTo.getLayoutY() + bindTo.getFitHeight()/2.0d)  - bindObject.getHeight() / 2.0d), bindTo.layoutYProperty(), bindTo.fitHeightProperty(), bindObject.heightProperty()));
    }
  
}

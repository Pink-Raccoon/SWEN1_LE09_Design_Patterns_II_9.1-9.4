package ch.zhaw.soe.swen1.le09.lightcontrol;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * JavaFX Lamp control which kind of implements the MVC pattern. 
 */
public class LampFX extends Region {
    private static final double PREF_LAMP_SIZE = 32;
    private static final double LAMP_SIZE_FACTOR = 0.8;
    private static final double DIST_LAMP_TEXT = 4;
    
    private View view;
    private Text text;
    
    // Model
    private ObjectProperty<RGBColorPercentage> lampColor;
    
    public LampFX(String name) {
        view = new View(this);
        getChildren().add(view);
        
        text = new Text(0, 0, name);
        text.setFill(Color.DARKRED);        
        Font font = new Font(12);
        text.setFont(font);
        getChildren().add(text);
        
        setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, new CornerRadii(2), 
                new BorderWidths(4))));
        
        setPadding(new Insets(4));
        
        lampColor = new SimpleObjectProperty<>(RGBColorPercentage.BLACK);
        lampColor.addListener(this::lampColorInvalidated);
    }

    /**
     * Setter method for lampColor
     * @param color
     */
    public void setLampColor(RGBColorPercentage color) {
        lampColor.set(color);
    }
    
    /**
     * Getter method for lampColor
     * @return
     */
    public RGBColorPercentage getLampColor() {
        return lampColor.get();
    }
    
    /**
     * Property for lampColor
     * @return
     */
    public ObjectProperty<RGBColorPercentage> lampColorProperty(){
        return lampColor;
    }
    
    /**
     * Helper method to update the view after lampColor properties has changed
     * @param observable
     */
    protected void lampColorInvalidated(Object observable) {
        view.draw();
    }
    
    /**
     * Computes the preferred with. Part of JavaFX Control API.
     */
    @Override
    protected double computePrefWidth(double width) {
        double textWidth = text.getBoundsInLocal().getWidth();
        double prefWidthCore = Math.max(textWidth, PREF_LAMP_SIZE);
        double prefWidth = 
                getPadding().getLeft() +
                getBorder().getInsets().getLeft() +
                prefWidthCore + 
                getBorder().getInsets().getRight() +
                getPadding().getRight();
        return prefWidth;
    }

    /**
     * Computes the preferred height. Part of JavaFX Control API 
     */
    @Override
    protected double computePrefHeight(double height) {
        double prefHeight = 
                getPadding().getTop() +
                getBorder().getInsets().getTop() +
                PREF_LAMP_SIZE + 
                DIST_LAMP_TEXT +                 
                text.getBoundsInLocal().getHeight() + 
                getBorder().getInsets().getBottom() +
                getPadding().getBottom();
        return prefHeight;
    }

    /**
     * Layouts the view and text elements. Part of JavaFX Control API 
     */
    @Override
    protected void layoutChildren() {
        double textHeight = text.getBoundsInLocal().getHeight();
        double height = getHeight();
        double spaceForLampHeight = height - 
                getPadding().getTop() - 
                getBorder().getInsets().getTop() -
                DIST_LAMP_TEXT -
                textHeight -   
                getBorder().getInsets().getBottom() -
                getPadding().getBottom();
        double spaceForLampWidth = getWidth() - 
                getPadding().getLeft() -
                getBorder().getInsets().getLeft() -
                getBorder().getInsets().getRight() -
                getPadding().getRight();
        double canvasSize = Math.min(spaceForLampWidth, spaceForLampHeight);
        view.setWidth(canvasSize);
        view.setHeight(canvasSize);
        
        double canvasX = getPadding().getLeft() + 
                getBorder().getInsets().getLeft() + 
                (spaceForLampWidth - canvasSize) / 2; 
        double canvasY = getPadding().getTop() + getBorder().getInsets().getTop();         
        view.relocate(canvasX, canvasY);
        view.draw();
        
        double textWidth = text.getBoundsInLocal().getWidth();
        double textOfsX = (canvasSize - textWidth) / 2;
        if (textOfsX < 0) {
            textOfsX = 0;
        }
        double textX = getPadding().getLeft() + getBorder().getInsets().getLeft() + textOfsX;
        double textY = getPadding().getTop() + getBorder().getInsets().getTop() + 
                canvasSize + DIST_LAMP_TEXT;
        text.relocate(Math.round(textX), Math.round(textY)); 
    }
    
    
    protected static class View extends Canvas {
        private GraphicsContext context;
        private LampFX lampFX;
        
        public View(LampFX lampFX) {
            super();
            this.context = getGraphicsContext2D();
            this.lampFX = lampFX;
        }

        protected void draw() {
            clearBackground();
            drawFilledCircle();
        }

        private void drawFilledCircle() {
            double circleWidth = getWidth() * LAMP_SIZE_FACTOR;
            double circleHeight = getWidth() * LAMP_SIZE_FACTOR;
            RGBColorPercentage rgbColor = lampFX.getLampColor();
            Color color = new Color(rgbColor.getRed() / 100d, 
                    rgbColor.getGreen() / 100d, rgbColor.getBlue() / 100d, 1d);
            context.setFill(color);        
            double circleX = (getWidth() - circleWidth) / 2; 
            double circleY = (getHeight() - circleHeight) / 2; 
            context.fillOval(circleX, circleY, circleWidth, circleHeight);
            
            context.setStroke(Color.BLACK);
            context.strokeOval(circleX, circleY, circleWidth, circleHeight);
        }

        private void clearBackground() {
            context.clearRect(0, 0, getWidth(), getHeight());
        }
    }
    
}

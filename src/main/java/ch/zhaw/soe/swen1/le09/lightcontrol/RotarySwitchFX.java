package ch.zhaw.soe.swen1.le09.lightcontrol;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * JavaFX rotary switch control. 
 *
 */
public class RotarySwitchFX extends VBox {
    private LightControl control;
    private Button increment;
    private Button decrement;
    private Button click;

    public RotarySwitchFX(String name, LightControl control) {
        super(2);
        this.control = control;
        setFillWidth(true);
        Label switchName = new Label(name);
        increment = new Button("+");    
        increment.setMaxWidth(Double.MAX_VALUE);
        increment.setOnAction(this::incrementHandler);
        decrement = new Button("-");
        decrement.setMaxWidth(Double.MAX_VALUE);
        decrement.setOnAction(this::decrementHandler);
        click = new Button("S");
        click.setMaxWidth(Double.MAX_VALUE);
        click.setOnAction(this::clickHandler);
        getChildren().addAll(switchName, increment, click, decrement);
        setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, new CornerRadii(2), 
                new BorderWidths(4))));
        setPadding(new Insets(4));    
    }

    protected void incrementHandler(ActionEvent event) {
        control.setLampsColor(RGBColorPercentage.WHITE);
    }
    
    protected void decrementHandler(ActionEvent event) {
        control.setLampsColor(RGBColorPercentage.BLACK);
    }
    
    protected void clickHandler(ActionEvent event) {
        control.setLampsColor(RGBColorPercentage.GRAY);
    }
}

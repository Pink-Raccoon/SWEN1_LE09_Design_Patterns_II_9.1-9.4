package ch.zhaw.soe.swen1.le09.lightcontrol;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX Application class which initializes simulated lamps and rotary switches as well. 
 */
public class LightControlApp extends Application{
    private LightControl control;
    
    @Override
    public void start(Stage primaryStage) {
        // create control class
        control = new LightControl();
        
        // initialize HW simulation components and add them to the 
        // control class 
        var label = new Label("Lighting Application with simulated Components");
        var lamp0 = new LampFX("Lamp Living Room");
        control.addLamp(lamp0); 
        var lamp1 = new LampFX("Lamp Home Office");
        control.addLamp(lamp1);
        var lamp2 = new LampFX("Lamp Bedroom");
        control.addLamp(lamp2);
        var lampRow = new HBox(8, lamp0, lamp1, lamp2);
        var switch0 = new RotarySwitchFX("Central Switch", control);
        var switchRow = new HBox(8, switch0);
        
        // prepare scene and add HW simulation components to the scene graph
        var scene = new Scene(new VBox(8, label, lampRow, switchRow), 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
     }

    public static void main(String[] args) {
        Application.launch(LightControlApp.class, args);
    };

}
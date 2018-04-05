package assignment5;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class Error {

    public static void display(String message){
        Stage error = new Stage();

        error.initModality(Modality.APPLICATION_MODAL);
        error.setTitle("ERROR");
        error.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
    }
}

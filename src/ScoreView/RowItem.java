package ScoreView;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.opencv.core.Mat;

public class RowItem{

    private ReadValue val;
    private String value = "";
    private String name;
    SimpleStringProperty ssp = new SimpleStringProperty("");

    public ReadValue getVal() {
        return val;
    }

    public RowItem(ReadValue value, String name){
        this.val = value;
        this.name = name;
    }

    public void update(Mat image) {
        this.value = "" + val.getValue(image);
        Platform.runLater(() -> ssp.setValue(name + ": " + value));
    }

    public StringProperty getStrProperty(){
        return ssp;
    }
}

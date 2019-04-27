package ScoreView;

import ScoreView.GUIStates.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Christian on 2019-03-24.
 */
public class DisplayImage extends Application {

    private Image shownImage;

    private static int frame = 200;

    private State currentState;
    private Scoreboard board;

    private ArrayList<ReadDigit> selectedDigits = new ArrayList<>();
    ObservableList<RowItem> numbers = null;

    public static void main(String[] args) throws Exception {
        System.load("C:\\Users\\Christian\\Desktop\\opencv\\build\\java\\x64\\opencv_java401.dll");
        launch();
    }

    public Mat generateImage() {
        VideoCapture cap = new VideoCapture("C:\\Users\\Christian\\Pictures\\samplewebcam.mp4");
        Mat image = new Mat((int) cap.get(Videoio.CAP_PROP_FRAME_WIDTH), (int) cap.get(Videoio.CAP_PROP_FRAME_HEIGHT), 16);

        cap.set(Videoio.CAP_PROP_POS_FRAMES, 100 * frame);
        cap.read(image);
        return image;
    }


    public ObservableList<RowItem> getNumbers() {
        return numbers;
    }

    public ArrayList<ReadDigit> getSelectedDigits() {
        return selectedDigits;
    }

    private ListView generateListView() {
        ListView<RowItem> lv = new ListView<>(numbers);

        lv.setCellFactory(param -> new ListCell<RowItem>() {
            @Override
            protected void updateItem(RowItem item, boolean empty) {
                super.updateItem(item, empty);

                if (!empty && item != null) {
                    setText(item.toString());
                }
                textProperty().unbind();
                if (item != null) {
                    textProperty().bind(item.getStrProperty());
                }
            }
        });

        lv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDigits = new ArrayList<>();
            selectedDigits.addAll(Arrays.asList(observable.getValue().getVal().digits));
        });

        return lv;
    }

    public MenuBar generateMenuBar() {
        MenuBar mbar = new MenuBar();
        Menu modify = new Menu("Digits");
        MenuItem setDigit = new MenuItem("Tie Digit");
        MenuItem addDigit = new MenuItem("Add Digit");


        mbar.getMenus().add(modify);
        modify.getItems().add(setDigit);
        modify.getItems().add(addDigit);


        addDigit.setOnAction((x) -> {
            this.currentState = new AddNumberState(board, this);
        });

        setDigit.setOnAction((x) -> {
            this.currentState = new TieDigitsState(board, this);
        });

        return mbar;
    }

    public HBox generateButtonPane() {
        HBox buttonPane = new HBox();
        Button finishTie = new Button("Finish Tie");
        Button deleteValue = new Button("Delete Value");
        finishTie.setOnMouseClicked((x) -> {
            if (this.currentState instanceof TieDigitsState) {
                ReadValue val = ((TieDigitsState) currentState).getValue();
                this.board.values.add(val);

                TextInputDialog tid = new TextInputDialog();
                tid.setContentText("Enter the name of the value");
                Optional<String> opt = tid.showAndWait();
                this.numbers.add(new RowItem(val, opt.orElse("Undefined")));

                this.currentState = new MonitorState(this.board, this);
            }
        });
        buttonPane.getChildren().addAll(finishTie, deleteValue);
        return buttonPane;
    }

    public ImageView generateImaging() {
        final ImageView im = new ImageView(shownImage);
        im.setOnMouseClicked((x) -> {
            currentState.mouseClicked((int) x.getX(), (int) x.getY());
            if (currentState.nextState() != null) {
                this.currentState = currentState.nextState();
            }
        });

        Timer tim = new Timer(1000, (x) -> {
            frame++;
            this.shownImage = currentState.render(this.generateImage());//board).dothing(frame++);
            im.setImage(this.shownImage);
        });
        tim.start();
        return im;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.board = (new Scoreboard());
        this.currentState = new FramingState(board, this);
        numbers = FXCollections.observableArrayList();

        VBox align = new VBox();
        MenuBar mbar = generateMenuBar();
        VBox rightPane = new VBox();
        HBox buttonPane = generateButtonPane();
        align.getChildren().add(mbar);

        final ImageView im = generateImaging();

        HBox alongside = new HBox();
        ListView lv = generateListView();
        alongside.getChildren().addAll(im, rightPane);
        rightPane.getChildren().addAll(buttonPane, lv);
        align.getChildren().add(alongside);

        Scene sc = new Scene(align, 800, 600, Color.BLACK);
        primaryStage.setScene(sc);
        primaryStage.show();
    }
}

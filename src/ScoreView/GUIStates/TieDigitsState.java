package ScoreView.GUIStates;

import ScoreView.*;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class TieDigitsState extends State {
    DisplayImage parent;
    ArrayList<ReadDigit> digits = new ArrayList<>();

    public TieDigitsState(Scoreboard sb, DisplayImage pare) {
        super(sb);
        this.parent = pare;
    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mouseClicked(int x, int y) {
        for (ReadDigit p : this.score.getDigits()) {
            if (x > p.getStartX() && x < p.getEndX() && y > p.getStartY() && y < p.getEndY()) {
                digits.add(p);
            }
        }
    }

    @Override
    public Image render(Mat image) {
        image = this.score.skewMat(image);
        for (ReadDigit x : this.score.getDigits()) {
            Scalar color = new Scalar(255, 255, 255);
            if (digits.contains(x)) {
                color = new Scalar(100, 255, 100);

            }
            Imgproc.rectangle(image, new Point(x.getStartX(), x.getStartY()), new Point(x.getEndX(), x.getEndY()), color, 4);
        }
        return this.score.convertMat(image);
    }

    public ReadValue getValue() {
        ReadValue val = new ReadValue(digits.size());
        for (ReadDigit dig : digits) {
            val.addDigit(dig);
        }
        return val;
    }

    @Override
    public State nextState() {
        return null;
    }
}

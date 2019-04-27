package ScoreView.GUIStates;

import ScoreView.*;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Christian on 2019-04-20.
 */
public class MonitorState extends State {
    PointI[] points;
    DisplayImage parent;

    public MonitorState(Scoreboard sb, DisplayImage par) {
        super(sb);
        this.parent = par;
        this.points = score.getSkewPts();
    }


    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mouseClicked(int x, int y) {

    }

    @Override
    public Image render(Mat image) {
        image = this.score.skewMat(image);

        for (RowItem x : this.parent.getNumbers()) {
            x.update(image);
        }

        for (ReadDigit x : this.score.getDigits()) {
            if (this.parent.getSelectedDigits().contains(x)) {
                Scalar  color = new Scalar(100, 100, 255);
                Imgproc.rectangle(image, new Point(x.getStartX(), x.getStartY()), new Point(x.getEndX(), x.getEndY()), color, 4);
            }

        }

        return this.score.convertMat(image);
    }

    @Override
    public State nextState() {
        return null;
    }
}

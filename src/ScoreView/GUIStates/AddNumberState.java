package ScoreView.GUIStates;

import ScoreView.DisplayImage;
import ScoreView.PointI;
import ScoreView.Scoreboard;
import javafx.scene.image.Image;
import org.opencv.core.Mat;

public class AddNumberState extends State {

    private DisplayImage parent;
    private PointI[] pts = new PointI[2];
    public AddNumberState(Scoreboard sb, DisplayImage par) {
        super(sb);
        this.parent = par;
    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mouseClicked(int x, int y) {
        int i = 0;
        while( i < pts.length && pts[i] != null){
            i++;
        }
        if (i != pts.length){
            pts[i] = new PointI(x, y);
            if (pts.length - 1 == i){
                this.score.addDigit(pts[0], pts[1]);
            }
        }

    }

    @Override
    public Image render(Mat image) {
        return this.score.convertMat(this.score.skewMat(image));
    }

    @Override
    public State nextState() {
        return (pts[1] == null) ? null : new MonitorState(this.score, this.parent);
    }
}

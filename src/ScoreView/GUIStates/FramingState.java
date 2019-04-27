package ScoreView.GUIStates;

import ScoreView.DisplayImage;
import ScoreView.PointI;
import ScoreView.Scoreboard;
import javafx.scene.image.Image;
import org.opencv.core.Mat;

public class FramingState extends State {

    public DisplayImage parent;
    private PointI[] pts = new PointI[4];
    public FramingState(Scoreboard sb, DisplayImage p) {
        super(sb);
        this.parent = p;
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
                this.score.setSkewPts(this.pts);
            }
        }

    }

    @Override
    public Image render(Mat image) {
        return this.score.convertMat(image);
    }

    @Override
    public State nextState() {
        return (pts[3] == null) ? null : new MonitorState(this.score, this.parent);
    }

}
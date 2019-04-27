package ScoreView.GUIStates;

import ScoreView.Scoreboard;
import javafx.scene.image.Image;
import org.opencv.core.Mat;

/**
 * Created by Christian on 2019-04-20.
 */
public abstract class State {
    public abstract void mouseMoved(int x, int y);
    public abstract void mouseClicked(int x, int y);
    public abstract Image render(Mat image);
    public abstract State nextState();

    Scoreboard score;

    public State(Scoreboard sb) {
        this.score = sb;
    }
}

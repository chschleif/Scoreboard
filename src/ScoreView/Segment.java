package ScoreView; /**
 * Created by Christian on 2019-03-24.
 */
import org.opencv.core.*;

public class Segment {
    private int posX;
    private int posY;
    public final int DEFAULT_THRESHOLD = 100;
    private int threshold = DEFAULT_THRESHOLD;
    public Segment(int x, int y){
        this.setPosX(x);
        this.setPosY(y);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean checkIfLit(Mat img) {

        double[] values = img.get(posY, posX);


        double luminance = values[0] * 0.2126 + values[1] * 0.7152 + values[2] * 0.0722;

        return luminance > threshold;

        //https://stackoverflow.com/questions/596216/formula-to-determine-brightness-of-rgb-color
    }
}

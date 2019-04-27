package ScoreView;

import org.opencv.core.Mat;

/**
 * Created by Christian on 2019-03-24.
 */
public class TestableLine {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public final int SEGMENT_DEPTH = 4;
    private int segments = SEGMENT_DEPTH;

    private Segment[] pieces;

    public TestableLine(PointI a, PointI b){
        this(a.X, a.Y, b.X, b.Y);
    }

    public TestableLine(int sX, int sY, int eX, int eY){
        this.startX = sX;
        this.startY = sY;
        this.endX = eX;
        this.endY = eY;

        int divisor = segments + 1;
        this.pieces = new Segment[segments];
        for(int i = 0; i < segments; i++){
            pieces[i] = new Segment(Math.min(startX, endX) + ((int)Math.floor(Math.abs(startX - endX) / divisor) * (i+1)),
                                    Math.min(startY, endY) + ((int)Math.floor(Math.abs(startY - endY) / divisor) * (i+1)));
        }

    }

    public float getLitConfidence(Mat img){
        int count = 0;
        for (Segment piece : pieces) {
            if (piece.checkIfLit(img)) {
                count++;
            }
        }
        return Math.min(count / (segments/2), 1);
    }
}

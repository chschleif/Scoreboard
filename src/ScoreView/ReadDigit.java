package ScoreView;

import org.opencv.core.Mat;

/**
 * Created by Christian on 2019-03-24.
 */
public class ReadDigit {
    /*
    .0.
    5.1
    .6.
    4.2
    .3.
     */
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    final boolean[][] expectations = new boolean[][]{
            {true, true, true, true, true, true, false},
            {false, true, true, false, false, false, false},
            {true, true, false, true, true, false, true},
            {true, true, true, true, false, false, true},
            {false, true, true, false, false, true, false},
            {true, false, true, true, false, true, true},
            {true, false, true, true, true, true, true},
            {true, true, true, false, false, false, false},
            {true, true, true, true, true, true, true},
            {true, true, true, true, false, true, true},
            {false, false, false, false, false, false, false} // all off; it's effectively zero!
    };

    private TestableLine[] lineTests;

    public ReadDigit(int sX, int sY, int eX, int eY){
        this.setStartX(sX);
        this.setStartY(sY);
        this.setEndX(eX);
        this.setEndY(eY);

        // assume our materials have been properly run through a homography (perspective warp)
        PointI cornerA = new PointI(sX, sY);
        PointI cornerB = new PointI(eX, sY);
        PointI cornerC = new PointI(eX, sY + (eY - sY)/2);
        PointI cornerD = new PointI(eX, eY);
        PointI cornerE = new PointI(sX, eY);
        PointI cornerF = new PointI(sX, sY + (eY - sY) / 2);

        lineTests = new TestableLine[]{
            new TestableLine(cornerA, cornerB),
                new TestableLine(cornerB, cornerC),
                new TestableLine(cornerC, cornerD),
                new TestableLine(cornerD, cornerE),
                new TestableLine(cornerE, cornerF),
                new TestableLine(cornerF, cornerA),
                new TestableLine(cornerF, cornerC)
        };
    }

    public int getValue(Mat img){
        float[] confidences = new float[lineTests.length];
        for(int i = 0; i < confidences.length; i++){
            confidences[i] = lineTests[i].getLitConfidence(img);
        }

        float closestConfidence = Float.MAX_VALUE;
        int bestGuess = 0;
        for(int i = 0; i < expectations.length; i++){
            float conf = 0;
            for(int j = 0; j < expectations[i].length; j++){
                conf += Math.abs((expectations[i][j] ? 1 : 0) - confidences[j]);
            }
            if (conf < closestConfidence){
                closestConfidence = conf;
                bestGuess = i % 10;
            }
        }
        return bestGuess;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
}

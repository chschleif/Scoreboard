package ScoreView; /*
  Created by Christian on 2019-03-07.
 */

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    public static void main(String[] args) {
    }

    ArrayList<ReadDigit> digits = new ArrayList<>();
    ArrayList<ReadValue> values = new ArrayList<>();

    private PointI[] skewPoints;

    public Image convertMat(Mat input){
        MatOfByte matOfByte = new MatOfByte();

        Imgcodecs.imencode(".jpg", input, matOfByte); // image => proj

        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;


        try {

            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            return SwingFXUtils.toFXImage(bufImage, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void setSkewPts(PointI[] pts){
        this.skewPoints = pts;
    }

    public PointI[] getSkewPts(){
        return this.skewPoints;
    }

    public void addDigit(PointI topLeft, PointI bottomRight){
        this.digits.add(new ReadDigit(topLeft.X, topLeft.Y, bottomRight.X, bottomRight.Y));
    }

    public Mat skewMat(Mat img){
        List<Point> corners = new ArrayList<Point>();
        List<Point> target = new ArrayList<Point>();
        corners.add(new Point(skewPoints[0].X, skewPoints[0].Y));
        corners.add(new Point(skewPoints[1].X, skewPoints[1].Y));
        corners.add(new Point(skewPoints[3].X, skewPoints[3].Y));
        corners.add(new Point(skewPoints[2].X, skewPoints[2].Y));

        double maxWidth = 500 ;//BottomRight.x - BottomLeft.x
        double maxHeight = 600 ; //BottomRight.y - TopLeft.y

        target.add(new Point(0,0));
        target.add(new Point(maxWidth-1,0));
        target.add(new Point(0,maxHeight-1));
        target.add(new Point(maxWidth-1,maxHeight-1));

        Mat trans= Imgproc.getPerspectiveTransform(Converters.vector_Point2f_to_Mat(corners), Converters.vector_Point2f_to_Mat(target));

        Mat proj = new Mat((int)maxHeight, (int)maxWidth, 16);
        Imgproc.warpPerspective(img, proj, trans, new Size(maxWidth,maxHeight));
        return proj;
    }


    public ArrayList<ReadDigit> getDigits() {
        return digits;
    }

    public ArrayList<ReadValue> getValues() {
        return values;
    }


}


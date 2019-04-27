

import ScoreView.ReadDigit;
import ScoreView.ReadValue;
import junit.framework.TestCase;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Christian on 2019-04-23.
 */
public class ScoreboardTest extends TestCase {

    public ScoreboardTest(){
        testReader();
    }
    @Test
    public void testReader() {
        System.load("C:\\Users\\Christian\\Desktop\\opencv\\build\\java\\x64\\opencv_java401.dll");

        Mat image = Imgcodecs.imread("C:\\Users\\Christian\\Downloads\\scoreboard images\\scoreboard-protective-cages-04.jpg");
        Imgproc.blur(image, image, new Size(5, 5));


        List<Point> corners = new ArrayList<Point>();
        List<Point> target = new ArrayList<Point>();
        corners.add(new Point(153,123));
        corners.add(new Point(507,47));
        corners.add(new Point(125,460));
        corners.add(new Point(487,423));
        double maxWidth = 500 ;//BottomRight.x - BottomLeft.x
        double maxHeight = 600 ; //BottomRight.y - TopLeft.y

        target.add(new Point(0,0));
        target.add(new Point(maxWidth-1,0));
        target.add(new Point(0,maxHeight-1));
        target.add(new Point(maxWidth-1,maxHeight-1));

        Mat trans=Imgproc.getPerspectiveTransform(Converters.vector_Point2f_to_Mat(corners), Converters.vector_Point2f_to_Mat(target));

        Mat proj = new Mat((int)maxHeight, (int)maxWidth, 16);
        Imgproc.warpPerspective(image, proj, trans, new Size(maxWidth,maxHeight));

        ReadValue p = new ReadValue(2);

        ReadDigit s1 = new ReadDigit(267, 20, 295, 120);
        ReadDigit s2 = new ReadDigit(317, 20, 345, 120);
        p.addDigit(s1);
        p.addDigit(s2);
        assertEquals("21", p.getValue(proj)+"");
    }
}

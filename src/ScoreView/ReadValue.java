package ScoreView;

import org.opencv.core.Mat;

import java.util.Arrays;

/**
 * Created by Christian on 2019-03-24.
 */
public class ReadValue {

    ReadDigit[] digits;
    public ReadValue(int size){
        digits = new ReadDigit[size];

    }

    public void addDigit(ReadDigit input){
        int i = 0;
        while(digits[i] != null){ i++; }
        digits[i] = input;
    }

    public int getValue(Mat img){
        Arrays.sort(digits, (a, b) ->  (a == null || b == null) ? ( a == null ? -1 : 1 ) : a.getStartX() - b.getStartX());
        int sum = 0;
        for(int i = 0; i < digits.length; i++){
            int pow = (int)Math.pow(10, (digits.length-i)-1);
            int val = digits[i].getValue(img);
            sum += pow*val;
        }
        return sum;
    }
}

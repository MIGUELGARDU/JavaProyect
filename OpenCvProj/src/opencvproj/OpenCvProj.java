/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvproj;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;

/**
 *
 * @author Placas
 */
public class OpenCvProj {
    static
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("Welcome to OpenCV"+Core.VERSION);
        Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0));
        System.out.println("OpenCV Mat: "+m);
        Mat mr1 = m.row(1);
        mr1.setTo(new Scalar(1));
        Mat mc5 = m.col(5);
        mc5.setTo(new Scalar(5));
        System.out.println("OpenCV Mat data\n"+m.dump());
        // TODO code application logic here
    }
    
}

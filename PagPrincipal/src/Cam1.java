
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;


public class Cam1 
{
    private Boolean begin = false;
    private VideoCapture video = null;
    private CaptureThread thread = null;
    private PrincPag image;
    
    public void start()
    {
        if(begin == false)
        {
            video = new VideoCapture(0);
            
            if (video.isOpened())
            {
                thread = new CaptureThread();
                thread.start();
                begin = true;
            }
        }
    }
    
    private MatOfByte matOfByte = new MatOfByte();
    private BufferedImage bufImage = null;
    private InputStream in;
    private Mat frameaux = new Mat();
    private Mat frame = new Mat(240,320,CvType.CV_8UC3);
    
    
    class CaptureThread extends Thread
    {
        @Override
        public void run()
        {
            if(video.isOpened())
            {
                while(begin ==true)
                {
                    video.retrieve(frameaux);
                    Imgproc.resize(frameaux, frame, frame.size());
                    
                    Imgcodecs.imencode(".jpg", frame, matOfByte);
                    byte[] byteArray = matOfByte.toArray();
                    
                    
                    try
                    {
                        in = new ByteArrayInputStream(byteArray);
                        bufImage = ImageIO.read(in);
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    } 
     
                    image.updateImage(bufImage);
                    
                    try{Thread.sleep(5);}catch(Exception ex){}
                }
            }
        }
    }
        public static void main(String[] args) throws IOException
    {
        new Main();
    }
    
}

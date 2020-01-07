
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;




public class Main 
{
    static
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private JFrame window;
    private PrincPag image;
    JTabbedPane Carriles=new JTabbedPane();
    JButton Configura = new JButton();
    
    public Main() throws IOException 
    {
        buildGUI() ;
    }
    
    private void buildGUI() throws IOException
    {
        
        window = new JFrame("HOLA");
        image =  new PrincPag(new ImageIcon("figs/320x240.gif").getImage());
        
        window.add(image, BorderLayout.CENTER);
        
        
        window.setSize(450, 900);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        start();
        
    }
    
    private Boolean begin = false;
    private VideoCapture video = null;
    private CaptureThread thread = null;
    
    private void start()
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

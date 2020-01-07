import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class Paint1 extends JPanel
{
   private Image img;
   
   public Paint1(String img)
   {
       this(new ImageIcon(img).getImage());
   }
   
   public Paint1(Image img)
   {
       this.img = img;
       Dimension size = new Dimension(640, 480);
       setPreferredSize(size);
       setMinimumSize(size);
       setMaximumSize(size);
       setSize(size);
       setLayout(null);
   }
   
   public void updateImage(Image img)
   {
       this.img = img;
       validate();
       repaint();
   }
   @Override
   public void paintComponent(Graphics g)
   {
       g.drawImage(img, 0, 0, null);
   }
}

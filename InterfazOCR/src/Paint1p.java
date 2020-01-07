import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class Paint1p extends JPanel
{
   private Image img;
   
   public Paint1p(String img)
   {
       this(new ImageIcon(img).getImage());
   }
   
   public Paint1p(Image img)
   {
       this.img = img;
       Dimension size = new Dimension(320, 240);
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
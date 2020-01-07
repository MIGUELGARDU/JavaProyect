/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ocrplacassystemas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Placas
 */
class PaintPlaca extends JPanel
{
    private Image img;
   
   public PaintPlaca(String img)
   {
       this(new ImageIcon(img).getImage());
   }
   
   public PaintPlaca(Image img)
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

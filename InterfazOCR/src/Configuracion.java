
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Configuracion {
    private final File archivo = new File(LeerConfiguracion.ruta);
    private final JPanel Configurar;
    private final JFrame Configura;
    private static Image icono_aceptar, icono_cancelar, icono_reset, icono_add;
    private BufferedWriter bw;
    private final JLabel Sistemm;
    private final JLabel CamarasExist;
    private final JLabel DimVentana;
    private final JLabel OrdCamaras;
    private final JLabel Carr1;
    private final JLabel Carr2;
    private final JLabel Carr3;
    private final JLabel Carr4;
    private final JButton AddSystem;
    private final JButton Aceptar;
    private final JButton Cancelar;
    private final JButton Reset;
    private final JComboBox Sistem;
    private final JComboBox nummCam;
    private final JComboBox DimmVen;
    private final JComboBox Ca1;
    private final JComboBox Ca2;
    private final JComboBox Ca3;
    private final JComboBox Ca4;
    
    public Configuracion() throws IOException
    {
        icono_aceptar = ImageIO.read(getClass().getResource("icono_aceptar.png"));
        icono_cancelar = ImageIO.read(getClass().getResource("icono_cancelar.png"));
        icono_reset = ImageIO.read(getClass().getResource("reset.png"));
        icono_add = ImageIO.read(getClass().getResource("add.png"));
        Configura = new JFrame("Configuracion");
        Configurar = new JPanel();
        Configurar.setLayout(new GridBagLayout());
        Sistemm = new JLabel();
        Sistemm.setText("Sistema Actual: "+LeerConfiguracion.Sistemaactual);
        Sistemm.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        AddSystem = new JButton();
        AddSystem.setIcon(new ImageIcon(icono_add));
        CamarasExist = new JLabel();
        CamarasExist.setText(LeerConfiguracion.Camarasexistentes+" Camaras instaladas");
        CamarasExist.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        DimVentana =  new JLabel();
        DimVentana.setText("Ventana "+LeerConfiguracion.DimensionVentana);
        DimVentana.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        OrdCamaras = new JLabel();
        OrdCamaras.setText("Orden de las Camaras");
        OrdCamaras.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        Carr1 = new JLabel();
        Carr1.setText("Camara "+LeerConfiguracion.Carril1+" en Carril 1");
        Carr1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        Carr2 = new JLabel();
        Carr2.setText("Camara "+LeerConfiguracion.Carril2+" en Carril 2");
        Carr2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        Carr3 = new JLabel();
        Carr3.setText("Camara "+LeerConfiguracion.Carril3+" en Carril 3");
        Carr3.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        Carr4 = new JLabel();
        Carr4.setText("Camara "+LeerConfiguracion.Carril4+" en Carril 4");
        Carr4.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        Aceptar =  new JButton();
        Aceptar.setIcon(new ImageIcon(icono_aceptar));
        Aceptar.setText("Aceptar");
        Aceptar.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        Cancelar = new JButton();
        Cancelar.setIcon(new ImageIcon(icono_cancelar));
        Cancelar.setText("Cancelar");
        Cancelar.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        Sistem = new JComboBox();
        for(int i=0; i<LeerConfiguracion.Sistemasvalidos.length;i++)
        {
            Long item = new Long(LeerConfiguracion.Sistemasvalidos[i]);
            Sistem.addItem(item);
        }
        nummCam = new JComboBox();
        Ca1 = new JComboBox();
        Ca2 = new JComboBox();
        Ca3 = new JComboBox();
        Ca4 = new JComboBox();
        int[] cama1 = {0,1,2,3};
        int[] cama2 = {1,0,2,3};
        int[] cama3 = {2,0,1,3};
        int[] cama4 = {3,0,1,2};
        int[] camaras ={1,2,3,4};
        for (int i=0; i<4;i++)
        {
            Long item1 = new Long(cama1[i]);
            Long item2 = new Long(cama2[i]);
            Long item3 = new Long(cama3[i]);
            Long item4 = new Long(cama4[i]);
            Ca1.addItem(item1);
            Ca2.addItem(item2);
            Ca3.addItem(item3);
            Ca4.addItem(item4);
            Long item = new Long(camaras[i]);
            nummCam.addItem("Cambiar a "+item+" Camaras");
        }
        DimmVen = new JComboBox();
        String[] Dimen ={"Chica", "Mediana", "Grande"};
        for (int i=0; i<3 ; i++)
        {
            String item = Dimen[i];
            DimmVen.addItem("Cambiar a "+item);
        }
        Reset = new JButton();
        Reset.setIcon(new ImageIcon(icono_reset));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        Configurar.add(Sistemm, c);
        c.gridx = 5;
        c.gridy = 0;
        c.gridwidth = 4;
        Configurar.add(Sistem,c);
        c.gridx = 9;
        c.gridy = 0;
        c.gridwidth = 1;
        Configurar.add(AddSystem,c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        Configurar.add(CamarasExist,c);
        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 5;
        Configurar.add(nummCam,c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        Configurar.add(DimVentana,c);
        c.gridx = 5;
        c.gridy = 2;
        c.gridwidth = 5;
        Configurar.add(DimmVen,c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 10;
        Configurar.add(OrdCamaras,c);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 5;
        Configurar.add(Carr1,c);
        c.gridx = 5;
        c.gridy = 4;
        c.gridwidth = 5;
        Configurar.add(Ca1,c);
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 5;
        Configurar.add(Carr2,c);
        c.gridx = 5;
        c.gridy = 5;
        c.gridwidth = 5;
        Configurar.add(Ca2,c);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 5;
        Configurar.add(Carr3,c);
        c.gridx = 5;
        c.gridy = 6;
        c.gridwidth = 5;
        Configurar.add(Ca3,c);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 5;
        Configurar.add(Carr4,c);
        c.gridx = 5;
        c.gridy = 7;
        c.gridwidth = 5;
        Configurar.add(Ca4,c);
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 4;
        Configurar.add(Aceptar,c);
        c.gridx = 4;
        c.gridy = 8;
        c.gridwidth = 4;
        Configurar.add(Cancelar,c);
        c.gridx = 8;
        c.gridy = 8;
        c.gridwidth = 2;
        Configurar.add(Reset,c);
        Configura.add(Configurar, BorderLayout.CENTER);
        Configura.setSize(550,500);
        Configura.setLocation(700,400);
        Configura.setVisible(true);
        Configura.setResizable(false);
        Configura.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
    }
    
}

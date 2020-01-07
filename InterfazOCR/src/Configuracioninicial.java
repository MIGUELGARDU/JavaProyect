import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;


public class Configuracioninicial{
    private File archivo = new File(LeerConfiguracion.ruta);
    private final JPanel VentanaConf;
    private JFrame VentanaConfi;
    private BufferedWriter bw;
    private static Image icono_aceptar;
    private static Image icono_cancelar;
    public Configuracioninicial()  throws IOException
    {
        LeerConfiguracion.Camarasexistentes = 0;
        LeerConfiguracion.Sistemasvalidos = new int[]
        {
            11,
            13,
            14,
            15,
            16,
            17,
            18,
            21,
            22,
            25,
            27,
            29,
            35,
            36,
            37,
            38,
            39,
            40,
            43,
            44,
            47,
            48,
            49,
            50
        };
        icono_aceptar = ImageIO.read(getClass().getResource("icono_aceptar.png"));
        icono_cancelar = ImageIO.read(getClass().getResource("icono_cancelar.png"));
        VentanaConfi = new JFrame("Configuracion Inicial");
        VentanaConf = new JPanel();
        VentanaConf.setLayout(new GridBagLayout());
        JLabel Sistem = new JLabel();
        Sistem.setText("Sistema Actual: ");
        Sistem.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        JComboBox Sistemas = new JComboBox();
        for(int i=0;i<LeerConfiguracion.Sistemasvalidos.length;i++)
        {
            Long item = new Long(LeerConfiguracion.Sistemasvalidos[i]);
            Sistemas.addItem(item);
        }
        JLabel texto = new JLabel();
        texto.setText("Camaras Existentes");
        texto.setFont(new Font("Comic Sans MS", Font.BOLD, 26));
        JButton BtnAcept = new JButton();
        JRadioButton cam1 = new JRadioButton();
        cam1.setText("1 Camara");
        cam1.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        JRadioButton cam2 = new JRadioButton();
        cam2.setText("2 Camaras");
        cam2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        JRadioButton cam3 = new JRadioButton();
        cam3.setText("3 Camaras");
        cam3.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        JRadioButton cam4 = new JRadioButton();
        cam4.setText("4 Camaras");
        cam4.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        cam1.setMnemonic(KeyEvent.VK_1);
        cam2.setMnemonic(KeyEvent.VK_2);
        cam3.setMnemonic(KeyEvent.VK_3);
        cam4.setMnemonic(KeyEvent.VK_4);
        cam1.addItemListener((ItemEvent) -> {
            LeerConfiguracion.Camarasexistentes = 1;
        });
        cam2.addItemListener((ItemEvent) -> {
            LeerConfiguracion.Camarasexistentes = 2;
        });
        cam3.addItemListener((ItemEvent) -> {
            LeerConfiguracion.Camarasexistentes = 3;
        });
        cam4.addItemListener((ItemEvent) -> {
            LeerConfiguracion.Camarasexistentes = 4;
        });
        ButtonGroup group = new ButtonGroup();
        group.add(cam1);
        group.add(cam2);
        group.add(cam3);
        group.add(cam4);
        BtnAcept.setIcon(new ImageIcon(icono_aceptar));
        BtnAcept.setText("Aceptar");
        BtnAcept.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        LeerConfiguracion.DimensionVentana = "Mediana";
        LeerConfiguracion.Carril1 = 0;
        LeerConfiguracion.Carril2 = 1;
        LeerConfiguracion.Carril3 = 2;
        LeerConfiguracion.Carril4 = 3;
        BtnAcept.addActionListener((ActionEvent) -> 
        {
            if (LeerConfiguracion.Camarasexistentes!=0)
            {
                int returnValue = JOptionPane.showConfirmDialog(null,
                        "Iniciar programa OCR con: "+"\n"+LeerConfiguracion.Camarasexistentes +" Camaras"+"\n"+"En el sistema "+Sistemas.getSelectedItem(),
                        "Confirmacion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if(returnValue == 0)
                {
                    try {
                        LeerConfiguracion.Sistemaactual = (int)(long)Sistemas.getSelectedItem();
                        bw = new BufferedWriter(new FileWriter(archivo));
                        bw.write("Camaras Existentes="+LeerConfiguracion.Camarasexistentes +"\n"+"Sistema Actual="+LeerConfiguracion.Sistemaactual+"\n"+"Sistemas Validos="+Arrays.toString(LeerConfiguracion.Sistemasvalidos)+"\n"+"Camara Carril1="+LeerConfiguracion.Carril1+"\n"+"Camara Carril2="+LeerConfiguracion.Carril2+"\n"+"Camara Carril3="+LeerConfiguracion.Carril3+"\n"+"Camara Carril4="+LeerConfiguracion.Carril4+"\n"+"Dimension Ventana="+LeerConfiguracion.DimensionVentana);
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Configuracioninicial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null, "Configuracion inicial guardada");
                    VentanaConfi.dispose();
                    SwingUtilities.invokeLater(() -> {
                        try {
                            new VentanaPrincipal();
                        } catch (IOException ex) {
                            Logger.getLogger(Configuracioninicial.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
            else 
            {
                JOptionPane.showMessageDialog(null, "Cantidad de Camaras no definida");
            }
        });
        JButton Btncancel = new JButton();
        Btncancel.setIcon(new ImageIcon(icono_cancelar));
        Btncancel.setText("Cancelar");
        Btncancel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        Btncancel.addActionListener((ActionEvent)->
        {
            int returnValue = JOptionPane.showConfirmDialog(null,
                    "Cerrar Configuracion Inicial? "+"\n"+"El Programa OCR Placas No se ejecutara",
                    "Cancelar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (returnValue == 0)
            {
                System.exit(0);
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(30,30,30,30);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 6;
        c.gridheight = 1;
        VentanaConf.add(Sistem,c);
        c.gridx = 6;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        VentanaConf.add(Sistemas,c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 15;
        c.gridheight = 1;
        VentanaConf.add(texto,c);
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 3;
        c.gridheight = 1;
        VentanaConf.add(cam1,c);
        c.gridx = 5;
        c.gridy = 4;
        c.gridwidth = 3;
        c.gridheight = 1;
        VentanaConf.add(cam2,c);
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 3;
        c.gridheight = 1;
        VentanaConf.add(cam3,c);
        c.gridx = 5;
        c.gridy = 6;
        c.gridwidth = 3;
        c.gridheight = 1;
        VentanaConf.add(cam4,c);
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 5;
        c.gridheight = 1;
        VentanaConf.add(BtnAcept,c);
        c.gridx = 6;
        c.gridy = 10;
        c.gridwidth = 5;
        c.gridheight = 1;
        VentanaConf.add(Btncancel,c);
        VentanaConfi.add(VentanaConf, BorderLayout.CENTER);
        VentanaConfi.setSize(450,500);
        VentanaConfi.setLocation(750,100);
        VentanaConfi.setVisible(true);
        VentanaConfi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VentanaConfi.setResizable(false);
        
    }
}

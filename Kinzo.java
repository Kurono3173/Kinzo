import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.net.*;


import java.util.Random;
/**
 * A Java class to demonstrate how to load an image from disk with the
 * ImageIO class. Also shows how to display the image by creating an
 * ImageIcon, placing that icon an a JLabel, and placing that label on
 * a JFrame.
 *
 * @author alvin alexander, devdaily.com
 */
public class Kinzo extends Thread
{	public Random random;
    String Filename;
    int rotar;
    private static DesktopApi youtube;
    public Kinzo(final String filename, int numero) throws Exception
    {
        Filename = filename;
        rotar = numero;
    }

    public void run() {

        random = new Random();
        JFrame editorFrame = new JFrame("OH DESIRE!!!");
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        Image imagen = null;
        URL urlDeLaImagen = null;
        BufferedImage ima2 = null;
        try
        {
            urlDeLaImagen = Kinzo.class.getClassLoader().getResource(Filename);
            ImageIcon icono = new ImageIcon(urlDeLaImagen);
            imagen = icono.getImage();
            ima2 = ImageIO.read(getClass().getResourceAsStream(Filename));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        imagen.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(urlDeLaImagen);
        JLabel jLabel = null;

        if(rotar == 0){
             jLabel = new JLabel();
            jLabel.setIcon(icono);
        }
        else{
            BufferedImage ima3 = rotate(ima2,Math.toRadians(random.nextInt(360)));
            icono = new ImageIcon(ima3);
            jLabel = new JLabel();
            jLabel.setIcon(icono);



        }

        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocation(random.nextInt(5000), random.nextInt(700));


        //editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);

        imagen.flush();
        try{
            Thread.sleep(500);
            editorFrame.dispose();
        }catch(InterruptedException e){
        }

    }
    //Funciones para rotar
    public static BufferedImage rotate(BufferedImage image, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        GraphicsConfiguration gc = getDefaultConfiguration();
        BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2, h / 2);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }
    //Fin funciones para rotar

    public static void main(String[] args) throws Exception
    {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder leer

            archivo = new File ("Video.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                System.out.println(linea);
                if(linea.equals(null)){

                }
                else{

                    youtube = new DesktopApi();
                    youtube.browse(new URI(linea));
                }
            }


        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();

                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }




        while(true){
            Kinzo kinzo1 = new Kinzo("desire.png", 0);
            kinzo1.start();
            Thread.sleep(100);
            Kinzo kinzo2 = new Kinzo("Beatrice1.png", 0);

            kinzo2.start();
            Thread.sleep(100);

            Kinzo kinzo3 = new Kinzo ("desire.png",1);
            kinzo3.start();
            Thread.sleep(100);
            Kinzo kinzo4 = new Kinzo ("Beatrice1.png",1);
            kinzo4.start();
            Thread.sleep(100);
        }
    }

}

import java.awt.*;
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
    private static DesktopApi youtube;
    public Kinzo(final String filename) throws Exception
    {
        Filename = filename;
    }

    public void run() {

        random = new Random();
        JFrame editorFrame = new JFrame("OH DESIRE!!!");
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        Image imagen = null;
        URL urlDeLaImagen = null;
        try
        {
            urlDeLaImagen = Kinzo.class.getClassLoader().getResource(Filename);
            ImageIcon icono = new ImageIcon(urlDeLaImagen);
            imagen = icono.getImage();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        imagen.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(urlDeLaImagen);

        JLabel jLabel = new JLabel();
        jLabel.setIcon(icono);
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



    public static void main(String[] args) throws Exception
    {
        youtube = new DesktopApi();
        youtube.browse(new URI("https://www.youtube.com/watch?v=QEOy4zEVFoU"));

        while(true){
            Kinzo kinzo1 = new Kinzo("desire.png");
            kinzo1.start();
            Thread.sleep(100);
            Kinzo kinzo2 = new Kinzo("Beatrice1.png");

            kinzo2.start();
            Thread.sleep(100);
        }
    }

    public static void play(URI uri){
        if(Desktop.isDesktopSupported()){
            try{
                Desktop.getDesktop().browse(uri);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }



}

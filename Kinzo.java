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
    int opcion;
    private static DesktopApi youtube;
    
    public Kinzo(final String filename, int numero) throws Exception
    {
        Filename = filename;
        opcion = numero;
    }

    public void run() {

        random = new Random();
        JFrame editorFrame = new JFrame("OH DESIRE!!!");
        editorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        BufferedImage imagen = null;
        try
        {
            imagen = ImageIO.read(getClass().getResourceAsStream(Filename));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        ImageIcon icono = new ImageIcon(imagen);
        JLabel jLabel = null;
        //Opcion sin cambios
        if(opcion == 0){
             jLabel = new JLabel();
            jLabel.setIcon(icono);
        }
        //Opcion rotada
        else if(opcion == 1){
            BufferedImage ima3 = rotate(imagen,Math.toRadians(random.nextInt(360)));
            icono = new ImageIcon(ima3);
            jLabel = new JLabel();
            jLabel.setIcon(icono);
        }
        //Opcion sepia
        else if(opcion == 2){
            BufferedImage ima3 = aplicarFiltroSepia(imagen);
            icono = new ImageIcon(ima3);
            jLabel = new JLabel();
            jLabel.setIcon(icono);
        }
        //Opcion rotada y con filtro invertido
        else{
            BufferedImage ima3 = rotate(imagen,Math.toRadians(random.nextInt(360)));
            BufferedImage ima4 = InvertImage(ima3);
            icono = new ImageIcon(ima4);
            jLabel = new JLabel();
            jLabel.setIcon(icono);
        }


        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
        editorFrame.pack();
        editorFrame.setLocation(random.nextInt(5000), random.nextInt(700));
        //editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);


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

    //Invertir filtro

        public BufferedImage InvertImage(BufferedImage image) {
            byte[] invertArray = new byte[256];

            for (int counter = 0; counter < 256; counter++)
                invertArray[counter] = (byte) (255 - counter);

            BufferedImageOp invertFilter = new LookupOp(new ByteLookupTable(0, invertArray), null);
            return invertFilter.filter(image, null);

        }

    //Fin invertir filtro

    //Filtro sepia
    public BufferedImage aplicarFiltroSepia(BufferedImage ima2) {
        BufferedImage bi = null;
        if(ima2 != null ) {
            bi = new BufferedImage(ima2.getWidth(), ima2.getHeight(),
                    ima2.getType());
            Color colorImagen = null; int red = 0, green = 0, blue = 0;
            for(int i = 0; i < ima2.getWidth(); i++) {
                for (int j = 1; j < (ima2.getHeight()-1); j++) {
                    colorImagen = new Color(ima2.getRGB(i, j));
                    try {
                        red = (int)(colorImagen.getRed() * 0.393 +
                                colorImagen.getGreen() * 0.769 + colorImagen.getBlue() * 0.189);
                        green = (int)(colorImagen.getRed() * 0.349 +
                                colorImagen.getGreen() * 0.686 + colorImagen.getBlue() * 0.168);
                        blue = (int)(colorImagen.getRed() * 0.272 +
                                colorImagen.getGreen() * 0.534 + colorImagen.getBlue() * 0.131);
                        colorImagen = new Color((red>255)?255:red,
                                (green>255)?255:green, (blue>255)?255:blue);
                    } catch (Exception e) {}
                    bi.setRGB(i, j, colorImagen.getRGB());
                }
            }
        }
        return bi;
    }
    //Fin filtro sepia

    public static void lectura(){
        // Apertura del fichero y creacion de BufferedReader para poder leer
        InputStream archivo = null;

        try {
            archivo = ClassLoader.getSystemResourceAsStream("Video.txt");
            BufferedReader br =  new BufferedReader(new InputStreamReader(archivo));

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
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
                if( null != archivo ){
                    archivo.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception
    {

        lectura();
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
            Kinzo kinzo5 = new Kinzo ("desire.png",2);
            kinzo5.start();
            Thread.sleep(100);
            Kinzo kinzo6 = new Kinzo ("Beatrice1.png",2);
            kinzo6.start();
            Thread.sleep(100);
            Kinzo kinzo7 = new Kinzo ("desire.png",3);
            kinzo7.start();
            Thread.sleep(100);
            Kinzo kinzo8 = new Kinzo ("Beatrice1.png",3);
            kinzo8.start();
            Thread.sleep(100);
        }
    }

}

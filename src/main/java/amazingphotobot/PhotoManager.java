package amazingphotobot;


import com.jhlabs.image.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class PhotoManager {
    //-----------------------------------filters--------------------------------
    public static BufferedImage sepiaFilter(BufferedImage img){
        BufferedImage sepiaFiltered = copyImage(img);
        int width = sepiaFiltered.getWidth();
        int height = sepiaFiltered.getHeight();
        
        for(int y = 0; y<height; y++){
            for(int x = 0; x<width; x++){
                int p = sepiaFiltered.getRGB(x, y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                int tr = (int)(0.393*r + 0.769*g + 0.189*b);
                int tg = (int)(0.349*r + 0.686*g + 0.168*b);
                int tb = (int)(0.272*r + 0.534*g + 0.131*b);
                if(tr > 255){
                    r = 255;
                }else{
                     r = tr;
                }
                if(tg > 255){
                    g = 255;
                }else{
                    g = tg;
                }
                if(tb > 255){
                    b = 255;
                }else{
                    b = tb;
                }
                p = (a<<24) | (r<<16) | (g<<8) | b;
                sepiaFiltered.setRGB(x, y, p);
            }
        }
        return sepiaFiltered;
    }  
    
    public static BufferedImage grayscaleFilter(BufferedImage img){
        BufferedImage grayscaleFiltered = copyImage(img);
        int width = grayscaleFiltered.getWidth();
        int height = grayscaleFiltered.getHeight();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = grayscaleFiltered.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                int avg = (r+g+b)/3;
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                grayscaleFiltered.setRGB(x, y, p);
            }
        }
        return grayscaleFiltered;
    }
    
    public static BufferedImage negativeFilter(BufferedImage img){
        BufferedImage negativeFiltered = copyImage(img);
        int width = negativeFiltered.getWidth();
        int height = negativeFiltered.getHeight();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int p = negativeFiltered.getRGB(x,y);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                p = (a<<24) | (r<<16) | (g<<8) | b;
                negativeFiltered.setRGB(x, y, p);
            }
        }
        return negativeFiltered;
    }
    
    public static BufferedImage blurFilter(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();     
        GaussianFilter gaussianFilter = new GaussianFilter(10);
        BufferedImage gaussianFiltered = new BufferedImage(width, height, img.getType());
        gaussianFilter.filter(img, gaussianFiltered);
        
        return gaussianFiltered;
    }
    
    public static BufferedImage mixFilter(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        ChannelMixFilter cmf = new ChannelMixFilter();
        cmf.setBlueGreen((int) (Math.random() * 250));
        cmf.setRedBlue((int) (Math.random() * 250));
        cmf.setGreenRed((int) (Math.random() * 250));
        cmf.setIntoB((int) (50 + Math.random() * 200));
        cmf.setIntoG((int) (50 + Math.random() * 200));
        cmf.setIntoR((int) (50 + Math.random() * 200));
        BufferedImage mixFiltered = new BufferedImage(width, height, img.getType());
        cmf.filter(img, mixFiltered);
        
        return mixFiltered;
    }
    
    public static BufferedImage posterizeFilter(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        PosterizeFilter pf = new PosterizeFilter();
        BufferedImage posterizeFiltered = new BufferedImage(width, height, img.getType());
        pf.filter(img, posterizeFiltered);
        
        return  posterizeFiltered;
    }
    
    public static BufferedImage diffusionFilter(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        DiffusionFilter df = new DiffusionFilter();
        BufferedImage diffusionFiltered = new BufferedImage(width, height, img.getType());
        df.filter(img, diffusionFiltered);     
        
        return diffusionFiltered;
    }
    
    public static BufferedImage edgeFilter(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        EdgeFilter ef = new EdgeFilter();
        BufferedImage edgeFiltered = new BufferedImage(width, height, img.getType());
        ef.filter(img, edgeFiltered);       
        
        return edgeFiltered;
    }
    
    public static BufferedImage tritoneFilter(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        TritoneFilter tf = new TritoneFilter();      
        tf.setShadowColor(8388863);
        tf.setMidColor(16639);
        tf.setHighColor(65470);
        BufferedImage tritoneFiltered = new BufferedImage(width, height, img.getType());
        tf.filter(img, tritoneFiltered);
        
        return tritoneFiltered;      
    }   
    
    public static BufferedImage glowFilter(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        GlowFilter gf = new GlowFilter(); 
        gf.setUseAlpha(true);
        gf.setEdgeAction(ConvolveFilter.CLAMP_EDGES);
        gf.setAmount(0.15f);
        BufferedImage glowFiltered = new BufferedImage(width, height, img.getType());
        gf.filter(img, glowFiltered);
        
        return glowFiltered;
    }
        
    //-----------------------------------effects--------------------------------
    public static BufferedImage kaleidoscopeEffect(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        KaleidoscopeFilter kf = new KaleidoscopeFilter();
        kf.setSides(3);
        BufferedImage kaleidoscopeFiltered = new BufferedImage(width, height, img.getType());
        kf.filter(img, kaleidoscopeFiltered);
        return kaleidoscopeFiltered;     
    }
        
    public static BufferedImage mirrorEffect(BufferedImage img){   
        BufferedImage mirrorFiltered = copyImage(img);
        int width = mirrorFiltered.getWidth();
        int height = mirrorFiltered.getHeight();     
        
        for(int y = 0; y < height; y++){
           for(int x = width/2; x>0; x--){
              mirrorFiltered.setRGB(width - x, y, mirrorFiltered.getRGB(x, y));
           }
        }    
        return mirrorFiltered;
    }
    
    public static BufferedImage sharpenEffect(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        SharpenFilter sf = new SharpenFilter();
        BufferedImage sharpenFiltered = new BufferedImage(width, height, img.getType());
        sf.filter(img, sharpenFiltered);
        
        return sharpenFiltered;
    }
    
    public static BufferedImage mosaicEffect(BufferedImage img){
        BufferedImage mosaicFiltered = copyImage(img);
        int width = mosaicFiltered.getWidth();
        int height = mosaicFiltered.getHeight();
        int tile = 8;
        
        for(int y = 1; y < height-tile; y+=tile){
            for(int x = 1; x < width-tile; x+=tile){
                int p = mosaicFiltered.getRGB(x+tile/2, y+tile/2);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                a = 4*a/5;
                r = 4*r/5;
                g = 4*g/5;
                b = 4*b/5;
                int np = (a<<24) | (r<<16) | (g<<8) | b;
                for(int sy = y; sy <= y+tile; sy++){
                    for(int sx = x; sx <= x+tile; sx++){
                        mosaicFiltered.setRGB(sx, sy, p);                        
                        if(sx == x || sx == x + tile || sy == y || sy == y + tile){
                            mosaicFiltered.setRGB(sx, sy, np);
                        }                       
                    }
                }
            }
        }
        return mosaicFiltered;
    }
    
    public static BufferedImage crystallizeEffect(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        CrystallizeFilter cf = new CrystallizeFilter();
        cf.setScale(8);
        BufferedImage crystallizeFiltered = new BufferedImage(width, height, img.getType());
        cf.filter(img, crystallizeFiltered);
        
        return crystallizeFiltered;
    }

    public static BufferedImage embossEffect(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        EmbossFilter ef = new EmbossFilter();       
        BufferedImage embossFiltered = new BufferedImage(width, height, img.getType());
        ef.filter(img, embossFiltered);
        
        return embossFiltered;
    }
    
    public static BufferedImage pinchEffect(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        PinchFilter pf = new PinchFilter();
        pf.setRadius(Math.min(width, height)/2);
        BufferedImage pinchFiltered = new BufferedImage(width, height, img.getType());
        pf.filter(img, pinchFiltered);
        
        return pinchFiltered;
    }

    public static BufferedImage chromeEffect(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        ChromeFilter cf = new ChromeFilter();       
        BufferedImage chromeFiltered = new BufferedImage(width, height, img.getType());
        cf.filter(img, chromeFiltered);
        
        return chromeFiltered;
    }

    public static BufferedImage swimEffect(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        SwimFilter sf = new SwimFilter(); 
        sf.setAmount(5);
        sf.setScale(20);
        BufferedImage swimFiltered = new BufferedImage(width, height, img.getType());
        sf.filter(img, swimFiltered);
        
        return swimFiltered;
    }
    
    //----------------------------------service---------------------------------
    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
    
    public static void cleanImage(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        for(int y = 0; y < height; y++){
           for(int x = 0; x < width; x++){
               /*
               int cp = img.getRGB(x,y);
                int a = (cp>>24)&0xff;
                int r = (cp>>16)&0xff;
                int g = (cp>>8)&0xff;
                int b = cp&0xff;
               System.out.println();
*/
               if(img.getRGB(x, y) == 0){
                   int p = 0;
                   boolean stop = false;
                   for(int sy = -2; sy<=2 && !stop; sy++){
                       for(int sx = -2; sx<=2 && !stop; sx++){                 
                           if(x+sx>0 && x+sx<width && y+sy>0 && y+sy<height &&(p = img.getRGB(x+sx, y+sy))!=0){
                               stop = true;
                           }
                       }
                   }
                   img.setRGB(x, y, p);
               }
            }
        }
    }  
    
    public static BufferedImage getImage(String name){
        BufferedImage image = null;
        try {
           image = ImageIO.read(new File(PhotoManager.class.getResource("/res/"+name).getFile()));
           
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return image;
    }
    
    //-----------------------contrast/brightness--------------------------------
    public static BufferedImage contrastChanger(BufferedImage img, int value) {
        int width = img.getWidth();
        int height = img.getHeight();
        RescaleOp op = new RescaleOp(1 + (value - 50)/50f, 0, null);
        BufferedImage contrastChanged = new BufferedImage(width, height, img.getType());
        op.filter(img, contrastChanged);
        return contrastChanged;
    }

    public static BufferedImage brightnessChanger(BufferedImage img, int value) {
        int width = img.getWidth();
        int height = img.getHeight();
        RescaleOp op = new RescaleOp(1, value - 50, null);
        BufferedImage brightnessChanged = new BufferedImage(width, height, img.getType());
        op.filter(img, brightnessChanged);
        return brightnessChanged;
    }
    
}

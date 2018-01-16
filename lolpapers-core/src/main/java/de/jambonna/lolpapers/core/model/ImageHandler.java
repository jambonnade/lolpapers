package de.jambonna.lolpapers.core.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

/**
 * Image manipulation utility
 */
public class ImageHandler {
    public BufferedImage resizeFit(BufferedImage inputImage, int boxw, int boxh) {
        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        double r = (double)w / (double)h;

        double boxr = (double)boxw / (double)boxh;

        int rw = boxw;
        int rh = boxh;
        int cropx = 0;
        int cropy = 0;

        if (boxr > r) {
            double rratio = (double)boxw / (double)w;
            rh = (int)Math.round(h * rratio);
            cropy = (rh - boxh) / 2;
        } else {
            double rratio = (double)boxh / (double)h;
            rw = (int)Math.round(w * rratio);
            cropx = (rw - boxw) / 2;
        }
        
//        System.out.printf("%f %f %d %d %d %d\n", boxr, r, rw, rh, cropx, cropy);


        BufferedImage resizedImage = Scalr.resize(inputImage,
                Scalr.Method.QUALITY,
                Scalr.Mode.FIT_EXACT,
                rw,
                rh);

        BufferedImage outputImage = Scalr.crop(resizedImage, cropx, cropy, boxw, boxh);
        return outputImage;
    }
    
    public BufferedImage loadImage(Path path) throws IOException {
        InputStream ins = Files.newInputStream(path);
        try {
            BufferedImage inputImage = ImageIO.read(ins);
            return inputImage;
        } finally {
            ins.close();
        }
    }
    
    public void saveJpeg(Path path, BufferedImage img, int jpegQuality) throws IOException {
        // Note : jpeg quality not handled for now
        // Note2 : some input images types (png) will make write to fail
        OutputStream outStream = Files.newOutputStream(path, StandardOpenOption.CREATE);
        try {
            ImageIO.write(img, "jpeg", outStream);
        } finally {
            outStream.close();
        }
    }
}

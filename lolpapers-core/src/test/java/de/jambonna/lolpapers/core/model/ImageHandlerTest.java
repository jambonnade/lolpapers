package de.jambonna.lolpapers.core.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.Ignore;

public class ImageHandlerTest {
    

    @Test
    @Ignore("For manual testing")
    public void testResizeFit() throws IOException {
        ImageHandler ih = new ImageHandler();
        Path testDir = Paths.get("/home/gruik");
        BufferedImage input = ih.loadImage(testDir.resolve("uhQzK7R.jpg"));
//        BufferedImage input = ih.loadImage(testDir.resolve("z.png"));
        BufferedImage resized;
        resized = ih.resizeFit(input, 600, 300);
        ih.saveJpeg(testDir.resolve("out.jpg"), resized, 90);
        resized = ih.resizeFit(input, 300, 600);
        ih.saveJpeg(testDir.resolve("out2.jpg"), resized, 90);
        resized = ih.resizeFit(input, 5000, 4000);
        ih.saveJpeg(testDir.resolve("ou3.jpg"), resized, 90);
        resized = ih.resizeFit(input, 4000, 5000);
        ih.saveJpeg(testDir.resolve("out4.jpg"), resized, 90);
    }
    
}

package com.example.vadim.dpapp.helper;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vadim on 16.05.2017.
 */
public class Converter {

    public BufferedImage image = null;
    public byte[] imageByte;

    public Converter(String strBase64, String shtrih) {
        try {
            convertBase64StringToBitmap(strBase64);
            File outputfile = new File(AppConstant.images+shtrih + ".png");
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage convertBase64StringToBitmap(String source) throws IOException {
        byte[] imageByte = Base64.decode(source);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();
        return image;
    }


}

package fr.r34.metagg.manager;

import fr.r34.metagg.Strings;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Utils {

    public Utils() {}

    public ImageIcon getImageFromResource(String path) throws IOException {
        URL odtUrl = this.getClass().getResource(path);
        if (odtUrl == null) throw new IllegalArgumentException(Strings.ERROR_ODT_ICON_NOT_LOADED);
        BufferedImage fileImg = ImageIO.read(odtUrl);
        return new ImageIcon(fileImg);
    }

}

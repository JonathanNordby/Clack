package data;

import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;

public class ImageClackData extends ClackData {

    private String imageLoc;
    private Image image;

    public ImageClackData(String userName, String imageLoc, int type) {
        super(userName, type);
        this.imageLoc = imageLoc;
        image = new Image(imageLoc);
    }

    public ImageClackData() {
        this("Anon", "", 0);
    }

    public void setImageLocation(String imageLoc) {
        this.imageLoc = imageLoc;
        image = new Image(imageLoc);
    }

    public String getImageLocation() {
        return imageLoc;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String getData() {
        return image.toString();
    }

    @Override
    public String getData(String key) {
        return image.toString();
    }
}

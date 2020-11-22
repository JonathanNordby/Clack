package src.data;

import src.data.ClackData;
import javafx.scene.media.Media;

public class VideoClackData extends ClackData {

    private Media video;
    private String videoLocation;

    public VideoClackData(String userName, String videoLocation, int type) {
        super(userName, type);
        video = new Media(videoLocation);
    }

    public VideoClackData() {
        this("Anon","",ClackData.CONSTANT_SENDVIDEO);
    }

    public Media getVideo() {return video;}

    @Override
    public String getData() {
        return null;
    }

    @Override
    public String getData(String key) {
        return null;
    }
}
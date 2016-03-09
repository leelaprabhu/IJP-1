package ijp;

import java.awt.image.BufferedImage;

/**
 * An object representing a photograph.
 * A photograph includes an image and a topic.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public class Photo {

    private BufferedImage image = null;
    private String topic = "";

    /**
     * Return the image associated with the photograph.
     * 
     * @return the image associated with the photograph
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Set the image associated with the photograph.
     * 
     * @param image the associated image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Return the description of the photograph.
     * 
     * @return the description of the photograph
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Set the description of the photograph.
     * 
     * @param topic the photo topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
}

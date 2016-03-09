package ijp;

/**
 * An object for controlling how the images are displayed in response
 * to user actions from the interface (viewer).
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public interface PhotoController {

    /**
     * Initialize (eg. set captions for buttons etc ..)
     */
    public void initialize();

    /**
     * Handle topic selections from the viewer.
     *
     * @param topicNumber the button number
     */
    public void topicSelected(int topicNumber);

    /**
     * Handle an exit command from the viewer.
     */
    public void exit();
}

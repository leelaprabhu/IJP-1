package ijp.controller;

import ijp.Photo;
import ijp.PhotoFinder;
import ijp.PhotoViewer;
import ijp.PhotoController;

/**
 * A simple controller for IJP Assignment 1 (for you to copy and modify).
 * This trivial controller includes a simple demonstration of the
 * photo viewer which will display a single photograph.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 16:40 25 Sep 2014
 */
public class PhotoController1 implements PhotoController {
    private PhotoFinder finder;
    private PhotoViewer viewer;

    /**
     * Create a new PhotoController.
     *
     * @param finder the photo finder object to locate images from the photo service (eg. Flickr)
     * @param viewer the photo viewer object to display images in a window
     */
    public PhotoController1(PhotoFinder finder, PhotoViewer viewer) {
        this.finder = finder;
        this.viewer = viewer;
    }

    /**
     * Initialize the controller.
     * In this simple case, we set the caption on a single selector (eg. button or menu item)
     * (So only the first selector will be enabled)
     */
    @Override
    public void initialize() {
        viewer.setCaption(1, "Edinburgh Informatics Building");
        viewer.setCaption(2, "Edinburgh Main Library");
        viewer.setCaption(3, "Edinburgh Appleton Tower");
    }

    /**
     * Handle a topic selection from the interface. This simple controller
     * retrieves an image of the Informatics Forum and displays it in the
     * viewer. Notice that only one selector has been enabled, so the topic
     * number can be ignored (it will always be 1).
     *
     * @param n the topic number
     */
    @Override
    public void topicSelected(int topicNumber) {
        String selectedTopic = new String();
        switch(topicNumber)
        {
            case 1: selectedTopic = "Edinburgh Informatics Building"; break;
            case 2: selectedTopic = "Edinburgh Main Library"; break;
            case 3: selectedTopic = "Edinburgh Appleton Tower"; break;
            default: selectedTopic = "Edinburgh Old College"; break;
        }
        Photo photo = finder.find(selectedTopic, 5);
        if (photo != null) {
            viewer.show(photo);
        }
    }

    /**
     * Handle the "Close" button on the interface window.
     * Exits the application.
     */
    @Override
    public void exit() {
        System.exit(0);
    }
}

package ijp;

/**
 * An object for retrieving photos from a photo service such as Flickr.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 15:12 19 Sep 2014
 */
public interface PhotoFinder {

    /**
     * Return a photo object from a free-text search of the photo service.
     * Normally, there will be more than one match, and the <code>n</code>'th
     * matching photograph is returned. <p> If there is no matching image, or
     * <code>n</code> is greater than the number of matching images, or there is
     * any other kind of error, then an error message is printed to the console
     * and null is returned.
     *
     * @param searchText the free-text search string
     * @param n the number of the matching photo to return
     * @return the requested photo, or null if not available
     */
    public Photo find(String searchText, int n);
}

package ijp.finder;

import ijp.Photo;
import ijp.PhotoFinder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import javax.imageio.ImageIO;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * An object for retrieving photos from Flickr using a free-text search string.
 *
 * @author  Paul Anderson &lt;dcspaul@ed.ac.uk&gt;
 * @version 15:12 19 Sep 2014
 */
public class FlickrPhotoFinder implements PhotoFinder {

    // the URL for the Flickr REST API
    private static String flickrURLString = "https://api.flickr.com/services/rest/";
    // the method for photo search
    private static String searchMethod = "?method=flickr.photos.search";
    // the Flickr API key (see http://bit.ly/8B4ql0)
    private String apiKey;
    // the URL of the image 
    private String photoURL;
    
    
    /**
     * Create a Flickr photofinder and set the API key.
     * API keys can be obtained from <a href=http://bit.ly/8B4ql0>http://bit.ly/8B4ql0</a>
     *
     * @param apiKey the Flickr API key
     */
   public FlickrPhotoFinder(String apiKey) {
        this.apiKey = apiKey;
    }

   /**
    * Create a Flickr photofinder with no API key.
    * The "find" method will look for a key in
    * <code>~/.flickrapikey</code> (on Unix-based systems).
    * If no API key is found, a runtime exception is thrown.
    * API keys can be obtained from <a href=http://bit.ly/8B4ql0>http://bit.ly/8B4ql0</a>
    *
    */
    public FlickrPhotoFinder() {
        this.apiKey = "";
    }

    // Handles events during the SAX parsing of the XML from the REST request
    private class FlickrHandler extends DefaultHandler {

        // we are only interested in element start events
        // stubs for the other events are provided by the default handler
        @Override
        public void startElement(
                String uri,
                String localName,
                String qName,
                Attributes attributes) {

            // we are only interested in "photo" elements
            if (localName.equals("photo")) {

                // extract the relevant attributes from the photo
                String server = attributes.getValue("server");
                String id = attributes.getValue("id");
                String secret = attributes.getValue("secret");
                // title currently unused
                // String title = attributes.getValue("title");

                // compose a URL to locate the image itself
                // see here: http://bit.ly/10EGq7
                // notice that we will accept any size of image ...
                photoURL = "http://static.flickr.com/" + server
                        + "/" + id + "_" + secret + ".jpg";
            }
        }
    }

    // we report errors to the console rather than using exceptions, because
    // the course has not yet covered exceptions.
    private void reportError(String msg, String searchText, int index) {
        System.err.println("failed to retrieve photo " + index
                + " for search string: " + searchText + "\n- " + msg);
    }

    // check the API key before making a search request
    private void checkAPIKey() {

        // if the API key is null, try looking for a ~/.flickrapikey file
        if (apiKey == null || apiKey.equals("")) {
           File dotFile = new File(
                System.getProperty("user.home"), ".flickrapikey");
           try {
                FileInputStream fstream = new FileInputStream(dotFile);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                apiKey = br.readLine();
                in.close();
            } catch (Exception e) {
                throw new RuntimeException("missing Flickr API key: " + dotFile);
            }
        }
    }

    /**
     * Returns a photo object from a free-text search of Flickr. Normally, there
     * will be more than one match, and the <code>n</code>'th matching photograph
     * is returned. <p> If there is no matching image, or <code>n</code> is greater
     * than the number of matching images, or there is any other kind of error,
     * then an error message is printed to the console and null is returned.
     *
     * @param topic the free-text topic string
     * @param n the number of the matching photo to return
     * @return the requested photo, or null if not available
     */
    @Override
    public Photo find(String topic, int n) {

        // the flickr search API is described here: http://bit.ly/10F8zp

        // the image of the downloaded photo
        BufferedImage bufferedImage;

        // check the API key before making a search request
        checkAPIKey();

        try {

            // initialise the URL
            // this instance variable is filled in by the SAX tree walker
            // when it encounters an appropriate node.
            this.photoURL = null;

            // based on code from here: http://bit.ly/M6Ee6N
            String urlString = flickrURLString + searchMethod
                    + "&api_key=" + apiKey
                    + "&per_page=1"
                    + "&page=" + n
                    + "&content_type=1" // photos only 
                    + "&media=photos" // no video 
                    + "&text=" + URLEncoder.encode(topic, "utf-8");

            // this handles the SAX parse events
            FlickrHandler handler = new FlickrHandler();

            // extract the photo url from the metadata
            // from: http://bit.ly/lvDqMB
            XMLReader myReader = XMLReaderFactory.createXMLReader();
            myReader.setContentHandler(handler);
            myReader.parse(new InputSource(new URL(urlString).openStream()));

            // something went wrong - we never saw any photo info ...
            if (photoURL == null) {
                reportError("no matching photo", topic, n);
                return null;
            }

            // go fetch the image itself
            bufferedImage = ImageIO.read(new URL(photoURL).openStream());

        } catch (IOException ex) {
            reportError(ex.getMessage(), topic, n);
            return null;
        } catch (SAXException ex) {
            reportError(ex.getMessage(), topic, n);
            return null;
        }

        // create a photo object with the image data
        // and the search string as a description
        Photo photo = new Photo();
        photo.setImage(bufferedImage);
        photo.setTopic(topic);
        return photo;
    }
}

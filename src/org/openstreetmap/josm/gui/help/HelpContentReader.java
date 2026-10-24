// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.gui.help;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.openstreetmap.josm.tools.HttpClient;
import org.openstreetmap.josm.tools.WikiReader;

/**
 * Reads help content from the JOSM Wiki and prepares it for rendering in the internal
 * help browser.
 *
 * The help content has to be <strong>filtered</strong> because only the main content <code>&lt;div&gt;</code>
 * of a Wiki help page is displayed in the internal help browser.
 *
 * It also has to be <strong>transformed</strong> because the internal help browser required slightly
 * different HTML than what is provided by the Wiki.
 */
public class HelpContentReader extends WikiReader {

    /**
     * Constructs a new {@code HelpContentReader}.
     *
     * @param baseUrl the base url of the JOSM help wiki, i.e. https://josm.openstreetmap.org
     */
    public HelpContentReader(String baseUrl) {
        super(baseUrl);
    }

    /**
     * Fetches the content of a help topic from the JOSM wiki.
     *
     * @param helpTopicUrl  the absolute help topic URL
     * @param dotest if {@code true}, checks if help content is empty
     * @return the content, filtered and transformed for being displayed in the internal help browser
     * @throws HelpContentReaderException if problem occurs
     */
    public String fetchHelpTopicContent(String helpTopicUrl, boolean dotest) throws HelpContentReaderException {
        if (helpTopicUrl == null || helpTopicUrl.isBlank()) {
        	throw new HelpContentReaderException(new IllegalArgumentException("helpTopicUrl is null or blank"), 0);
		}
        HttpClient.Response con = null;
        try {
            final URL u = new URI(helpTopicUrl).toURL();
            con = HttpClient.create(u).connect();
            
            try (BufferedReader in = con.getContentReader()) {
                return prepareHelpContent(in, dotest, u);
            }
        } catch (MalformedURLException | URISyntaxException e) {
            throw new HelpContentReaderException(e, 0);
        } catch (IOException e) {
        	int responseCode = 0;
            if (con != null) {
                try {
                    responseCode = con.getResponseCode();
                } catch (Exception ignored) {
                	// do nothing
                }
            }
            throw new HelpContentReaderException(e, responseCode);
        } finally {
        	if (con instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) con).close();
                } catch (Exception ignored) {
                    // do nothing
                }
        	}
        }
    }

    /**
     * Reads help content from the input stream and prepares it to be rendered later
     * in the internal help browser.
     *
     * Throws a {@link MissingHelpContentException} if the content read from the stream
     * most likely represents a stub help page.
     *
     * @param in the input stream
     * @param dotest if {@code true}, checks if help content is empty
     * @param url help topic URL
     * @return the content
     * @throws HelpContentReaderException if an exception occurs
     * @throws MissingHelpContentException if the content read isn't a help page
     * @since 5936
     */
    protected String prepareHelpContent(BufferedReader in, boolean dotest, URL url) throws HelpContentReaderException {
        String s = "";
        try {
            s = readFromTrac(in, url);
        } catch (IOException e) {
            throw new HelpContentReaderException(e, 0);
        }
        if (dotest && s.isEmpty()) {
			throw new MissingHelpContentException(s);
		}
        return s;
    }
}

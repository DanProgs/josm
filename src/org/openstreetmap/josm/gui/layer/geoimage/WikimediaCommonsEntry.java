// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.gui.layer.geoimage;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.tools.Mediawiki;

/**
 * A geocoded image from <a href="https://commons.wikimedia.org/">Wikimedia Commons</a>
 */
class WikimediaCommonsEntry extends ImageEntry {
	private static final String COMMONS_BASE_URL = "https://upload.wikimedia.org/wikipedia/commons";
    private final String title;

    WikimediaCommonsEntry(String title, LatLon latLon) {
    	Objects.requireNonNull(title, "Title must not be null");
    	this.title = title.trim()
    			.replaceFirst("^(?i)File:", "")
    			.replace("\\s+", "_");
        
    	if (latLon != null) {
            setPos(latLon);
        }
    }

    @Override
    protected URL getImageUrl() throws MalformedURLException {
    	try {
            return getImageURI().toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new MalformedURLException("Invalid Commons URL for '" + title + "': " + e.getMessage());
        }
    }

    @Override
    public URI getImageURI() {
        return URI.create(Mediawiki.getImageUrl(COMMONS_BASE_URL, title));
    }

    @Override
    public String getDisplayName() {
        return "File:" + title;
    }

    @Override
    public String toString() {
    	return getDisplayName();
    }

    @Override
    public int hashCode() {
    	return 31 * super.hashCode() + Objects.hash(title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
			return true;
		}
        
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        if (!super.equals(obj)) {
            return false;
        }
        
        final WikimediaCommonsEntry other = (WikimediaCommonsEntry) obj;
        return Objects.equals(title, other.title);
    }
}

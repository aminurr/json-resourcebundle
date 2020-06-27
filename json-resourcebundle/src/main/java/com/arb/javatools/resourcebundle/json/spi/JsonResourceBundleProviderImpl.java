package com.arb.javatools.resourcebundle.json.spi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import com.arb.javatools.resourcebundle.ext.spi.JsonResourceBundleProvider;
import com.arb.javatools.resourcebundle.json.JsonResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for {@code}JsonResourceBundleProvider that takes a ARB structured
 * bundle name, parses it and loads the bundle.
 *
 * @author Aminur Rashid
 */
public class JsonResourceBundleProviderImpl implements JsonResourceBundleProvider {

    @Override
    public ResourceBundle loadBundle(String resourceName) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            return null;
        }
        try {
            return new JsonResourceBundle(is);
        } catch (IOException ex) {
            Logger.getLogger(JsonResourceBundleProviderImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}

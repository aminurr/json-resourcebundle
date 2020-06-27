package com.arb.javatools.resourcebundle.ext;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ServiceLoader;
import com.arb.javatools.resourcebundle.ext.spi.JsonResourceBundleProvider;
/**
 * JsonResourceBundleControl extension. To avoid dependency on third party jar,
 * the class will find service {@code}JsonResourceBundleProvider.class to return a parsed 
 * JsonBundle
 * @author Aminur Rashid
 */
public class JsonResourceBundleControl extends ResourceBundle.Control {
    @Override
    public List<String> getFormats(String baseName) {
        return Arrays.asList("json");
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) {
          String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, format);
        Iterator<JsonResourceBundleProvider> s = ServiceLoader.load(JsonResourceBundleProvider.class, loader).iterator();
        if (s.hasNext()) {
            return s.next().loadBundle(resourceName);
        }
     return null;
    }

}

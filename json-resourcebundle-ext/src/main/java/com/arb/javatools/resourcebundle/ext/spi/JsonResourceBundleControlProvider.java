package com.arb.javatools.resourcebundle.ext.spi;

import com.arb.javatools.resourcebundle.ext.JsonResourceBundleControl;

import java.util.ResourceBundle;
import java.util.spi.ResourceBundleControlProvider;

/**
 * Service implementing {@code}java.util.spi.ResourceBundleControlProvider to
 * return {@code}JsonResourceBundleControl A control
 *
 * @author Aminur Rashid
 */
public class JsonResourceBundleControlProvider implements ResourceBundleControlProvider {

    private final static JsonResourceBundleControl CONTROL = new JsonResourceBundleControl();

    @Override
    public ResourceBundle.Control getControl(String baseName) {
        //Override if you want this control to restrict for specific folder or pattern
        //of files e.g baseName will be qualified package.
        /*if(baseName.endsWith(".json")){
            return control;
        }*/
        return CONTROL;
    }
}

package com.arb.javatools.resourcebundle.ext.spi;

import java.util.ResourceBundle;

/**
 * A service that delegates parsing of a resource identified by resourceName, to the implementor so that
 * implementor is free to use any third party API to parse json.
 * @author Aminur Rashid
 */
public interface JsonResourceBundleProvider {
    ResourceBundle loadBundle(String resourceName);
}

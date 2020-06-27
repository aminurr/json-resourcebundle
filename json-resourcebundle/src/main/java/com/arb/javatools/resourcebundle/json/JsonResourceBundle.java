package com.arb.javatools.resourcebundle.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
/**
 * A basic quick implementation that reads an ARB message bundle, and the strings in IC format.
 * @author Aminur Rashid
 */
public class JsonResourceBundle extends ResourceBundle {

    private final KeyEnumeration keyEnumeration;
    private final Map<String, Object> resourceBundleMap;

    public JsonResourceBundle(InputStream is) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            resourceBundleMap = mapper.readValue(is, Map.class);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }

        keyEnumeration = new KeyEnumeration(resourceBundleMap.keySet());

    }

    @Override
    protected Object handleGetObject(String key) {
        return resourceBundleMap.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return keyEnumeration;
    }

    private static class KeyEnumeration implements Enumeration<String> {

        private final List<String> keyList;
        private int i = 0;

        KeyEnumeration(Set<String> keySet) {
            keyList = new ArrayList<String>();

            for (String key : keySet) {
                if (!key.startsWith("@")) {
                    keyList.add(key);
                }
            }
        }

        public boolean hasMoreElements() {
            return i < keyList.size();
        }

        public String nextElement() {
            return keyList.get(i++);
        }
    }
}
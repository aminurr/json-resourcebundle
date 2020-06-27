package com.arb.javatools.resourcebundle.json;

import com.ibm.icu.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Aminur Rashid
 */
public class TestBundle {

    @Test
    public void testBundle() throws Exception {
        System.out.println("java.ext.dirs::" + System.getProperty("java.ext.dirs"));
        ResourceBundle bundle = ResourceBundle.getBundle("com.arb.javatools.resourcebundle.json.sampleResourceBundle");
        assertTrue(bundle.containsKey("key1"));
        assertTrue(bundle.containsKey("dotty.key.allowed"));
        assertTrue(bundle.containsKey("plural1"));
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("gender_of_host", "male");
        arguments.put("num_guests", Integer.valueOf(3));
        String pattern = bundle.getString("plural1");
        arguments.put("host", "Ken");
        arguments.put("guest", "Mary");
        String message = MessageFormat.format(pattern, arguments);
        assertEquals("Ken invites Mary and 2 other people to his party.", message);
        //debugInfo(message,bundle);
    }

    private static void debugInfo(String message, ResourceBundle bundle) {
        System.out.println("\t" + message);
        for (String key : bundle.keySet()) {
            System.out.println("\t" + key);
            System.out.println("\t\t" + bundle.getString(key));
        }

        System.out.println();
        System.out.println("Formatted string....");

        System.out.println("\t" + message);
    }

    public static void main(String args[]) throws Exception {
        new TestBundle().testBundle();
    }
}

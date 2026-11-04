/*
 * @test
 * @summary Test for System.setProperties(Properties)
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.Properties;

public class SystemWitupTest {

    public static void main(String[] args) {
        testSetPropertiesWithNullProps();
        testSetPropertiesWithValidProps();
    }

    private static void testSetPropertiesWithNullProps() {
        try {
            System.setProperties(null);
            Properties props = System.getProperties();
            if (props == null) {
                System.err.println("Test failed: Properties is null after setting null.");
            } else {
                System.out.println("testSetPropertiesWithNullProps passed.");
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }

    private static void testSetPropertiesWithValidProps() {
        try {
            Properties newProps = new Properties();
            newProps.setProperty("testKey", "testValue");
            System.setProperties(newProps);
            Properties currentProps = System.getProperties();
            if ("testValue".equals(currentProps.getProperty("testKey"))) {
                System.out.println("testSetPropertiesWithValidProps passed.");
            } else {
                System.err.println("Test failed: Property not set correctly.");
            }
        } catch (Exception e) {
            System.err.println("Test failed with exception: " + e);
        }
    }
}

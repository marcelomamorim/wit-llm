/*
 * @test
 * @summary Test System.setProperties(Properties)
 * @run main SystemWitupTest
 */
import java.lang.System;
import java.util.Properties;

public class SystemWitupTest {
    public static void main(String[] args) {
        testSetPropertiesWithNull();
        testSetPropertiesWithValidProps();
    }

    static void testSetPropertiesWithNull() {
        Properties originalProps = System.getProperties();
        try {
            System.setProperties(null);
            Properties newProps = System.getProperties();
            if (newProps == null) {
                throw new AssertionError("Properties should not be null after setting null");
            }
            if (newProps.size() != 0) {
                throw new AssertionError("Properties should be empty after setting null");
            }
        } finally {
            System.setProperties(originalProps);
        }
    }

    static void testSetPropertiesWithValidProps() {
        Properties testProps = new Properties();
        testProps.setProperty("testKey", "testValue");
        Properties originalProps = System.getProperties();
        try {
            System.setProperties(testProps);
            Properties currentProps = System.getProperties();
            if (currentProps == null) {
                throw new AssertionError("Properties should not be null after setting valid props");
            }
            if (!"testValue".equals(currentProps.getProperty("testKey"))) {
                throw new AssertionError("Property 'testKey' does not have expected value 'testValue'");
            }
        } finally {
            System.setProperties(originalProps);
        }
    }
}

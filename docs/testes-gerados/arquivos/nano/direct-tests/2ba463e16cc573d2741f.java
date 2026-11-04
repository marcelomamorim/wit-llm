/*
 * @test
 * @summary Test System.getLogger with ResourceBundle parameter
 * @run main SystemWitupTest
 */
import java.lang.System;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SystemWitupTest {
    public static void main(String[] args) {
        testGetLoggerWithValidInputs();
        testGetLoggerWithNullName();
        testGetLoggerWithNullResourceBundle();
    }

    static void testGetLoggerWithValidInputs() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.logging");
        Logger logger = System.getLogger("TestLogger", rb);
        assert logger != null : "Logger should not be null with valid inputs";
        System.out.println("testGetLoggerWithValidInputs passed.");
    }

    static void testGetLoggerWithNullName() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("java.util.logging");
            System.getLogger(null, rb);
            assert false : "Expected NullPointerException for null name";
        } catch (NullPointerException e) {
            System.out.println("testGetLoggerWithNullName passed.");
        }
    }

    static void testGetLoggerWithNullResourceBundle() {
        try {
            System.getLogger("TestLogger", null);
            assert false : "Expected NullPointerException for null ResourceBundle";
        } catch (NullPointerException e) {
            System.out.println("testGetLoggerWithNullResourceBundle passed.");
        }
    }
}

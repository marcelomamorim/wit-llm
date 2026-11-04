/*
 * @test
 * @summary Tests for jdk.internal.logger.DefaultLoggerFinder.getLogger
 * @run main DefaultLoggerFinderWitupTest
 */

import jdk.internal.logger.DefaultLoggerFinder;
import java.lang.System.Logger;
import java.lang.Module;

public class DefaultLoggerFinderWitupTest {

    public static void main(String[] args) {
        testGetLoggerWithNullName();
        testGetLoggerWithNullModule();
        testGetLoggerWithValidInputs();
    }

    private static void testGetLoggerWithNullName() {
        try {
            DefaultLoggerFinder finder = new DefaultLoggerFinder();
            finder.getLogger(null, ModuleLayer.boot());
            System.out.println("FAIL: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null name");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null name: " + t);
        }
    }

    private static void testGetLoggerWithNullModule() {
        try {
            DefaultLoggerFinder finder = new DefaultLoggerFinder();
            finder.getLogger("TestLogger", null);
            System.out.println("FAIL: Expected NullPointerException for null module");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null module");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null module: " + t);
        }
    }

    private static void testGetLoggerWithValidInputs() {
        try {
            DefaultLoggerFinder finder = new DefaultLoggerFinder();
            Logger logger = finder.getLogger("ValidLogger", ModuleLayer.boot());
            if (logger != null) {
                System.out.println("PASS: getLogger returned a Logger instance");
            } else {
                System.out.println("FAIL: getLogger returned null");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid inputs: " + t);
        }
    }
}

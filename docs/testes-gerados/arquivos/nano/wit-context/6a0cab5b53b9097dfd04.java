/*
 * @test
 * @summary Test for ClassLoader.getSystemResource(String)
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;
import java.lang.ClassLoader;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testNullInput();
        testValidInput();
    }

    private static void testNullInput() {
        try {
            URL result = ClassLoader.getSystemResource(null);
            // Expecting a NullPointerException, so if result is returned, fail
            System.err.println("Expected NullPointerException for null input, but got: " + result);
        } catch (NullPointerException e) {
            System.out.println("Passed null input test: NullPointerException thrown as expected.");
        } catch (Exception e) {
            System.err.println("Failed null input test: Unexpected exception: " + e);
        }
    }

    private static void testValidInput() {
        try {
            URL url = ClassLoader.getSystemResource("java/lang/Object.class");
            if (url != null) {
                System.out.println("Valid input test passed: URL obtained: " + url);
            } else {
                System.err.println("Valid input test failed: URL is null.");
            }
        } catch (Exception e) {
            System.err.println("Valid input test failed: Exception: " + e);
        }
    }
}

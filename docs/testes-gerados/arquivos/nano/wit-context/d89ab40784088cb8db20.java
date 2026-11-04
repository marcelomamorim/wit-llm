/*
 * @test
 * @summary Test for java.lang.ClassLoader.getSystemResourceAsStream(String)
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.lang.ClassLoader;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testNullInput();
        testExistingResource();
        testNonExistingResource();
    }

    private static void testNullInput() {
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(null);
            if (is != null) {
                is.close();
            }
            System.out.println("testNullInput: Passed");
        } catch (NullPointerException e) {
            System.out.println("testNullInput: Caught NullPointerException as expected");
        } catch (IOException e) {
            System.out.println("testNullInput: IOException occurred");
        }
    }

    private static void testExistingResource() {
        String resourceName = "java/lang/Object.class";
        InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
        if (is != null) {
            try {
                int data = is.read();
                if (data != -1) {
                    System.out.println("testExistingResource: Passed");
                } else {
                    System.out.println("testExistingResource: Empty stream");
                }
                is.close();
            } catch (IOException e) {
                System.out.println("testExistingResource: IOException occurred");
            }
        } else {
            System.out.println("testExistingResource: Resource not found");
        }
    }

    private static void testNonExistingResource() {
        String resourceName = "non/existent/resource.txt";
        InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
        if (is == null) {
            System.out.println("testNonExistingResource: Passed");
        } else {
            try {
                is.close();
            } catch (IOException e) {
                // ignore
            }
            System.out.println("testNonExistingResource: Resource unexpectedly found");
        }
    }
}

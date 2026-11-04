/*
 * @test
 * @summary Test getResourceAsStream method of URLClassLoader for various resource names.
 * @run main URLClassLoaderWitupTest
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class URLClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetResourceAsStreamExistingResource();
        testGetResourceAsStreamNonExistingResource();
        testGetResourceAsStreamNullName();
    }

    static void testGetResourceAsStreamExistingResource() {
        try {
            URL url = new URL("file", null, System.getProperty("java.home") + "/lib/rt.jar");
            URL[] urls = { url };
            URLClassLoader loader = new URLClassLoader(urls);
            String resourceName = "java/lang/Object.class";
            InputStream is = loader.getResourceAsStream(resourceName);
            if (is != null) {
                try {
                    int available = is.available();
                    if (available > 0) {
                        System.out.println("PASS: Existing resource found and stream is available.");
                    } else {
                        System.out.println("FAIL: Resource stream is empty.");
                    }
                } catch (IOException e) {
                    System.out.println("FAIL: IOException while checking resource stream.");
                } finally {
                    is.close();
                }
            } else {
                System.out.println("FAIL: Existing resource not found.");
            }
        } catch (MalformedURLException e) {
            System.out.println("FAIL: Malformed URL.");
        } catch (IOException e) {
            System.out.println("FAIL: IOException during test.");
        }
    }

    static void testGetResourceAsStreamNonExistingResource() {
        try {
            URL url = new URL("file", null, System.getProperty("java.home") + "/lib/rt.jar");
            URL[] urls = { url };
            URLClassLoader loader = new URLClassLoader(urls);
            String resourceName = "non/existent/resource.txt";
            InputStream is = loader.getResourceAsStream(resourceName);
            if (is == null) {
                System.out.println("PASS: Non-existent resource correctly returns null.");
            } else {
                System.out.println("FAIL: Non-existent resource returned a stream.");
                is.close();
            }
        } catch (MalformedURLException e) {
            System.out.println("FAIL: Malformed URL.");
        } catch (IOException e) {
            System.out.println("FAIL: IOException during test.");
        }
    }

    static void testGetResourceAsStreamNullName() {
        try {
            URL url = new URL("file", null, System.getProperty("java.home") + "/lib/rt.jar");
            URL[] urls = { url };
            URLClassLoader loader = new URLClassLoader(urls);
            try {
                loader.getResourceAsStream(null);
                System.out.println("FAIL: Null resource name did not throw.");
            } catch (NullPointerException e) {
                System.out.println("PASS: Null resource name threw NullPointerException.");
            }
        } catch (MalformedURLException e) {
            System.out.println("FAIL: Malformed URL.");
        }
    }
}

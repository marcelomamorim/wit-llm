/*
 * @test
 * @summary Verify ClassLoader.getResources rejects a null name and returns no elements for an absent resource
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;
import java.util.Enumeration;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        boolean thrown = false;
        try {
            cl.getResources(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null name");
        }

        Enumeration<URL> urls = cl.getResources("java/lang/witup/NoSuchResource.txt");
        if (urls.hasMoreElements()) {
            throw new RuntimeException("No resources expected for absent name");
        }
    }
}

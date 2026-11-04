/*
 * @test
 * @summary Smoke test for URLClassPath construction with the public two-argument constructor
 * @run main URLClassPathWitupTest
 */

import java.net.URL;
import jdk.internal.loader.URLClassPath;

public class URLClassPathWitupTest {
    public static void main(String[] args) throws Exception {
        URLClassPath cp = new URLClassPath(new URL[0], null);
        if (cp == null) {
            throw new RuntimeException("URLClassPath construction returned null");
        }
    }
}

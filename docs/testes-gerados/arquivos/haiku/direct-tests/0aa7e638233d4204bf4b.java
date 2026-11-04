/*
 * @test
 * @summary Verifies that public URLClassLoader constructors, which reach the package-private constructor, load resources from a directory URL and honor the parent loader.
 * @run main URLClassLoaderWitupTest
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;

public class URLClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        File dir = File.createTempFile("witup", "dir");
        dir.delete();
        dir.mkdir();
        File res = new File(dir, "res.txt");
        try (FileOutputStream out = new FileOutputStream(res)) {
            out.write("hello".getBytes(StandardCharsets.UTF_8));
        }
        URL[] urls = { dir.toURI().toURL() };

        try (URLClassLoader withParent = new URLClassLoader(urls, ClassLoader.getSystemClassLoader())) {
            check("hello".equals(read(withParent, "res.txt")), "resource via system parent");
            check(withParent.getResource("missing-witup.txt") == null, "missing resource with parent");
        }

        try (URLClassLoader noParent = new URLClassLoader(urls, null)) {
            check("hello".equals(read(noParent, "res.txt")), "resource with null parent");
            check(noParent.getResource("missing-witup.txt") == null, "missing resource with null parent");
        }

        try (URLClassLoader empty = new URLClassLoader(new URL[0])) {
            check(empty.getResource("res.txt") == null, "empty search path finds nothing");
        }

        res.delete();
        dir.delete();
    }

    static String read(ClassLoader cl, String name) throws IOException {
        try (InputStream in = cl.getResourceAsStream(name)) {
            if (in == null) {
                throw new RuntimeException("resource not found: " + name);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Failed: " + what);
        }
    }
}

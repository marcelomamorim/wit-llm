/*
 * @test
 * @summary URLClassLoader.getResourceAsStream: null name, missing resource and existing directory resource
 * @run main URLClassLoaderWitupTest
 */

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class URLClassLoaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testMissingResourceReturnsNull();
        testExistingResourceFromDirectory();
    }

    static void testNullNameThrowsNPE() {
        try (URLClassLoader cl = new URLClassLoader(new URL[0], null)) {
            cl.getResourceAsStream(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testMissingResourceReturnsNull() throws Exception {
        try (URLClassLoader cl = new URLClassLoader(new URL[0], null)) {
            InputStream is = cl.getResourceAsStream("witup/does/not/Exist.txt");
            if (is != null) {
                is.close();
                throw new RuntimeException("Expected null for missing resource");
            }
        }
    }

    static void testExistingResourceFromDirectory() throws Exception {
        Path dir = Files.createTempDirectory("witup");
        Path file = dir.resolve("res.txt");
        Files.write(file, "hello".getBytes(StandardCharsets.UTF_8));
        try {
            URL[] urls = { dir.toUri().toURL() };
            try (URLClassLoader cl = new URLClassLoader(urls, null);
                 InputStream is = cl.getResourceAsStream("res.txt")) {
                if (is == null) {
                    throw new RuntimeException("Expected stream for existing resource");
                }
                byte[] data = is.readAllBytes();
                String content = new String(data, StandardCharsets.UTF_8);
                if (!"hello".equals(content)) {
                    throw new RuntimeException("Unexpected content: " + content);
                }
            }
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(dir);
        }
    }
}

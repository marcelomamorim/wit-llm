/*
 * @test
 * @summary Verifies URLClassLoader.getResourceAsStream null-name, missing-resource and existing-resource behavior
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
        testExistingResourceIsReadable();
    }

    static void testNullNameThrowsNPE() {
        URLClassLoader cl = new URLClassLoader(new URL[0], null);
        try {
            cl.getResourceAsStream(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            try {
                cl.close();
            } catch (Exception ignored) {
            }
        }
    }

    static void testMissingResourceReturnsNull() throws Exception {
        URLClassLoader cl = new URLClassLoader(new URL[0], null);
        try {
            InputStream in = cl.getResourceAsStream("witup-no-such-resource-12345.txt");
            if (in != null) {
                in.close();
                throw new AssertionError("Expected null for missing resource");
            }
        } finally {
            cl.close();
        }
    }

    static void testExistingResourceIsReadable() throws Exception {
        Path dir = Files.createTempDirectory("witup-urlcl");
        Path file = dir.resolve("data.txt");
        try {
            Files.write(file, "hello".getBytes(StandardCharsets.UTF_8));
            String dirUri = dir.toUri().toString();
            if (!dirUri.endsWith("/")) {
                dirUri = dirUri + "/";
            }
            URL[] urls = { new URL(dirUri) };
            URLClassLoader cl = new URLClassLoader(urls, null);
            try {
                InputStream in = cl.getResourceAsStream("data.txt");
                if (in == null) {
                    throw new AssertionError("Expected stream for existing resource");
                }
                try {
                    byte[] bytes = in.readAllBytes();
                    String content = new String(bytes, StandardCharsets.UTF_8);
                    if (!"hello".equals(content)) {
                        throw new AssertionError("Unexpected content: " + content);
                    }
                } finally {
                    in.close();
                }
            } finally {
                cl.close();
            }
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(dir);
        }
    }
}

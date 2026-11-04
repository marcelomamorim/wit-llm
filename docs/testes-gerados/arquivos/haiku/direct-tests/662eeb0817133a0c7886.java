/*
 * @test
 * @summary Behavioral tests for ClassLoader.getResourceAsStream using public API only
 * @run main ClassLoaderWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ClassLoaderWitupTest {

    static class StubLoader extends ClassLoader {
        private final URL url;
        String lastName;

        StubLoader(URL url) {
            super();
            this.url = url;
        }

        @Override
        public URL getResource(String name) {
            lastName = name;
            return url;
        }
    }

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testNullResourceReturnsNull();
        testMissingFileReturnsNull();
        testExistingFileReturnsContent();
    }

    static void testNullNameThrowsNPE() {
        StubLoader loader = new StubLoader(null);
        try {
            loader.getResourceAsStream(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullResourceReturnsNull() throws Exception {
        StubLoader loader = new StubLoader(null);
        InputStream in = loader.getResourceAsStream("a/b.txt");
        if (in != null) {
            throw new RuntimeException("Expected null stream when getResource returns null");
        }
        if (!"a/b.txt".equals(loader.lastName)) {
            throw new RuntimeException("getResource was not called with the same name");
        }
    }

    static void testMissingFileReturnsNull() throws Exception {
        URL missing = new URL("file:/witup/nonexistent/dir/xyz-missing-resource.txt");
        StubLoader loader = new StubLoader(missing);
        InputStream in = loader.getResourceAsStream("missing");
        if (in != null) {
            throw new RuntimeException("Expected null stream when openStream fails with IOException");
        }
    }

    static void testExistingFileReturnsContent() throws Exception {
        File f = File.createTempFile("witup", ".txt");
        try {
            byte[] expected = "hello-witup".getBytes("UTF-8");
            FileOutputStream fos = new FileOutputStream(f);
            try {
                fos.write(expected);
            } finally {
                fos.close();
            }

            StubLoader loader = new StubLoader(f.toURI().toURL());
            InputStream in = loader.getResourceAsStream("x");
            if (in == null) {
                throw new RuntimeException("Expected non-null stream for existing file");
            }
            byte[] actual;
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[64];
                int n;
                while ((n = in.read(buf)) != -1) {
                    bos.write(buf, 0, n);
                }
                actual = bos.toByteArray();
            } finally {
                in.close();
            }
            if (actual.length != expected.length) {
                throw new RuntimeException("Content length mismatch");
            }
            for (int i = 0; i < expected.length; i++) {
                if (actual[i] != expected[i]) {
                    throw new RuntimeException("Content mismatch at index " + i);
                }
            }
        } finally {
            f.delete();
        }
    }
}

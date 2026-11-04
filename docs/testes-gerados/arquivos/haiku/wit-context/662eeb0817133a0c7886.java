/*
 * @test
 * @summary Checks ClassLoader.getResourceAsStream null-name, missing-resource and unopenable-URL behavior
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassLoaderWitupTest {

    static class NullResourceLoader extends ClassLoader {
        @Override
        public URL getResource(String name) {
            return null;
        }
    }

    static class MissingFileLoader extends ClassLoader {
        @Override
        public URL getResource(String name) {
            try {
                return new URL("file:/nonexistent_witup_dir/" + name);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        try {
            new NullResourceLoader().getResourceAsStream(null);
            fail("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
        }

        InputStream in = new NullResourceLoader().getResourceAsStream("a.txt");
        if (in != null) {
            fail("expected null when getResource returns null");
        }

        InputStream missing = new MissingFileLoader().getResourceAsStream("x.txt");
        if (missing != null) {
            fail("expected null when URL cannot be opened");
        }
    }
}

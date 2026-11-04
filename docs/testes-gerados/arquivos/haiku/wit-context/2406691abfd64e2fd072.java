/*
 * @test
 * @summary WITUP generated test for ClassLoader.getResource(String): null name must throw NullPointerException and a missing resource must return null
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;

public class ClassLoaderWitupTest {

    static class TestLoader extends ClassLoader {
        TestLoader() {
            super();
        }

        TestLoader(ClassLoader parent) {
            super(parent);
        }
    }

    static final String MISSING = "witup/generated/no/such/resource/missing-entry.txt";

    static void checkNullNameThrowsNpe(ClassLoader loader) {
        boolean thrown = false;
        try {
            loader.getResource(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null name");
        }
    }

    static void checkMissingReturnsNull(ClassLoader loader) {
        URL url = loader.getResource(MISSING);
        if (url != null) {
            throw new RuntimeException("expected null for missing resource but got " + url);
        }
    }

    public static void main(String[] args) throws Exception {
        TestLoader withDefaultParent = new TestLoader();
        checkNullNameThrowsNpe(withDefaultParent);
        checkMissingReturnsNull(withDefaultParent);

        TestLoader withNullParent = new TestLoader(null);
        checkNullNameThrowsNpe(withNullParent);
        checkMissingReturnsNull(withNullParent);
    }
}

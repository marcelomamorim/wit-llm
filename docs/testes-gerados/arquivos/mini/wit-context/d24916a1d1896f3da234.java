/*
 * @test
 * @summary Test constructor of jdk.internal.jimage.ImageLocation for null argument checks
 * @run main ImageLocationWitupTest
 */

import java.util.Objects;
import jdk.internal.jimage.ImageLocation;
import jdk.internal.jimage.ImageStrings;

public class ImageLocationWitupTest {
    public static void main(String[] args) {
        testConstructorRejectsNullAttributes();
        testConstructorRejectsNullStrings();
        testConstructorAcceptsValidArgs();
        System.out.println("All tests passed.");
    }

    private static void testConstructorRejectsNullAttributes() {
        ImageStrings dummyStrings = new DummyImageStrings();
        try {
            new ImageLocation(null, dummyStrings);
            throw new RuntimeException("Expected NullPointerException for null attributes");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorRejectsNullStrings() {
        long[] dummyAttributes = new long[ImageLocation.ATTRIBUTE_COUNT];
        try {
            new ImageLocation(dummyAttributes, null);
            throw new RuntimeException("Expected NullPointerException for null strings");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorAcceptsValidArgs() {
        long[] dummyAttributes = new long[ImageLocation.ATTRIBUTE_COUNT];
        ImageStrings dummyStrings = new DummyImageStrings();
        ImageLocation loc = new ImageLocation(dummyAttributes, dummyStrings);
        if (loc == null) {
            throw new RuntimeException("Constructor returned null instance");
        }
    }

    // Minimal stub implementation of ImageStrings to allow construction
    private static class DummyImageStrings extends ImageStrings {
        DummyImageStrings() {
            super();
        }
    }
}

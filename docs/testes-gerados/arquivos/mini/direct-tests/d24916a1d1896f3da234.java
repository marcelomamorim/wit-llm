/*
 * @test
 * @summary Test constructor of ImageLocation for null and valid inputs
 * @run main ImageLocationWitupTest
 */

import java.util.Objects;

public class ImageLocationWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidArgs();
        testConstructorWithNullAttributes();
        testConstructorWithNullStrings();
    }

    private static void testConstructorWithValidArgs() {
        long[] attributes = new long[jdk.internal.jimage.ImageLocation.ATTRIBUTE_COUNT];
        DummyImageStrings strings = new DummyImageStrings();
        jdk.internal.jimage.ImageLocation loc = new jdk.internal.jimage.ImageLocation(attributes, strings);
        if (loc == null) {
            throw new AssertionError("Constructor returned null");
        }
    }

    private static void testConstructorWithNullAttributes() {
        DummyImageStrings strings = new DummyImageStrings();
        try {
            new jdk.internal.jimage.ImageLocation(null, strings);
            throw new AssertionError("Expected NullPointerException for null attributes");
        } catch (NullPointerException expected) {
        }
    }

    private static void testConstructorWithNullStrings() {
        long[] attributes = new long[jdk.internal.jimage.ImageLocation.ATTRIBUTE_COUNT];
        try {
            new jdk.internal.jimage.ImageLocation(attributes, null);
            throw new AssertionError("Expected NullPointerException for null strings");
        } catch (NullPointerException expected) {
        }
    }

    // Minimal stub for ImageStrings to allow compilation
    private static class DummyImageStrings extends jdk.internal.jimage.ImageStrings {
        DummyImageStrings() {
            super();
        }
    }
}

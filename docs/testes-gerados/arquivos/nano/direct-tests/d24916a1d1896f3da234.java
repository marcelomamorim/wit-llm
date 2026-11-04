/*
 * @test
 * @summary Test constructor of ImageLocation with valid parameters
 * @run main ImageLocationWitupTest
 */

import jdk.internal.jimage.ImageLocation;
import java.util.Objects;

public class ImageLocationWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidArguments();
        testConstructorWithNullAttributes();
        testConstructorWithNullStrings();
    }

    static void testConstructorWithValidArguments() {
        long[] attrs = {0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L};
        ImageStrings strings = new ImageStrings();
        ImageLocation loc = new ImageLocation(attrs, strings);
        assert loc.getStrings() == strings : "Strings object should be the same as passed";
    }

    static void testConstructorWithNullAttributes() {
        try {
            new ImageLocation(null, new ImageStrings());
            assert false : "Expected NullPointerException for null attributes";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullStrings() {
        try {
            new ImageLocation(new long[8], null);
            assert false : "Expected NullPointerException for null strings";
        } catch (NullPointerException e) {
            // expected
        }
    }
}

class ImageStrings {
    // Dummy class for testing purposes
}

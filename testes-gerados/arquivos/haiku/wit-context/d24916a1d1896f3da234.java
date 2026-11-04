/*
 * @test
 * @summary Verify that the ImageLocation constructor rejects null attributes and null strings with NullPointerException
 * @modules java.base/jdk.internal.jimage
 * @run main ImageLocationWitupTest
 */
import jdk.internal.jimage.ImageLocation;

public class ImageLocationWitupTest {

    public static void main(String[] args) {
        testNullAttributesThrowsNPE();
        testNullStringsThrowsNPE();
        testNullAttributesAndNullStringsThrowsNPE();
    }

    static void testNullAttributesThrowsNPE() {
        expectNPE(() -> new ImageLocation(null, null), "null attributes with null strings");
        final long[] nullAttrs = null;
        final Object dummy = null;
        expectNPE(() -> new ImageLocation(nullAttrs, null), "null attributes");
    }

    static void testNullStringsThrowsNPE() {
        final long[] attrs = new long[ImageLocation.ATTRIBUTE_COUNT];
        expectNPE(() -> new ImageLocation(attrs, null), "null strings with valid attributes");
    }

    static void testNullAttributesAndNullStringsThrowsNPE() {
        final long[] attrs = new long[ImageLocation.ATTRIBUTE_COUNT];
        final long[] nullAttrs = null;
        expectNPE(() -> new ImageLocation(nullAttrs, null), "both null");
        expectNPE(() -> new ImageLocation(nullAttrs, null), "both null repeated");
    }

    static void expectNPE(Runnable r, String description) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for: " + description);
    }
}

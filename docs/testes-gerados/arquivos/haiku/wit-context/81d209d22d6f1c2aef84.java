/*
 * @test
 * @summary WITUP-generated checks for NameConstraintsExtension.delete(String)
 * @modules java.base/sun.security.x509
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.IOException;

public class NameConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testUnrecognizedNameThrowsIOException();
        testUnrecognizedNameIsRejectedRegardlessOfCase();
        testKnownNamesAreAcceptedCaseInsensitively();
    }

    static NameConstraintsExtension newExtension() throws IOException {
        return new NameConstraintsExtension(null, null);
    }

    static void testUnrecognizedNameThrowsIOException() throws Exception {
        NameConstraintsExtension ext = newExtension();
        try {
            ext.delete("unknown_subtrees");
            throw new AssertionError("Expected IOException for unrecognized name");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testUnrecognizedNameIsRejectedRegardlessOfCase() throws Exception {
        NameConstraintsExtension ext = newExtension();
        try {
            ext.delete("UNKNOWN_SUBTREES");
            throw new AssertionError("Expected IOException for unrecognized name in upper case");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testKnownNamesAreAcceptedCaseInsensitively() throws Exception {
        NameConstraintsExtension ext = newExtension();
        ext.delete(NameConstraintsExtension.PERMITTED_SUBTREES.toUpperCase());
        ext.delete(NameConstraintsExtension.EXCLUDED_SUBTREES.toUpperCase());
        ext.delete(NameConstraintsExtension.PERMITTED_SUBTREES);
        ext.delete(NameConstraintsExtension.EXCLUDED_SUBTREES);
    }
}

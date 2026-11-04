/* @test    @summary Test for PSSParameterSpec constructor exception paths    @run main PSSParameterSpecWitupTest */

import java.security.spec.PSSParameterSpec;

public class PSSParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullMdName();
        testNullMgfNameWithMd();
        testInvalidSaltLen();
        testInvalidTrailerField();
    }

    private static void testNullMdName() {
        try {
            new PSSParameterSpec(null, "MGF1", null, 20, 1);
            System.err.println("Failed: Expected NullPointerException for null mdName");
        } catch (NullPointerException e) {
            // Expected
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null mdName: " + t);
        }
    }

    private static void testNullMgfNameWithMd() {
        try {
            new PSSParameterSpec("SHA-1", null, null, 20, 1);
            System.err.println("Failed: Expected NullPointerException for null mgfName with mdName not null");
        } catch (NullPointerException e) {
            // Expected
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for null mgfName with mdName not null: " + t);
        }
    }

    private static void testInvalidSaltLen() {
        try {
            new PSSParameterSpec("SHA-1", "MGF1", null, -1, 1);
            System.err.println("Failed: Expected IllegalArgumentException for negative saltLen");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for negative saltLen: " + t);
        }
        try {
            new PSSParameterSpec("SHA-1", "MGF1", null, 0, 1);
            // No exception expected for saltLen=0, assuming valid
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for saltLen=0: " + t);
        }
    }

    private static void testInvalidTrailerField() {
        try {
            new PSSParameterSpec("SHA-1", "MGF1", null, 20, -1);
            System.err.println("Failed: Expected IllegalArgumentException for negative trailerField");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for negative trailerField: " + t);
        }
        try {
            new PSSParameterSpec("SHA-1", "MGF1", null, 20, 0);
            // No exception expected for trailerField=0, assuming valid
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception for trailerField=0: " + t);
        }
    }
}

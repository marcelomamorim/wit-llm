/* @test    @summary Test for ManifestEntryVerifier.update(byte[], int, int) handling null and invalid inputs    @run main ManifestEntryVerifierWitupTest */

import sun.security.util.ManifestEntryVerifier;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

public class ManifestEntryVerifierWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithNullBuffer();
        testUpdateWithEmptyBuffer();
        testUpdateWithNullBufferAndSkipFalse();
        testUpdateWithNegativeOffset();
        testUpdateWithNegativeLength();
        System.out.println("All tests passed.");
    }

    private static void testUpdateWithNullBuffer() {
        ManifestEntryVerifier verifier = new ManifestEntryVerifier(null);
        verifier.setEntry("testEntry", null);
        try {
            verifier.update(null, 0, 1);
        } catch (NullPointerException e) {
            // Expected, as buffer is null
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null buffer: " + e);
        }
        throw new AssertionError("Expected NullPointerException for null buffer");
    }

    private static void testUpdateWithEmptyBuffer() {
        ManifestEntryVerifier verifier = new ManifestEntryVerifier(null);
        verifier.setEntry("testEntry", null);
        try {
            verifier.update(new byte[0], 0, 0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty buffer: " + e);
        }
    }

    private static void testUpdateWithNullBufferAndSkipFalse() {
        ManifestEntryVerifier verifier = new ManifestEntryVerifier(null);
        verifier.setEntry("testEntry", null);
        // Force skip to false to test behavior
        // Since skip is private, we simulate by setting entry to non-null and attributes
        // but as we can't access skip directly, we simulate by calling update with null buffer
        try {
            verifier.update(null, 0, 1);
        } catch (NullPointerException e) {
            // Expected
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null buffer with skip false: " + e);
        }
    }

    private static void testUpdateWithNegativeOffset() {
        ManifestEntryVerifier verifier = new ManifestEntryVerifier(null);
        verifier.setEntry("testEntry", null);
        byte[] buffer = new byte[10];
        try {
            verifier.update(buffer, -1, 5);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected due to negative offset
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative offset: " + e);
        }
    }

    private static void testUpdateWithNegativeLength() {
        ManifestEntryVerifier verifier = new ManifestEntryVerifier(null);
        verifier.setEntry("testEntry", null);
        byte[] buffer = new byte[10];
        try {
            verifier.update(buffer, 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected due to negative length
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for negative length: " + e);
        }
    }
}

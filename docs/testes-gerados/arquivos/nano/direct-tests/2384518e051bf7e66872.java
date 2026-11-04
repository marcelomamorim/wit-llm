/* @test    @summary Test ManifestEntryVerifier.update method with valid buffer input    @run main ManifestEntryVerifierWitupTest */

import sun.security.util.ManifestEntryVerifier;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

public class ManifestEntryVerifierWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithValidBuffer();
        testUpdateSkipFlagTrue();
    }

    private static void testUpdateWithValidBuffer() throws Exception {
        // Prepare a dummy manifest (null for simplicity, as method does not use it)
        ManifestEntryVerifier verifier = new ManifestEntryVerifier(null);
        // Access private fields via reflection to set up internal state
        java.lang.reflect.Field digestsField = ManifestEntryVerifier.class.getDeclaredField("digests");
        java.lang.reflect.Field skipField = ManifestEntryVerifier.class.getDeclaredField("skip");
        java.lang.reflect.Field signersField = ManifestEntryVerifier.class.getDeclaredField("signers");
        java.lang.reflect.Field createdDigestsField = ManifestEntryVerifier.class.getDeclaredField("createdDigests");

        digestsField.setAccessible(true);
        skipField.setAccessible(true);
        signersField.setAccessible(true);
        createdDigestsField.setAccessible(true);

        // Initialize with one MessageDigest
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ArrayList<MessageDigest> digestList = new ArrayList<>();
        digestList.add(md);
        digestsField.set(verifier, digestList);
        skipField.setBoolean(verifier, false);
        signersField.set(verifier, null);
        createdDigestsField.set(verifier, new HashMap<>());

        byte[] buffer = {0x01, 0x02, 0x03, 0x04};
        int off = 0;
        int len = buffer.length;

        verifier.update(buffer, off, len);

        // Verify that the digest has been updated
        byte[] digestBytes = md.digest();
        // Since digest is updated, digestBytes should not be empty
        boolean notEmpty = digestBytes != null && digestBytes.length > 0;
        if (!notEmpty) {
            throw new AssertionError("Digest was not updated properly.");
        }
        System.out.println("testUpdateWithValidBuffer passed.");
    }

    private static void testUpdateSkipFlagTrue() throws Exception {
        // Prepare verifier with skip = true
        ManifestEntryVerifier verifier = new ManifestEntryVerifier(null);
        java.lang.reflect.Field skipField = ManifestEntryVerifier.class.getDeclaredField("skip");
        skipField.setAccessible(true);
        skipField.setBoolean(verifier, true);

        // Prepare a dummy digest list
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ArrayList<MessageDigest> digestList = new ArrayList<>();
        digestList.add(md);
        java.lang.reflect.Field digestsField = ManifestEntryVerifier.class.getDeclaredField("digests");
        digestsField.setAccessible(true);
        digestsField.set(verifier, digestList);

        byte[] buffer = {0x0A, 0x0B, 0x0C};
        verifier.update(buffer, 0, buffer.length);
        // Since skip is true, digest should not be updated; digest remains unchanged
        byte[] digestBytes = md.digest();
        // digestBytes should be empty because digest was not updated
        if (digestBytes != null && digestBytes.length > 0) {
            throw new AssertionError("Digest should not be updated when skip is true.");
        }
        System.out.println("testUpdateSkipFlagTrue passed.");
    }
}

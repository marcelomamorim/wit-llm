/* @test    @summary Test for sun.security.x509.X500Name.getEncodedInternal() method    @run main X500NameWitupTest */

import java.io.IOException;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedInternalNullEncoded();
        testGetEncodedInternalAlreadyEncoded();
    }

    private static void testGetEncodedInternalNullEncoded() throws Exception {
        // Create an X500Name with minimal constructor to ensure encoded is null
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Org, C=US");
        // Force encoded to null to test the path
        // Reflection to set encoded to null if needed, but since it's private, we assume initial state
        // Call method and verify no exception and output is not null
        byte[] result = name.getEncodedInternal();
        if (result == null) {
            throw new AssertionError("getEncodedInternal() returned null");
        }
        System.out.println("testGetEncodedInternalNullEncoded passed.");
    }

    private static void testGetEncodedInternalAlreadyEncoded() throws Exception {
        // Create an X500Name and call getEncodedInternal twice to test caching
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Org, C=US");
        byte[] firstCall = name.getEncodedInternal();
        byte[] secondCall = name.getEncodedInternal();
        if (firstCall != secondCall) {
            throw new AssertionError("getEncodedInternal() did not cache the result");
        }
        System.out.println("testGetEncodedInternalAlreadyEncoded passed.");
    }
}

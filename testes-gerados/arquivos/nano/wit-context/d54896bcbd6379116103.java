/* @test    @summary Test DerOutputStream.putOID for valid ObjectIdentifier and boundary conditions    @run main DerOutputStreamWitupTest */

import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutOIDWithValidObjectIdentifier();
        testPutOIDWithNullObjectIdentifier();
        testPutOIDWithEmptyObjectIdentifier();
        testPutOIDWithLargeOID();
    }

    static void testPutOIDWithValidObjectIdentifier() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549");
        DerOutputStream dos = new DerOutputStream();
        dos.putOID(oid);
        byte[] result = dos.toByteArray();
        if (result.length == 0) {
            throw new AssertionError("Resulting byte array should not be empty for valid OID");
        }
    }

    static void testPutOIDWithNullObjectIdentifier() {
        try {
            DerOutputStream dos = new DerOutputStream();
            dos.putOID(null);
            throw new AssertionError("Expected NullPointerException for null ObjectIdentifier");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testPutOIDWithEmptyObjectIdentifier() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {});
        DerOutputStream dos = new DerOutputStream();
        dos.putOID(oid);
        byte[] result = dos.toByteArray();
        if (result.length == 0) {
            throw new AssertionError("Resulting byte array should not be empty for empty OID");
        }
    }

    static void testPutOIDWithLargeOID() throws IOException {
        int[] largeOid = new int[100];
        for (int i = 0; i < largeOid.length; i++) {
            largeOid[i] = i + 1;
        }
        ObjectIdentifier oid = new ObjectIdentifier(largeOid);
        DerOutputStream dos = new DerOutputStream();
        dos.putOID(oid);
        byte[] result = dos.toByteArray();
        if (result.length == 0) {
            throw new AssertionError("Resulting byte array should not be empty for large OID");
        }
    }
}

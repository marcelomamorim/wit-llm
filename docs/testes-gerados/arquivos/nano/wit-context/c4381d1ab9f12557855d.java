/* @test    @summary Test for sun.security.x509.AlgorithmId.encode(DerOutputStream)    @run main AlgorithmIdWitupTest */

import java.io.*;
import sun.security.util.*;
import sun.security.x509.*;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidDerOutputStream();
        testEncodeWithNullDerOutputStream();
    }

    private static void testEncodeWithValidDerOutputStream() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.5");
        AlgorithmId algId = new AlgorithmId(oid);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);
        try {
            algId.encode(dos);
            byte[] encodedBytes = baos.toByteArray();
            if (encodedBytes == null || encodedBytes.length == 0) {
                throw new AssertionError("Encoding produced empty byte array");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during encode with valid DerOutputStream: " + e);
        }
    }

    private static void testEncodeWithNullDerOutputStream() {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.5");
        AlgorithmId algId = new AlgorithmId(oid);
        try {
            algId.encode(null);
            throw new AssertionError("Expected NullPointerException not thrown when encoding with null DerOutputStream");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type when encoding with null DerOutputStream: " + e);
        }
    }
}

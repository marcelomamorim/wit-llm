/* @test    @summary Test the encode() method of AlgorithmId for correct byte array output.    @run main AlgorithmIdWitupTest */

import java.io.*;
import sun.security.x509.*;
import sun.security.util.*;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeReturnsByteArray();
        testEncodeWithNullParameters();
    }

    private static void testEncodeReturnsByteArray() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.newInternal("1.2.840.113549.1.1.5"); // sha1WithRSAEncryption
        AlgorithmId algId = new AlgorithmId(oid);
        byte[] encodedBytes = algId.encode();
        if (encodedBytes == null) {
            throw new AssertionError("encode() returned null");
        }
        if (!(encodedBytes instanceof byte[])) {
            throw new AssertionError("encode() did not return byte[]");
        }
        if (encodedBytes.length == 0) {
            throw new AssertionError("encode() returned empty byte array");
        }
        System.out.println("testEncodeReturnsByteArray passed.");
    }

    private static void testEncodeWithNullParameters() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.newInternal("2.16.840.1.101.3.4.2.1"); // sha-256
        AlgorithmId algId = new AlgorithmId(oid, null);
        byte[] encodedBytes = algId.encode();
        if (encodedBytes == null) {
            throw new AssertionError("encode() with null parameters returned null");
        }
        System.out.println("testEncodeWithNullParameters passed.");
    }
}

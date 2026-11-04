/* @test    @summary Test derEncode method of AlgorithmId class    @run main AlgorithmIdWitupTest */

import java.io.*;
import sun.security.x509.AlgorithmId;
import sun.security.util.*;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testDerEncodeWithNullParams();
        testDerEncodeWithAlgorithmParameters();
    }

    static void testDerEncodeWithNullParams() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.of("1.2.840.113549.1.1.1"); // rsaEncryption
        AlgorithmId algId = new AlgorithmId(oid);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded byte array is null or empty");
        }
        System.out.println("testDerEncodeWithNullParams passed.");
    }

    static void testDerEncodeWithAlgorithmParameters() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.of("1.2.840.113549.1.1.5"); // sha1WithRSAEncryption
        AlgorithmParameters params = AlgorithmParameters.getInstance("SHA1withRSA");
        AlgorithmId algId = new AlgorithmId(oid, params);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded byte array is null or empty");
        }
        System.out.println("testDerEncodeWithAlgorithmParameters passed.");
    }
}

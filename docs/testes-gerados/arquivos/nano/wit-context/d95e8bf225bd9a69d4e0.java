/* @test    @summary Test for AlgorithmId.derEncode method behavior and exception paths    @run main AlgorithmIdWitupTest */

import java.io.*;
import sun.security.x509.AlgorithmId;
import sun.security.util.ObjectIdentifier;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testDerEncodeNormal();
        testDerEncodeWithNullParams();
        testDerEncodeWithInvalidStream();
    }

    static void testDerEncodeNormal() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.5"); // sha1WithRSAEncryption
        AlgorithmId algId = new AlgorithmId(oid);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] result = baos.toByteArray();
        if (result == null || result.length == 0) {
            throw new AssertionError("derEncode returned empty result");
        }
    }

    static void testDerEncodeWithNullParams() throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.5");
        AlgorithmId algId = new AlgorithmId(oid, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        algId.derEncode(baos);
        byte[] result = baos.toByteArray();
        if (result == null || result.length == 0) {
            throw new AssertionError("derEncode with null params returned empty result");
        }
    }

    static void testDerEncodeWithInvalidStream() {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.5");
        AlgorithmId algId = new AlgorithmId(oid);
        OutputStream invalidStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                throw new IOException("Simulated write failure");
            }
        };
        try {
            algId.derEncode(invalidStream);
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}

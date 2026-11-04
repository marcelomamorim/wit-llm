/* @test    @summary Test constructor of JarVerifier with rawBytes array    @run main JarVerifierWitupTest */

import java.util.jar.JarVerifier;
import java.io.ByteArrayInputStream;

public class JarVerifierWitupTest {

    public static void main(String[] args) {
        testConstructorWithEmptyBytes();
        testConstructorWithSampleBytes();
    }

    static void testConstructorWithEmptyBytes() {
        byte[] emptyBytes = new byte[0];
        JarVerifier verifier = new JarVerifier(emptyBytes);
        assert verifier != null : "Verifier should not be null with empty byte array";
    }

    static void testConstructorWithSampleBytes() {
        byte[] sampleBytes = {1, 2, 3, 4, 5};
        JarVerifier verifier = new JarVerifier(sampleBytes);
        assert verifier != null : "Verifier should not be null with sample byte array";
    }
}

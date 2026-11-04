/* @test    @summary Test AlgorithmId.encode method for basic serialization.    @run main AlgorithmIdWitupTest */

import java.io.*;
import sun.security.x509.AlgorithmId;
import sun.security.util.DerOutputStream;
import org.junit.Test;

public class AlgorithmIdWitupTest {

    public static void main(String[] args) {
        new AlgorithmIdWitupTest().testEncodeBasic();
    }

    public void testEncodeBasic() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.1.1");
            AlgorithmId algId = new AlgorithmId(oid);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DerOutputStream dos = new DerOutputStream(baos);
            algId.encode(dos);
            byte[] encodedBytes = baos.toByteArray();
            // Basic check: encodedBytes should not be null and have length > 0
            assert encodedBytes != null : "Encoded byte array is null";
            assert encodedBytes.length > 0 : "Encoded byte array is empty";
        } catch (Exception e) {
            throw new RuntimeException("Encoding failed: " + e.getMessage(), e);
        }
    }
}

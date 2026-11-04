/*
 * @test
 * @summary Checks observable DER output of BasicConstraintsExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] encodeToBytes(BasicConstraintsExtension ext) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        // 1. Encoding yields a non-empty DER SEQUENCE (tag 0x30) for a CA extension.
        BasicConstraintsExtension caExt = new BasicConstraintsExtension(true, 2);
        byte[] caBytes = encodeToBytes(caExt);
        check(caBytes.length > 0, "encoded output must not be empty");
        check((caBytes[0] & 0xFF) == 0x30, "outer encoding must be a DER SEQUENCE");

        // 2. Encoding the same object twice is deterministic.
        byte[] caBytes2 = encodeToBytes(caExt);
        check(Arrays.equals(caBytes, caBytes2), "repeated encode must be identical");

        // 3. A CA extension and a non-CA extension encode differently.
        BasicConstraintsExtension nonCa = new BasicConstraintsExtension(false, 0);
        byte[] nonCaBytes = encodeToBytes(nonCa);
        check((nonCaBytes[0] & 0xFF) == 0x30, "non-CA outer encoding must be a DER SEQUENCE");
        check(!Arrays.equals(caBytes, nonCaBytes), "CA and non-CA encodings must differ");

        // 4. Different path lengths produce different encodings when ca is true.
        BasicConstraintsExtension len1 = new BasicConstraintsExtension(true, 1);
        BasicConstraintsExtension len5 = new BasicConstraintsExtension(true, 5);
        check(!Arrays.equals(encodeToBytes(len1), encodeToBytes(len5)),
              "different pathLen must produce different encodings");

        // 5. Criticality flag is reflected in the encoding.
        BasicConstraintsExtension critical =
            new BasicConstraintsExtension(Boolean.TRUE, true, 1);
        BasicConstraintsExtension nonCritical =
            new BasicConstraintsExtension(Boolean.FALSE, true, 1);
        check(!Arrays.equals(encodeToBytes(critical), encodeToBytes(nonCritical)),
              "critical flag must change the encoding");

        // 6. Encoding into the same stream appends output (stream is written to).
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        caExt.encode(bos);
        int firstLen = bos.size();
        check(firstLen > 0, "encode must write bytes to the stream");
        nonCa.encode(bos);
        check(bos.size() > firstLen, "second encode must append bytes");
    }
}

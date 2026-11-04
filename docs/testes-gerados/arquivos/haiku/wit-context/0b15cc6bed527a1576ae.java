/*
 * @test
 * @summary Checks Base64.Encoder.encode(byte[], byte[]) output size, output content and exception behavior
 * @run main Base64WitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        Base64.Encoder enc = Base64.getEncoder();
        byte[] src = "foobar".getBytes(StandardCharsets.US_ASCII);

        byte[] exact = new byte[8];
        int n = enc.encode(src, exact);
        check(n == 8, "expected 8 bytes written, got " + n);
        check("Zm9vYmFy".equals(new String(exact, StandardCharsets.US_ASCII)),
              "unexpected encoding: " + new String(exact, StandardCharsets.US_ASCII));

        byte[] larger = new byte[16];
        n = enc.encode(src, larger);
        check(n == 8, "expected 8 bytes written into larger array, got " + n);
        check("Zm9vYmFy".equals(new String(larger, 0, 8, StandardCharsets.US_ASCII)),
              "unexpected encoding into larger array");

        byte[] small = new byte[7];
        expectIllegalArgument(enc, src, small);

        byte[] emptyDst = new byte[0];
        n = enc.encode(new byte[0], emptyDst);
        check(n == 0, "expected 0 bytes written for empty input, got " + n);

        expectNullPointer(enc, src, null);
    }

    private static void expectIllegalArgument(Base64.Encoder enc, byte[] src, byte[] dst) {
        try {
            enc.encode(src, dst);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for too-small output array");
    }

    private static void expectNullPointer(Base64.Encoder enc, byte[] src, byte[] dst) {
        try {
            enc.encode(src, dst);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null output array");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

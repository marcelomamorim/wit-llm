/*
 * @test
 * @summary Verifies Mac.update(byte[]) state checks, null handling and incremental update equivalence
 * @run main MacWitupTest
 */

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.util.Arrays;

public class MacWitupTest {

    static final class RawKey implements SecretKey {
        private final byte[] key;

        RawKey(byte[] key) {
            this.key = key.clone();
        }

        public String getAlgorithm() {
            return "RAW";
        }

        public String getFormat() {
            return "RAW";
        }

        public byte[] getEncoded() {
            return key.clone();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");

        boolean thrown = false;
        try {
            mac.update(new byte[] {1, 2, 3});
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "update before init must throw IllegalStateException");

        mac.init(new RawKey(new byte[] {0x0b, 0x0b, 0x0b, 0x0b}));

        mac.update(null);
        byte[] emptyResult = mac.doFinal();
        check(emptyResult.length == mac.getMacLength(),
              "doFinal length must equal getMacLength");

        byte[] a = "hello ".getBytes("UTF-8");
        byte[] b = "world".getBytes("UTF-8");
        byte[] combined = new byte[a.length + b.length];
        System.arraycopy(a, 0, combined, 0, a.length);
        System.arraycopy(b, 0, combined, a.length, b.length);

        mac.update(a);
        mac.update(b);
        byte[] incremental = mac.doFinal();

        mac.update(combined);
        byte[] single = mac.doFinal();

        check(Arrays.equals(incremental, single),
              "incremental update must equal single update of concatenated input");

        mac.update(null);
        byte[] afterNull = mac.doFinal();
        check(Arrays.equals(afterNull, emptyResult),
              "update(null) must not change the MAC state");
    }
}

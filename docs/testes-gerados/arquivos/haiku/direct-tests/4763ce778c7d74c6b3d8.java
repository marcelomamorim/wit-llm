/*
 * @test
 * @summary KeyUsageExtension(BitArray) sets critical, extension id, bit values and encoding
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main KeyUsageExtensionWitupTest
 */

import java.util.Arrays;
import sun.security.util.BitArray;
import sun.security.x509.KeyUsageExtension;

public class KeyUsageExtensionWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        BitArray ba = new BitArray(3);
        ba.set(0, true);

        KeyUsageExtension ku = new KeyUsageExtension(ba);

        check(ku.isCritical(), "extension must be critical");
        check(Boolean.TRUE.equals(ku.get(KeyUsageExtension.DIGITAL_SIGNATURE)),
              "digital_signature bit (position 0) must be set");
        check(!Boolean.TRUE.equals(ku.get(KeyUsageExtension.KEY_CERTSIGN)),
              "key_certsign bit must not be set");

        byte[] value = ku.getExtensionValue();
        check(value != null && value.length > 0, "encoded extension value must not be empty");

        KeyUsageExtension fromBooleans =
                new KeyUsageExtension(new boolean[] {true, false, false});
        check(Arrays.equals(value, fromBooleans.getExtensionValue()),
              "BitArray and boolean[] constructors must encode identically");
    }
}

/*
 * @test
 * @summary Round-trip test for the UniqueIdentity(DerInputStream) constructor
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main UniqueIdentityWitupTest
 */

import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.x509.UniqueIdentity;

public class UniqueIdentityWitupTest {

    public static void main(String[] args) throws IOException {
        byte[][] inputs = {
            {0x0F},
            {(byte) 0xA5, 0x01},
            {0x00, (byte) 0xFF, 0x33}
        };

        for (byte[] in : inputs) {
            UniqueIdentity original = new UniqueIdentity(in);

            DerOutputStream out = new DerOutputStream();
            original.encode(out, (byte) 0x03);
            byte[] der = out.toByteArray();

            DerInputStream dis = new DerInputStream(der);
            UniqueIdentity decoded = new UniqueIdentity(dis);

            boolean[] expected = original.getId();
            boolean[] actual = decoded.getId();

            check(expected != null, "original getId() returned null");
            check(actual != null, "decoded getId() returned null");
            check(Arrays.equals(expected, actual),
                  "decoded bits differ from original bits");
        }

        byte[] wrongTag = {0x02, 0x01, 0x00};
        boolean thrown = false;
        try {
            new UniqueIdentity(new DerInputStream(wrongTag));
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "expected IOException for non-BIT-STRING tag");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

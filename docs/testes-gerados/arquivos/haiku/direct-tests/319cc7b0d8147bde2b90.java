/*
 * @test
 * @summary Basic behavior of the public DerInputStream(byte[]) constructor
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] der = {0x02, 0x01, 0x05};
        DerInputStream in = new DerInputStream(der);
        if (in == null) {
            throw new RuntimeException("constructor returned null");
        }

        boolean npe = false;
        try {
            new DerInputStream((byte[]) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null data");
        }
    }
}

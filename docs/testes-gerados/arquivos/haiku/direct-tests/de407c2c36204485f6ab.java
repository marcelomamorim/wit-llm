/*
 * @test
 * @summary Verify DerInputStream.getIA5String decodes IA5String values and rejects other tags
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check(new DerInputStream(new byte[]{0x16, 0x03, 'a', 'b', 'c'}).getIA5String(), "abc");
        check(new DerInputStream(new byte[]{0x16, 0x00}).getIA5String(), "");

        boolean thrown = false;
        try {
            new DerInputStream(new byte[]{0x04, 0x01, 0x41}).getIA5String();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for non-IA5String tag");
        }
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }
}

/*
 * @test
 * @summary Verify StringUTF16.putCharSB stores UTF16 chars big-endian at the given char index
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */
import java.lang.reflect.Method;

public class StringUTF16WitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        Method m = c.getMethod("putCharSB", byte[].class, int.class, int.class);
        m.setAccessible(true);

        byte[] val = new byte[8];
        m.invoke(null, val, 0, 0x4142);
        expect(val[0] == 0x41 && val[1] == 0x42, "index 0 stores big-endian char");
        expect(val[2] == 0 && val[3] == 0 && val[4] == 0 && val[5] == 0
               && val[6] == 0 && val[7] == 0, "other bytes untouched");

        m.invoke(null, val, 3, 0xBEEF);
        expect((val[6] & 0xff) == 0xBE && (val[7] & 0xff) == 0xEF, "index 3 stores big-endian char");
        expect(val[0] == 0x41 && val[1] == 0x42, "index 0 preserved after write to index 3");

        m.invoke(null, val, 0, 0x0000);
        expect(val[0] == 0 && val[1] == 0, "index 0 overwritten with zero");
        expect((val[6] & 0xff) == 0xBE && (val[7] & 0xff) == 0xEF, "index 3 preserved after write to index 0");
    }

    private static void expect(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}

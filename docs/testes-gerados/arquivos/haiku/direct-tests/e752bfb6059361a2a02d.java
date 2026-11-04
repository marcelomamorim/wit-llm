/*
 * @test
 * @summary Black-box checks of StringUTF16.inflate via reflection (LATIN1 to UTF16 widening)
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class StringUTF16WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        Method m = c.getMethod("inflate", byte[].class, int.class, byte[].class, int.class, int.class);

        byte[] src1 = {'a', 'b', 'c'};
        byte[] dst1 = new byte[6];
        m.invoke(null, src1, 0, dst1, 0, 3);
        check(new String(dst1, StandardCharsets.UTF_16BE).equals("abc"), "basic inflate failed");

        byte[] src2 = {(byte) 0xff};
        byte[] dst2 = new byte[2];
        m.invoke(null, src2, 0, dst2, 0, 1);
        check(dst2[0] == 0 && dst2[1] == (byte) 0xff, "high-bit byte must be masked to 0x00ff");

        byte[] src3 = {'x', 'y'};
        byte[] dst3 = new byte[8];
        m.invoke(null, src3, 0, dst3, 2, 2);
        check(dst3[0] == 0 && dst3[1] == 0 && dst3[2] == 0 && dst3[3] == 0, "prefix must be untouched");
        check(dst3[4] == 0 && dst3[5] == 'x' && dst3[6] == 0 && dst3[7] == 'y', "dstOff not honored");

        byte[] src4 = {'q', 'a', 'b'};
        byte[] dst4 = new byte[4];
        m.invoke(null, src4, 1, dst4, 0, 2);
        check(new String(dst4, StandardCharsets.UTF_16BE).equals("ab"), "srcOff not honored");

        byte[] src5 = {'z'};
        byte[] dst5 = new byte[2];
        m.invoke(null, src5, 0, dst5, 0, 0);
        check(dst5[0] == 0 && dst5[1] == 0, "len 0 must not write");
    }
}

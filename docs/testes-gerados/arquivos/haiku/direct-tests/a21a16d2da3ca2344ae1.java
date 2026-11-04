/*
 * @test
 * @summary Behavioral tests for java.lang.StringUTF16.codePointAtSB (checked code point access on UTF16 byte arrays)
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    static Method codePointAtSB;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        codePointAtSB = c.getDeclaredMethod("codePointAtSB", byte[].class, int.class, int.class);
        codePointAtSB.setAccessible(true);

        // Single BMP character 'A' (0x0041) stored as big-endian UTF16 bytes
        byte[] a = new byte[] {0x00, 0x41};
        check(invoke(a, 0, 1) == 0x41, "BMP char");

        // Surrogate pair U+1F600: D83D DE00
        byte[] pair = new byte[] {(byte) 0xD8, (byte) 0x3D, (byte) 0xDE, 0x00};
        check(invoke(pair, 0, 2) == 0x1F600, "valid surrogate pair");

        // High surrogate alone at end of range: returned as is
        byte[] high = new byte[] {(byte) 0xD8, (byte) 0x3D};
        check(invoke(high, 0, 1) == 0xD83D, "lone high surrogate at end");

        // High surrogate followed by non-low-surrogate: returns the high surrogate
        byte[] notLow = new byte[] {(byte) 0xD8, (byte) 0x3D, 0x00, 0x41};
        check(invoke(notLow, 0, 2) == 0xD83D, "high surrogate followed by non-low");

        // Pair exists in array but end bound excludes the low surrogate
        check(invoke(pair, 0, 1) == 0xD83D, "end bound excludes low surrogate");

        // Index equal to length is out of bounds for checked access
        expectIndexFailure(a, 1, 2);

        // Negative index is out of bounds for checked access
        expectIndexFailure(a, -1, 1);
    }

    static int invoke(byte[] val, int index, int end) throws Exception {
        return (Integer) codePointAtSB.invoke(null, val, index, end);
    }

    static void expectIndexFailure(byte[] val, int index, int end) {
        try {
            invoke(val, index, end);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IndexOutOfBoundsException) {
                return;
            }
            throw new RuntimeException("Unexpected cause: " + cause, cause);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
        throw new RuntimeException("Expected index failure for index=" + index);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Failed: " + msg);
        }
    }
}

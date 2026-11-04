/*
 * @test
 * @summary Behavioral checks for java.lang.StringUTF16.stripTrailing(byte[]) via reflection
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    private static Method stripTrailing;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        stripTrailing = c.getDeclaredMethod("stripTrailing", byte[].class);
        stripTrailing.setAccessible(true);

        check(utf16("abc  "), "abc");
        check(utf16("abc"), null);
        check(utf16("   "), "");
        check(utf16(" a b\t"), " a b");
        check(utf16("x"), null);
        check(utf16("\u00e9 "), "\u00e9");
    }

    private static void check(byte[] in, String expected) throws Exception {
        String actual;
        try {
            actual = (String) stripTrailing.invoke(null, in);
        } catch (InvocationTargetException e) {
            throw new AssertionError("unexpected exception", e.getCause());
        }
        boolean ok = (expected == null) ? (actual == null) : expected.equals(actual);
        if (!ok) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }

    private static byte[] utf16(String s) {
        byte[] b = new byte[s.length() * 2];
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            b[2 * i] = (byte) (ch >> 8);
            b[2 * i + 1] = (byte) ch;
        }
        return b;
    }
}

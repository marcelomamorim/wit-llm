/*
 * @test
 * @summary Witup-derived tests for StringUTF16.checkBoundsOffCount
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    private static final Method CHECK;

    static {
        try {
            Class<?> c = Class.forName("java.lang.StringUTF16");
            CHECK = c.getDeclaredMethod("checkBoundsOffCount", int.class, int.class, byte[].class);
            CHECK.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static Throwable run(int off, int count, byte[] val) {
        try {
            CHECK.invoke(null, off, count, val);
            return null;
        } catch (InvocationTargetException e) {
            return e.getCause();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static void expectOk(int off, int count, byte[] val) {
        Throwable t = run(off, count, val);
        if (t != null) {
            throw new RuntimeException("Unexpected exception for off=" + off + " count=" + count + ": " + t);
        }
    }

    static void expectSioobe(int off, int count, byte[] val) {
        Throwable t = run(off, count, val);
        if (!(t instanceof StringIndexOutOfBoundsException)) {
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for off=" + off
                    + " count=" + count + " but got: " + t);
        }
    }

    public static void main(String[] args) {
        byte[] val = new byte[6];
        expectOk(0, 0, val);
        expectOk(0, 3, val);
        expectOk(3, 0, val);
        expectOk(1, 2, val);
        expectOk(2, 1, val);

        expectSioobe(-1, 0, val);
        expectSioobe(0, -1, val);
        expectSioobe(-1, -1, val);
        expectSioobe(1, 3, val);
        expectSioobe(0, 4, val);
        expectSioobe(4, 0, val);

        byte[] empty = new byte[0];
        expectOk(0, 0, empty);
        expectSioobe(0, 1, empty);
        expectSioobe(1, 0, empty);
    }
}

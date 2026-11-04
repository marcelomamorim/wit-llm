/*
 * @test
 * @summary Verify StringLatin1.fillNull zeroes exactly the requested range and reports invalid ranges
 * @modules java.base/java.lang
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringLatin1WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringLatin1WitupTest {

    static Method fillNull;

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void invoke(byte[] val, int index, int end) throws Exception {
        fillNull.invoke(null, val, index, end);
    }

    static Throwable invokeExpectingFailure(byte[] val, int index, int end) throws Exception {
        try {
            invoke(val, index, end);
        } catch (InvocationTargetException e) {
            return e.getCause();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringLatin1");
        fillNull = c.getDeclaredMethod("fillNull", byte[].class, int.class, int.class);
        fillNull.setAccessible(true);

        byte[] partial = {1, 2, 3, 4, 5};
        invoke(partial, 1, 4);
        check(partial[0] == 1, "element before range modified");
        check(partial[1] == 0 && partial[2] == 0 && partial[3] == 0, "range not zeroed");
        check(partial[4] == 5, "element after range modified");

        byte[] full = {9, 9, 9};
        invoke(full, 0, 3);
        check(full[0] == 0 && full[1] == 0 && full[2] == 0, "full range not zeroed");

        byte[] empty = {7, 8};
        invoke(empty, 1, 1);
        check(empty[0] == 7 && empty[1] == 8, "empty range modified array");

        byte[] reversed = {7, 8, 9};
        Throwable t1 = invokeExpectingFailure(reversed, 2, 1);
        check(t1 instanceof IllegalArgumentException, "expected IllegalArgumentException for index > end, got " + t1);

        byte[] tooLong = {7, 8, 9};
        Throwable t2 = invokeExpectingFailure(tooLong, 0, 4);
        check(t2 instanceof ArrayIndexOutOfBoundsException, "expected ArrayIndexOutOfBoundsException for end > length, got " + t2);

        byte[] negative = {7, 8, 9};
        Throwable t3 = invokeExpectingFailure(negative, -1, 2);
        check(t3 instanceof ArrayIndexOutOfBoundsException, "expected ArrayIndexOutOfBoundsException for negative index, got " + t3);
    }
}

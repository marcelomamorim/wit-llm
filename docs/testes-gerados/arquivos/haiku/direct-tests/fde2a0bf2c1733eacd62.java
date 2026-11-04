/*
 * @test
 * @summary Verifies StringUTF16.checkBoundsBeginEnd accepts valid ranges and rejects invalid ones with StringIndexOutOfBoundsException
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    static Method checkMethod;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        checkMethod = c.getMethod("checkBoundsBeginEnd", int.class, int.class, byte[].class);
        checkMethod.setAccessible(true);

        byte[] val3 = new byte[6];
        byte[] val3Odd = new byte[7];
        byte[] empty = new byte[0];

        expectOk(0, 3, val3);
        expectOk(0, 0, val3);
        expectOk(3, 3, val3);
        expectOk(1, 2, val3);
        expectOk(0, 3, val3Odd);
        expectOk(0, 0, empty);

        expectSIOOBE(-1, 2, val3);
        expectSIOOBE(0, 4, val3);
        expectSIOOBE(2, 1, val3);
        expectSIOOBE(0, 1, empty);
        expectSIOOBE(0, 4, val3Odd);
        expectSIOOBE(-1, 0, empty);
    }

    static void expectOk(int begin, int end, byte[] val) throws Exception {
        try {
            checkMethod.invoke(null, begin, end, val);
        } catch (InvocationTargetException ex) {
            throw new AssertionError("Unexpected exception for " + begin + "," + end + ": " + ex.getCause(), ex.getCause());
        }
    }

    static void expectSIOOBE(int begin, int end, byte[] val) throws Exception {
        try {
            checkMethod.invoke(null, begin, end, val);
        } catch (InvocationTargetException ex) {
            if (!(ex.getCause() instanceof StringIndexOutOfBoundsException)) {
                throw new AssertionError("Wrong exception for " + begin + "," + end + ": " + ex.getCause(), ex.getCause());
            }
            return;
        }
        throw new AssertionError("No exception thrown for " + begin + "," + end);
    }
}

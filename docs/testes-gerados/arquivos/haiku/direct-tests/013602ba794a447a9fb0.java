/*
 * @test
 * @summary Verify PrintPattern.main prints the compiled pattern and propagates argument and syntax errors
 * @modules java.base/java.util.regex
 * @run main/othervm --add-opens java.base/java.util.regex=ALL-UNNAMED PrintPatternWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.PatternSyntaxException;

public class PrintPatternWitupTest {

    static Method mainMethod;

    static void init() throws Exception {
        Class<?> c = Class.forName("java.util.regex.PrintPattern");
        mainMethod = c.getMethod("main", String[].class);
        mainMethod.setAccessible(true);
    }

    static String runCapture(String... args) throws Exception {
        PrintStream orig = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos, true));
        try {
            mainMethod.invoke(null, (Object) args);
        } finally {
            System.setOut(orig);
        }
        return bos.toString();
    }

    static void expectCause(Class<?> expected, String... args) throws Exception {
        try {
            mainMethod.invoke(null, (Object) args);
        } catch (InvocationTargetException e) {
            if (!expected.isInstance(e.getCause())) {
                throw new RuntimeException("unexpected cause: " + e.getCause());
            }
            return;
        }
        throw new RuntimeException("expected exception was not thrown");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        init();

        String out1 = runCapture("abc");
        check(out1.contains("   Pattern: abc"), "missing pattern header for abc: " + out1);

        String out2 = runCapture("a+b");
        check(out2.contains("   Pattern: a+b"), "missing pattern header for a+b: " + out2);

        expectCause(PatternSyntaxException.class, "(");

        expectCause(ArrayIndexOutOfBoundsException.class);
    }
}

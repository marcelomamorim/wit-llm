/*
 * @test
 * @summary Verifies PrintPattern.main through reflection: valid pattern input, invalid pattern input raising PatternSyntaxException, and missing argument raising ArrayIndexOutOfBoundsException
 * @run main/othervm --add-opens java.base/java.util.regex=ALL-UNNAMED PrintPatternWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.PatternSyntaxException;

public class PrintPatternWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.util.regex.PrintPattern");
        Method m = c.getDeclaredMethod("main", String[].class);
        m.setAccessible(true);

        invokeNoFailure(m, new String[]{"abc"});

        Throwable t = invokeExpectingFailure(m, new String[]{"a("});
        if (!(t instanceof PatternSyntaxException)) {
            throw new RuntimeException("Expected PatternSyntaxException but got: " + t);
        }

        t = invokeExpectingFailure(m, new String[0]);
        if (!(t instanceof ArrayIndexOutOfBoundsException)) {
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException but got: " + t);
        }
    }

    static void invokeNoFailure(Method m, String[] a) throws Exception {
        try {
            m.invoke(null, (Object) a);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unexpected exception", e.getCause());
        }
    }

    static Throwable invokeExpectingFailure(Method m, String[] a) throws Exception {
        try {
            m.invoke(null, (Object) a);
        } catch (InvocationTargetException e) {
            return e.getCause();
        }
        throw new RuntimeException("Expected an exception but none was thrown");
    }
}

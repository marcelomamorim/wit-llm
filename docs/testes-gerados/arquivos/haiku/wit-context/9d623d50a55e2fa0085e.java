/*
 * @test
 * @summary Witup generated checks for the package-private RBCollationTables(String, int) constructor via reflection
 * @run main/othervm --add-opens java.base/java.text=ALL-UNNAMED RBCollationTablesWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public class RBCollationTablesWitupTest {

    private static Constructor<?> constructor() throws Exception {
        Class<?> cls = Class.forName("java.text.RBCollationTables");
        Constructor<?> ctor = cls.getDeclaredConstructor(String.class, int.class);
        ctor.setAccessible(true);
        return ctor;
    }

    private static Object build(String rules, int decmp) throws Exception {
        try {
            return constructor().newInstance(rules, decmp);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ParseException) {
                throw (ParseException) cause;
            }
            throw e;
        }
    }

    private static void checkValidRulesAccepted() throws Exception {
        for (int decmp = 0; decmp <= 1; decmp++) {
            Object tables = build("< a < b", decmp);
            if (tables == null) {
                throw new AssertionError("constructor returned null for decmp=" + decmp);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        checkValidRulesAccepted();
    }
}

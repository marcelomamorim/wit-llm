/*
 * @test
 * @summary Test java.lang.Class.forName(Module module, String name) for null argument checks and basic loading
 * @run main ClassWitupTest
 */

import java.lang.Class;
import java.lang.Module;
import java.util.Objects;
import java.lang.SecurityManager;
import java.lang.reflect.InvocationTargetException;

public class ClassWitupTest {
    public static void main(String[] args) throws Exception {
        testNullModule();
        testNullName();
        testLoadClassWithDummyModule();
    }

    private static void testNullModule() {
        try {
            Class.forName(null, "java.lang.String");
            throw new RuntimeException("Expected NullPointerException for null module");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullName() {
        Module m = ClassWitupTest.class.getModule();
        try {
            Class.forName(m, null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testLoadClassWithDummyModule() throws Exception {
        Module m = ClassWitupTest.class.getModule();
        String className = "java.lang.String";
        Class<?> cls = Class.forName(m, className);
        if (!"java.lang.String".equals(cls.getName())) {
            throw new RuntimeException("Loaded class name mismatch: " + cls.getName());
        }
    }
}

/*
 * @test
 * @summary Behavioral checks for Class.forName(Module, String)
 * @run main ClassWitupTest
 */

public class ClassWitupTest {

    public static void main(String[] args) throws Exception {
        testNullModuleThrowsNPE();
        testNullNameThrowsNPE();
        testLoadBootClassFromNamedModule();
        testLoadUnnamedModuleClass();
        testClassNotInModuleReturnsNull();
    }

    static void testNullModuleThrowsNPE() {
        try {
            Class.forName((Module) null, "java.lang.String");
            throw new RuntimeException("Expected NullPointerException for null module");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullNameThrowsNPE() {
        try {
            Class.forName(String.class.getModule(), null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testLoadBootClassFromNamedModule() {
        Class<?> c = Class.forName(String.class.getModule(), "java.lang.String");
        if (c != String.class) {
            throw new RuntimeException("Expected String.class but got " + c);
        }
    }

    static void testLoadUnnamedModuleClass() {
        Module m = ClassWitupTest.class.getModule();
        Class<?> c = Class.forName(m, "ClassWitupTest");
        if (c != ClassWitupTest.class) {
            throw new RuntimeException("Expected ClassWitupTest.class but got " + c);
        }
    }

    static void testClassNotInModuleReturnsNull() {
        Class<?> c = Class.forName(String.class.getModule(), "ClassWitupTest");
        if (c != null) {
            throw new RuntimeException("Expected null for class not in module but got " + c);
        }
    }
}

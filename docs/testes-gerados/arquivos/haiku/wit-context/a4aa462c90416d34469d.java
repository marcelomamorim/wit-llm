/*
 * @test
 * @summary Public ClassLoader.loadClass delegation and CNFE behavior for a ClassLoader subclass
 * @run main ClassLoaderWitupTest
 */

public class ClassLoaderWitupTest {

    static class TestLoader extends ClassLoader {
        TestLoader(ClassLoader parent) {
            super(parent);
        }
    }

    public static void main(String[] args) throws Exception {
        TestLoader bootParent = new TestLoader(null);
        Class<?> s = bootParent.loadClass("java.lang.String");
        check(s == String.class, "java.lang.String not delegated to bootstrap");
        check(s.getClassLoader() == null, "String must be defined by bootstrap loader");

        TestLoader sysParent = new TestLoader(ClassLoader.getSystemClassLoader());
        Class<?> self = sysParent.loadClass("ClassLoaderWitupTest");
        check(self == ClassLoaderWitupTest.class, "class not delegated to system loader");

        boolean thrown = false;
        try {
            bootParent.loadClass("no.such.pkg.Missing");
        } catch (ClassNotFoundException e) {
            thrown = true;
        }
        check(thrown, "missing class must raise ClassNotFoundException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

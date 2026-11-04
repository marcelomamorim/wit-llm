/*
 * @test
 * @summary Witup-generated checks for the public StackTraceElement constructors and their observable getters, toString and equals behavior
 * @run main StackTraceElementWitupTest
 */

public class StackTraceElementWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFourArgConstructorGetters() {
        StackTraceElement ste = new StackTraceElement("com.example.Foo", "bar", "Foo.java", 42);
        check(ste.getClassName().equals("com.example.Foo"), "className");
        check(ste.getMethodName().equals("bar"), "methodName");
        check(ste.getFileName().equals("Foo.java"), "fileName");
        check(ste.getLineNumber() == 42, "lineNumber");
        check(ste.getModuleName() == null, "moduleName should be null");
        check(ste.getModuleVersion() == null, "moduleVersion should be null");
        check(ste.getClassLoaderName() == null, "classLoaderName should be null");
    }

    static void testFourArgToString() {
        StackTraceElement ste = new StackTraceElement("com.example.Foo", "bar", "Foo.java", 42);
        check(ste.toString().equals("com.example.Foo.bar(Foo.java:42)"), "toString: " + ste);

        StackTraceElement nat = new StackTraceElement("com.example.Foo", "nat", "Foo.java", -2);
        check(nat.toString().equals("com.example.Foo.nat(Native Method)"), "native toString: " + nat);
    }

    static void testSevenArgConstructorGetters() {
        StackTraceElement ste = new StackTraceElement("loader", "mod", "1.0",
                "com.example.Baz", "qux", "Baz.java", 7);
        check(ste.getClassLoaderName().equals("loader"), "classLoaderName");
        check(ste.getModuleName().equals("mod"), "moduleName");
        check(ste.getModuleVersion().equals("1.0"), "moduleVersion");
        check(ste.getClassName().equals("com.example.Baz"), "className");
        check(ste.getMethodName().equals("qux"), "methodName");
        check(ste.getFileName().equals("Baz.java"), "fileName");
        check(ste.getLineNumber() == 7, "lineNumber");
    }

    static void testEquality() {
        StackTraceElement a = new StackTraceElement("com.example.Foo", "bar", "Foo.java", 42);
        StackTraceElement b = new StackTraceElement("com.example.Foo", "bar", "Foo.java", 42);
        StackTraceElement c = new StackTraceElement("com.example.Foo", "bar", "Foo.java", 43);
        check(a.equals(b), "equal elements must be equal");
        check(a.hashCode() == b.hashCode(), "equal elements must share hashCode");
        check(!a.equals(c), "different line numbers must not be equal");
    }

    static void testNullArgumentsRejected() {
        boolean threwOnClass = false;
        try {
            new StackTraceElement(null, "bar", "Foo.java", 1);
        } catch (NullPointerException e) {
            threwOnClass = true;
        }
        check(threwOnClass, "null declaringClass must throw NullPointerException");

        boolean threwOnMethod = false;
        try {
            new StackTraceElement("com.example.Foo", null, "Foo.java", 1);
        } catch (NullPointerException e) {
            threwOnMethod = true;
        }
        check(threwOnMethod, "null methodName must throw NullPointerException");
    }

    public static void main(String[] args) {
        testFourArgConstructorGetters();
        testFourArgToString();
        testSevenArgConstructorGetters();
        testEquality();
        testNullArgumentsRejected();
    }
}

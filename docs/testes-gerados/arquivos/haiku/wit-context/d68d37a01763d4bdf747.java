/*
 * @test
 * @summary Witup generated checks for public StackTraceElement constructors and null-argument NPE contract
 * @run main StackTraceElementWitupTest
 */

public class StackTraceElementWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNPE(String label, Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        } catch (Exception other) {
            throw new RuntimeException(label + ": unexpected exception " + other, other);
        }
        throw new RuntimeException(label + ": expected NullPointerException");
    }

    public static void main(String[] args) throws Exception {
        StackTraceElement ste = new StackTraceElement("com.example.Foo", "bar", "Foo.java", 42);
        check("com.example.Foo".equals(ste.getClassName()), "declaringClass not preserved");
        check("bar".equals(ste.getMethodName()), "methodName not preserved");
        check("Foo.java".equals(ste.getFileName()), "fileName not preserved");
        check(ste.getLineNumber() == 42, "lineNumber not preserved");
        check(ste.getModuleName() == null, "moduleName should be null for 4-arg constructor");
        check(ste.getModuleVersion() == null, "moduleVersion should be null for 4-arg constructor");

        StackTraceElement nullFile = new StackTraceElement("com.example.Foo", "bar", null, -1);
        check(nullFile.getFileName() == null, "null fileName should be accepted");
        check(nullFile.getLineNumber() == -1, "negative lineNumber should be preserved");

        expectNPE("4-arg null declaringClass", () ->
                new StackTraceElement(null, "bar", "Foo.java", 1));
        expectNPE("4-arg null methodName", () ->
                new StackTraceElement("com.example.Foo", null, "Foo.java", 1));
        expectNPE("7-arg null declaringClass", () ->
                new StackTraceElement(null, null, null, null, null, null, 1));
    }
}

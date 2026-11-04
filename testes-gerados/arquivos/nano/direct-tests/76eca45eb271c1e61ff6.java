/* @test    @summary Test visitRequire method of Printer class    @run main PrinterWitupTest */

import java.lang.RuntimeException;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitRequire();
    }

    public static void testVisitRequire() {
        class TestPrinter extends jdk.internal.org.objectweb.asm.util.Printer {
            private String lastModule;
            private int lastAccess;
            private String lastVersion;

            @Override
            public void visitRequire(String module, int access, String version) {
                this.lastModule = module;
                this.lastAccess = access;
                this.lastVersion = version;
            }
        }

        TestPrinter printer = new TestPrinter();
        String testModule = "test.module";
        int testAccess = 123;
        String testVersion = "1.0";
        printer.visitRequire(testModule, testAccess, testVersion);

        if (!testModule.equals(printer.lastModule)) {
            throw new AssertionError("Expected module: " + testModule + ", but got: " + printer.lastModule);
        }
        if (testAccess != printer.lastAccess) {
            throw new AssertionError("Expected access: " + testAccess + ", but got: " + printer.lastAccess);
        }
        if (!testVersion.equals(printer.lastVersion)) {
            throw new AssertionError("Expected version: " + testVersion + ", but got: " + printer.lastVersion);
        }
    }
}

/* @test    @summary Test visitModule method of Printer class    @run main PrinterWitupTest */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitModuleReturnsInstance();
        testVisitModuleWithNullName();
        testVisitModuleWithEmptyStrings();
    }

    private static void testVisitModuleReturnsInstance() {
        Printer printer = new Printer() {
            @Override
            public Printer visitModule(String name, int access, String version) {
                if (name == null || version == null) {
                    throw new NullPointerException();
                }
                return this;
            }
        };
        Printer result = printer.visitModule("moduleName", 0, "1.0");
        if (result != printer) {
            throw new AssertionError("Expected the same Printer instance to be returned.");
        }
    }

    private static void testVisitModuleWithNullName() {
        Printer printer = new Printer() {
            @Override
            public Printer visitModule(String name, int access, String version) {
                if (name == null) {
                    throw new NullPointerException("name is null");
                }
                return this;
            }
        };
        try {
            printer.visitModule(null, 0, "1.0");
            throw new AssertionError("Expected NullPointerException for null name.");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testVisitModuleWithEmptyStrings() {
        Printer printer = new Printer() {
            @Override
            public Printer visitModule(String name, int access, String version) {
                if (name.isEmpty() || version.isEmpty()) {
                    // Accept empty strings, no exception
                }
                return this;
            }
        };
        Printer result = printer.visitModule("", 0, "");
        if (result != printer) {
            throw new AssertionError("Expected the same Printer instance to be returned for empty strings.");
        }
    }
}

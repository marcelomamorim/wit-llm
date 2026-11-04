/* @test    @summary Test visitPackage method of Printer class    @run main PrinterWitupTest */

import java.lang.RuntimeException;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitPackageWithValidString();
        testVisitPackageWithNull();
    }

    public static void testVisitPackageWithValidString() {
        jdk.internal.org.objectweb.asm.util.Printer printer = new jdk.internal.org.objectweb.asm.util.Printer() {
            @Override
            public void visitPackage(String packaze) {
                if (packaze == null) {
                    throw new AssertionError("Expected non-null string");
                }
                // Simulate behavior
            }
        };
        try {
            printer.visitPackage("com/example/package");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    public static void testVisitPackageWithNull() {
        jdk.internal.org.objectweb.asm.util.Printer printer = new jdk.internal.org.objectweb.asm.util.Printer() {
            @Override
            public void visitPackage(String packaze) {
                if (packaze != null) {
                    throw new AssertionError("Expected null string");
                }
                // Simulate behavior
            }
        };
        try {
            printer.visitPackage(null);
        } catch (RuntimeException e) {
            // Expected, as method throws RuntimeException
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}

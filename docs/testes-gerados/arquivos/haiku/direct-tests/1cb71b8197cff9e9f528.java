/*
 * @test
 * @summary Verify that Printer.visitPackage throws the default RuntimeException when not overridden
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    static class EmptyPrinter extends Printer {
        EmptyPrinter() {
            super(327680);
        }
    }

    static void checkThrowsMustBeOverriden(String arg) {
        Printer p = new EmptyPrinter();
        RuntimeException caught = null;
        try {
            p.visitPackage(arg);
        } catch (RuntimeException e) {
            caught = e;
        }
        if (caught == null) {
            throw new AssertionError("Expected RuntimeException for arg: " + arg);
        }
        if (!"Must be overriden".equals(caught.getMessage())) {
            throw new AssertionError("Unexpected message: " + caught.getMessage());
        }
    }

    static void testNonNullPackage() {
        checkThrowsMustBeOverriden("java/lang");
    }

    static void testEmptyPackage() {
        checkThrowsMustBeOverriden("");
    }

    static void testNullPackage() {
        checkThrowsMustBeOverriden(null);
    }

    public static void main(String[] args) {
        testNonNullPackage();
        testEmptyPackage();
        testNullPackage();
    }
}

/*
 * @test
 * @summary Verify Printer.visitExport base behavior throws and can be overridden
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    static class BasePrinter extends Printer {
        BasePrinter() {
            super(458752);
        }
    }

    static class RecordingPrinter extends Printer {
        String seenPackage;
        int seenAccess;
        String[] seenModules;

        RecordingPrinter() {
            super(458752);
        }

        @Override
        public void visitExport(String packaze, int access, String... modules) {
            seenPackage = packaze;
            seenAccess = access;
            seenModules = modules;
        }
    }

    public static void main(String[] args) {
        testBaseThrowsWithModules();
        testBaseThrowsWithoutModules();
        testOverrideReceivesArguments();
    }

    static void testBaseThrowsWithModules() {
        Printer p = new BasePrinter();
        try {
            p.visitExport("jdk/test", 0, "java.base");
            throw new AssertionError("expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testBaseThrowsWithoutModules() {
        Printer p = new BasePrinter();
        try {
            p.visitExport("jdk/test", 0);
            throw new AssertionError("expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testOverrideReceivesArguments() {
        RecordingPrinter r = new RecordingPrinter();
        r.visitExport("a/b", 32, "m1", "m2");
        if (!"a/b".equals(r.seenPackage)) {
            throw new AssertionError("wrong package: " + r.seenPackage);
        }
        if (r.seenAccess != 32) {
            throw new AssertionError("wrong access: " + r.seenAccess);
        }
        if (r.seenModules == null || r.seenModules.length != 2
                || !"m1".equals(r.seenModules[0]) || !"m2".equals(r.seenModules[1])) {
            throw new AssertionError("wrong modules");
        }
    }
}

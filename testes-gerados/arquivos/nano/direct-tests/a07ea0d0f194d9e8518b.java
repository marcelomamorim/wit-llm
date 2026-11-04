/*
 * @test
 * @summary Test visitExport method of ASMifier class for various inputs
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitExportWithModules();
        testVisitExportWithoutModules();
        testVisitExportNullModules();
    }

    static void testVisitExportWithModules() {
        ASMifier asmifier = new ASMifier();
        StringBuilder buf = new StringBuilder();
        String[] modules = {"module1", "module2"};
        asmifier.visitExport("com/example", 0x0010, modules);
        String output = asmifier.buf.toString();
        if (!output.contains("com/example") || !output.contains("module1") || !output.contains("module2")) {
            throw new AssertionError("visitExport with modules failed: " + output);
        }
    }

    static void testVisitExportWithoutModules() {
        ASMifier asmifier = new ASMifier();
        StringBuilder buf = new StringBuilder();
        asmifier.visitExport("com/test", 0x0020);
        String output = asmifier.buf.toString();
        if (!output.contains("com/test") || output.contains("new String[]")) {
            throw new AssertionError("visitExport without modules failed: " + output);
        }
    }

    static void testVisitExportNullModules() {
        ASMifier asmifier = new ASMifier();
        StringBuilder buf = new StringBuilder();
        asmifier.visitExport("com/null", 0x0040, null);
        String output = asmifier.buf.toString();
        if (!output.contains("com/null") || output.contains("new String[]")) {
            throw new AssertionError("visitExport with null modules failed: " + output);
        }
    }
}

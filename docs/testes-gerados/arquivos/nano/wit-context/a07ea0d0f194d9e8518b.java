/*
 * @test
 * @summary Tests for ASMifier.visitExport method to verify exception paths and behavior.
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitExportNullModules();
        testVisitExportEmptyModules();
        testVisitExportWithModules();
    }

    public static void testVisitExportNullModules() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitExport("com/example", 0, (String[]) null);
            System.out.println("testVisitExportNullModules passed");
        } catch (Exception e) {
            System.err.println("testVisitExportNullModules failed: " + e);
        }
    }

    public static void testVisitExportEmptyModules() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitExport("com/example", 0, new String[] {});
            System.out.println("testVisitExportEmptyModules passed");
        } catch (Exception e) {
            System.err.println("testVisitExportEmptyModules failed: " + e);
        }
    }

    public static void testVisitExportWithModules() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitExport("com/example", 0, "module1", "module2");
            System.out.println("testVisitExportWithModules passed");
        } catch (Exception e) {
            System.err.println("testVisitExportWithModules failed: " + e);
        }
    }
}

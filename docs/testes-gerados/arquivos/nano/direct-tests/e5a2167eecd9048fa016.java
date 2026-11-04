/* @test    @summary Test visitExport method of Printer class    @run main PrinterWitupTest */

import java.util.Arrays;
import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    public static void main(String[] args) {
        testVisitExportWithMultipleModules();
        testVisitExportWithEmptyModules();
        testVisitExportWithNullModules();
    }

    static void testVisitExportWithMultipleModules() {
        Printer printer = new Printer() {
            @Override
            public void visitExport(String packaze, int access, String... modules) {
                System.out.println("visitExport called with packaze: " + packaze + ", access: " + access + ", modules: " + Arrays.toString(modules));
            }
        };
        String packaze = "com.example.package";
        int access = 0x0001;
        String[] modules = {"moduleA", "moduleB"};
        printer.visitExport(packaze, access, modules);
    }

    static void testVisitExportWithEmptyModules() {
        Printer printer = new Printer() {
            @Override
            public void visitExport(String packaze, int access, String... modules) {
                System.out.println("visitExport with empty modules: " + Arrays.toString(modules));
            }
        };
        String packaze = "com.example.empty";
        int access = 0x0002;
        String[] modules = {};
        printer.visitExport(packaze, access, modules);
    }

    static void testVisitExportWithNullModules() {
        Printer printer = new Printer() {
            @Override
            public void visitExport(String packaze, int access, String... modules) {
                System.out.println("visitExport with null modules: " + (modules == null ? "null" : Arrays.toString(modules)));
            }
        };
        String packaze = "com.example.null";
        int access = 0x0004;
        String[] modules = null;
        try {
            printer.visitExport(packaze, access, modules);
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException as expected when modules is null");
        }
    }
}

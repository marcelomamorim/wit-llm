/*
 * @test
 * @summary Checks ASMifier.visitExport output for module exports with and without target modules
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testExportWithoutModules();
        testExportWithSingleModule();
        testExportWithMultipleModules();
        testExportWithNullModules();
        testExportWithEmptyModules();
    }

    static String render(ASMifier asm) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asm.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testExportWithoutModules() {
        ASMifier asm = new ASMifier();
        asm.visitExport("com/example/pkg", 0);
        String out = render(asm);
        check(out.contains("mdv.visitExport("), "missing visitExport call: " + out);
        check(out.contains("\"com/example/pkg\""), "missing package name: " + out);
        check(!out.contains("new String[]"), "unexpected modules array: " + out);
    }

    static void testExportWithSingleModule() {
        ASMifier asm = new ASMifier();
        asm.visitExport("com/example/pkg", 0, "mod.a");
        String out = render(asm);
        check(out.contains("mdv.visitExport("), "missing visitExport call: " + out);
        check(out.contains("new String[] {"), "missing modules array: " + out);
        check(out.contains("\"mod.a\""), "missing module name: " + out);
    }

    static void testExportWithMultipleModules() {
        ASMifier asm = new ASMifier();
        asm.visitExport("com/example/pkg", 0, "mod.a", "mod.b");
        String out = render(asm);
        check(out.contains("new String[] {"), "missing modules array: " + out);
        check(out.contains("\"mod.a\""), "missing first module: " + out);
        check(out.contains("\"mod.b\""), "missing second module: " + out);
        check(out.indexOf("\"mod.a\"") < out.indexOf("\"mod.b\""),
              "module order not preserved: " + out);
    }

    static void testExportWithNullModules() {
        ASMifier asm = new ASMifier();
        asm.visitExport("com/example/pkg", 0, (String[]) null);
        String out = render(asm);
        check(out.contains("mdv.visitExport("), "missing visitExport call: " + out);
        check(!out.contains("new String[]"), "unexpected modules array for null: " + out);
    }

    static void testExportWithEmptyModules() {
        ASMifier asm = new ASMifier();
        asm.visitExport("com/example/pkg", 0, new String[0]);
        String out = render(asm);
        check(out.contains("mdv.visitExport("), "missing visitExport call: " + out);
        check(!out.contains("new String[]"), "unexpected modules array for empty: " + out);
    }
}

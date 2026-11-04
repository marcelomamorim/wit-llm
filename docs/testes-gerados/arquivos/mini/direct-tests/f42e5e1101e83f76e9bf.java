/*
 * @test
 * @summary Test jdk.internal.org.objectweb.asm.util.Textifier.visitOpen method behavior
 * @run main TextifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisitOpenWithNullModules();
        testVisitOpenWithEmptyModules();
        testVisitOpenWithOneModule();
        testVisitOpenWithMultipleModules();
    }

    private static void testVisitOpenWithNullModules() {
        Textifier t = new Textifier();
        t.visitOpen("com.example.package", 0x1, (String[]) null);
        String result = t.getText().get(t.getText().size() - 1);
        if (!result.contains("opens com.example.package;") || !result.contains("0x1")) {
            throw new AssertionError("visitOpen with null modules failed: " + result);
        }
    }

    private static void testVisitOpenWithEmptyModules() {
        Textifier t = new Textifier();
        t.visitOpen("com.example.package", 0x2);
        String result = t.getText().get(t.getText().size() - 1);
        if (!result.contains("opens com.example.package;") || !result.contains("0x2")) {
            throw new AssertionError("visitOpen with empty modules failed: " + result);
        }
        if (result.contains("to")) {
            throw new AssertionError("visitOpen with empty modules should not contain 'to': " + result);
        }
    }

    private static void testVisitOpenWithOneModule() {
        Textifier t = new Textifier();
        t.visitOpen("com.example.package", 0x4, "moduleA");
        String result = t.getText().get(t.getText().size() - 1);
        if (!result.contains("opens com.example.package to") || !result.contains("moduleA") || !result.contains("0x4")) {
            throw new AssertionError("visitOpen with one module failed: " + result);
        }
        if (!result.trim().endsWith("moduleA;")) {
            throw new AssertionError("visitOpen with one module should end with moduleA; : " + result);
        }
    }

    private static void testVisitOpenWithMultipleModules() {
        Textifier t = new Textifier();
        String[] modules = {"moduleA", "moduleB", "moduleC"};
        t.visitOpen("com.example.package", 0x8, modules);
        String result = t.getText().get(t.getText().size() - 1);
        if (!result.contains("opens com.example.package to") || !result.contains("moduleA") || !result.contains("moduleB") || !result.contains("moduleC") || !result.contains("0x8")) {
            throw new AssertionError("visitOpen with multiple modules failed: " + result);
        }
        if (!result.contains("moduleA,\n") || !result.contains("moduleB,\n") || !result.contains("moduleC;\n")) {
            throw new AssertionError("visitOpen modules formatting incorrect: " + result);
        }
    }
}

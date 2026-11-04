/*
 * @test
 * @summary Verify Textifier.visitOpen output for open directives with and without target modules
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testOpenWithoutModules();
        testOpenWithNullModules();
        testOpenWithMultipleModules();
        testOpenWithSingleModule();
    }

    static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOpenWithoutModules() {
        Textifier t = new Textifier();
        t.visitOpen("p", 0x20);
        String s = dump(t);
        check(s.contains("opens p;"), "missing 'opens p;' in: " + s);
        check(!s.contains(" to"), "unexpected ' to' without modules in: " + s);
        check(s.contains("access flags 0x20"), "missing access flags 0x20 in: " + s);
    }

    static void testOpenWithNullModules() {
        Textifier t = new Textifier();
        t.visitOpen("q", 0, (String[]) null);
        String s = dump(t);
        check(s.contains("opens q;"), "missing 'opens q;' in: " + s);
        check(s.contains("access flags 0x0"), "missing access flags 0x0 in: " + s);
    }

    static void testOpenWithMultipleModules() {
        Textifier t = new Textifier();
        t.visitOpen("p", 0x8000, "m1", "m2");
        String s = dump(t);
        check(s.contains("opens p to"), "missing 'opens p to' in: " + s);
        check(s.contains("m1,"), "missing 'm1,' in: " + s);
        check(s.contains("m2;"), "missing 'm2;' in: " + s);
        check(s.contains("access flags 0x8000"), "missing access flags 0x8000 in: " + s);
    }

    static void testOpenWithSingleModule() {
        Textifier t = new Textifier();
        t.visitOpen("p", 1, "m1");
        String s = dump(t);
        check(s.contains("opens p to"), "missing 'opens p to' in: " + s);
        check(s.contains("m1;"), "missing 'm1;' in: " + s);
        check(s.contains("access flags 0x1"), "missing access flags 0x1 in: " + s);
    }
}

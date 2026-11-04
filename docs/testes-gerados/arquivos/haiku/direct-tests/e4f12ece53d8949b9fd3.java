/*
 * @test
 * @summary Verifies that ASMifier.visitMaxs appends the generated maxs call to the printer text
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.util.List;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest extends ASMifier {

    public static void main(String[] args) {
        testSingleCall();
        testNegativeValues();
        testAccumulatesEntries();
        testLargeValues();
    }

    private static void testSingleCall() {
        ASMifierWitupTest t = new ASMifierWitupTest();
        t.visitMaxs(2, 3);
        List<Object> text = t.text;
        check(text.size() == 1, "expected one entry, got " + text.size());
        Object entry = text.get(0);
        check(entry instanceof String, "entry must be a String");
        String s = (String) entry;
        check(s.endsWith(".visitMaxs(2, 3);\n"), "unexpected output: " + s);
    }

    private static void testNegativeValues() {
        ASMifierWitupTest t = new ASMifierWitupTest();
        t.visitMaxs(-1, 0);
        check(t.text.size() == 1, "expected one entry");
        String s = (String) t.text.get(0);
        check(s.endsWith(".visitMaxs(-1, 0);\n"), "unexpected output: " + s);
    }

    private static void testAccumulatesEntries() {
        ASMifierWitupTest t = new ASMifierWitupTest();
        t.visitMaxs(1, 1);
        t.visitMaxs(4, 5);
        check(t.text.size() == 2, "expected two entries, got " + t.text.size());
        String second = (String) t.text.get(1);
        check(second.endsWith(".visitMaxs(4, 5);\n"), "unexpected second output: " + second);
    }

    private static void testLargeValues() {
        ASMifierWitupTest t = new ASMifierWitupTest();
        t.visitMaxs(Integer.MAX_VALUE, Integer.MIN_VALUE);
        check(t.text.size() == 1, "expected one entry");
        String s = (String) t.text.get(0);
        check(s.endsWith(".visitMaxs(" + Integer.MAX_VALUE + ", " + Integer.MIN_VALUE + ");\n"),
              "unexpected output: " + s);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

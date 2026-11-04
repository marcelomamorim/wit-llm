/*
 * @test
 * @summary Verifies that Textifier.visitIincInsn appends a textual IINC line with the variable index and increment
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.util.List;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    static class TestTextifier extends Textifier {
        TestTextifier() {
            super();
        }

        List<Object> lines() {
            return text;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TestTextifier t = new TestTextifier();

        t.visitIincInsn(1, 2);
        check(t.lines().size() == 1, "expected one appended line");
        Object first = t.lines().get(0);
        check(first instanceof String, "appended entry must be a String");
        String s1 = (String) first;
        check(s1.endsWith("IINC 1 2\n"), "unexpected IINC line: " + s1);
        check(s1.contains("IINC "), "missing IINC mnemonic: " + s1);

        t.visitIincInsn(-3, 0);
        check(t.lines().size() == 2, "expected two appended lines");
        String s2 = (String) t.lines().get(1);
        check(s2.endsWith("IINC -3 0\n"), "unexpected negative IINC line: " + s2);
        check(!s2.contains("IINC 1 2"), "buffer must be reset between calls: " + s2);

        t.visitIincInsn(0, -1);
        check(t.lines().size() == 3, "expected three appended lines");
        String s3 = (String) t.lines().get(2);
        check(s3.endsWith("IINC 0 -1\n"), "unexpected zero-index IINC line: " + s3);
    }
}

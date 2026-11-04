/*
 * @test
 * @summary Checks that Textifier.visitUse emits a well-formed "uses" line for each call and resets its buffer between calls
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitUse("java/lang/String");
        t.visitUse("a/B");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        String out = sw.toString();

        check(count(out, "uses java/lang/String;") == 1,
              "expected exactly one 'uses java/lang/String;' line, output was: " + out);
        check(count(out, "uses a/B;") == 1,
              "expected exactly one 'uses a/B;' line, output was: " + out);
        check(count(out, "uses java/lang/String;" + "uses") == 0,
              "buffer was not reset between visitUse calls, output was: " + out);
    }

    static int count(String haystack, String needle) {
        int n = 0;
        int idx = 0;
        while ((idx = haystack.indexOf(needle, idx)) >= 0) {
            n++;
            idx += needle.length();
        }
        return n;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

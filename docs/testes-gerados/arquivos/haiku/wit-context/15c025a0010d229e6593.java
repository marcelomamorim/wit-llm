/*
 * @test
 * @summary Checks that ASMifier.visitFieldEnd() appends a visitEnd statement observable through the public print(PrintWriter) method and resets its buffer between calls
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testSingleCallEmitsVisitEnd();
        testRepeatedCallsDoNotConcatenateStatements();
    }

    static void testSingleCallEmitsVisitEnd() {
        ASMifier a = new ASMifier();
        a.visitFieldEnd();
        String out = dump(a);
        if (!out.contains(".visitEnd();")) {
            throw new AssertionError("visitEnd statement missing: " + out);
        }
        if (out.indexOf(".visitEnd();") != out.lastIndexOf(".visitEnd();")) {
            throw new AssertionError("unexpected duplicated visitEnd after one call: " + out);
        }
    }

    static void testRepeatedCallsDoNotConcatenateStatements() {
        ASMifier a = new ASMifier();
        a.visitFieldEnd();
        a.visitFieldEnd();
        String out = dump(a);
        int count = countOccurrences(out, ".visitEnd();");
        if (count != 2) {
            throw new AssertionError("expected 2 visitEnd statements but found " + count + ": " + out);
        }
        String[] lines = out.split("\n");
        for (String line : lines) {
            if (countOccurrences(line, ".visitEnd();") > 1) {
                throw new AssertionError("statements concatenated on one line: " + line);
            }
        }
    }

    static String dump(ASMifier a) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        a.print(pw);
        pw.flush();
        return sw.toString();
    }

    static int countOccurrences(String s, String sub) {
        int count = 0;
        int idx = 0;
        while ((idx = s.indexOf(sub, idx)) >= 0) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}

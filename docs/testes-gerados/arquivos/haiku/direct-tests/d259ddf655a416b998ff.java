/*
 * @test
 * @summary Checks that Textifier.visitMaxs records MAXSTACK and MAXLOCALS in its printed output
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitMaxs(3, 5);
        String out = dump(t);
        check(out.contains("MAXSTACK = 3"), out);
        check(out.contains("MAXLOCALS = 5"), out);
        check(out.indexOf("MAXSTACK") < out.indexOf("MAXLOCALS"), out);

        Textifier t2 = new Textifier();
        t2.visitMaxs(-1, 0);
        String out2 = dump(t2);
        check(out2.contains("MAXSTACK = -1"), out2);
        check(out2.contains("MAXLOCALS = 0"), out2);

        Textifier t3 = new Textifier();
        t3.visitMaxs(1, 1);
        t3.visitMaxs(7, 9);
        String out3 = dump(t3);
        check(out3.contains("MAXSTACK = 1"), out3);
        check(out3.contains("MAXSTACK = 7"), out3);
        check(out3.contains("MAXLOCALS = 9"), out3);
    }

    static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Unexpected output: " + msg);
        }
    }
}

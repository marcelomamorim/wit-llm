/*
 * @test
 * @summary Verifies Textifier.visitLabel emits one line per call and assigns stable label names
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    private static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    public static void main(String[] args) {
        Textifier t = new Textifier();
        Label a = new Label();
        Label b = new Label();
        t.visitLabel(a);
        t.visitLabel(a);
        t.visitLabel(b);
        String out = dump(t);
        String[] lines = out.split("\n");
        if (lines.length != 3) {
            throw new RuntimeException("Expected 3 lines but got " + lines.length + ": " + out);
        }
        if (!lines[0].trim().equals("L0")) {
            throw new RuntimeException("First label mismatch: " + lines[0]);
        }
        if (!lines[1].trim().equals("L0")) {
            throw new RuntimeException("Repeated label must reuse name: " + lines[1]);
        }
        if (!lines[2].trim().equals("L1")) {
            throw new RuntimeException("Second distinct label mismatch: " + lines[2]);
        }
    }
}

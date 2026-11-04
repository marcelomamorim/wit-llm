/*
 * @test
 * @summary Verifies Textifier.visitAnnotation output for visible and invisible annotations
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        visibleAnnotationReturnsNewTextifier();
        visibleAnnotationOutput();
        invisibleAnnotationOutput();
    }

    static void visibleAnnotationReturnsNewTextifier() {
        Textifier tf = new Textifier();
        Textifier ret = tf.visitAnnotation("Ljava/lang/Deprecated;", true);
        if (ret == null) {
            throw new RuntimeException("visitAnnotation returned null");
        }
        if (ret == tf) {
            throw new RuntimeException("visitAnnotation should return a new Textifier");
        }
    }

    static void visibleAnnotationOutput() {
        Textifier tf = new Textifier();
        tf.visitAnnotation("Ljava/lang/Deprecated;", true);
        String out = dump(tf);
        check(out.contains("@"), "missing '@' in: " + out);
        check(out.contains("Deprecated"), "missing descriptor in: " + out);
        check(out.contains("("), "missing '(' in: " + out);
        check(out.contains(")\n"), "missing closing ')' line in: " + out);
        check(!out.contains("invisible"), "visible annotation marked invisible: " + out);
    }

    static void invisibleAnnotationOutput() {
        Textifier tf = new Textifier();
        tf.visitAnnotation("Ljava/lang/Deprecated;", false);
        String out = dump(tf);
        check(out.contains("@"), "missing '@' in: " + out);
        check(out.contains("// invisible"), "missing '// invisible' in: " + out);
    }

    static String dump(Textifier tf) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tf.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}

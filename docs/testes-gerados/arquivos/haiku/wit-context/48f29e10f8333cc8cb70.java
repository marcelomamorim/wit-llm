/*
 * @test
 * @summary Basic observable behavior of Textifier.visitAnnotation
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testVisibleAnnotationReturnsNonNullTextifier();
        testVisibleAnnotationOutput();
        testInvisibleAnnotationOutput();
        testReturnedTextifierIsDistinct();
    }

    static void testVisibleAnnotationReturnsNonNullTextifier() {
        Textifier t = new Textifier();
        Textifier nested = t.visitAnnotation("Ljava/lang/Deprecated;", true);
        if (nested == null) {
            throw new AssertionError("visitAnnotation returned null");
        }
    }

    static void testVisibleAnnotationOutput() {
        Textifier t = new Textifier();
        t.visitAnnotation("Ljava/lang/Deprecated;", true);
        String out = render(t);
        if (!out.contains("@Ljava/lang/Deprecated;(")) {
            throw new AssertionError("missing annotation header: " + out);
        }
        if (out.contains("invisible")) {
            throw new AssertionError("visible annotation marked invisible: " + out);
        }
    }

    static void testInvisibleAnnotationOutput() {
        Textifier t = new Textifier();
        t.visitAnnotation("Ljava/lang/Deprecated;", false);
        String out = render(t);
        if (!out.contains("@Ljava/lang/Deprecated;(")) {
            throw new AssertionError("missing annotation header: " + out);
        }
        if (!out.contains(") // invisible")) {
            throw new AssertionError("invisible annotation not marked: " + out);
        }
    }

    static void testReturnedTextifierIsDistinct() {
        Textifier t = new Textifier();
        Textifier nested = t.visitAnnotation("Ljava/lang/Deprecated;", true);
        if (nested == t) {
            throw new AssertionError("visitAnnotation returned the receiver");
        }
    }

    static String render(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }
}

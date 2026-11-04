/*
 * @test
 * @summary Verifies Textifier.visitMainClass output observed through the public print(PrintWriter) method
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        check("com/example/Main");
        check("");
        check(null);

        Textifier t = new Textifier();
        t.visitMainClass("first/A");
        t.visitMainClass("second/B");
        String out = render(t);
        if (!out.contains("  // main class first/A")) {
            throw new RuntimeException("missing first entry: " + out);
        }
        if (!out.contains("  // main class second/B")) {
            throw new RuntimeException("missing second entry: " + out);
        }
        if (out.indexOf("first/A") != out.lastIndexOf("first/A")) {
            throw new RuntimeException("first entry duplicated: " + out);
        }
    }

    static void check(String name) {
        Textifier t = new Textifier();
        t.visitMainClass(name);
        String expectedName = (name == null) ? "null" : name;
        String expected = "  // main class " + expectedName;
        String out = render(t);
        if (!out.contains(expected)) {
            throw new RuntimeException("expected " + expected + " in: " + out);
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

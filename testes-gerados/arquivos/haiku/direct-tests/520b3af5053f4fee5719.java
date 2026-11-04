/*
 * @test
 * @summary Verify Textifier.visitParameter output for named, unnamed, access-flagged and ordered parameters
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

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

    public static void main(String[] args) {
        testNamedParameter();
        testNullNameParameter();
        testFinalAccessParameter();
        testParameterOrderPreserved();
    }

    static void testNamedParameter() {
        Textifier t = new Textifier();
        t.visitParameter("arg0", 0);
        String out = dump(t);
        check(out.contains("// parameter"), "missing parameter comment: " + out);
        check(out.contains(" arg0"), "missing parameter name: " + out);
    }

    static void testNullNameParameter() {
        Textifier t = new Textifier();
        t.visitParameter(null, 0);
        String out = dump(t);
        check(out.contains("<no name>"), "null name not rendered as <no name>: " + out);
    }

    static void testFinalAccessParameter() {
        Textifier t = new Textifier();
        t.visitParameter("p", 0x0010);
        String out = dump(t);
        check(out.contains("// parameter"), "missing parameter comment: " + out);
        check(out.contains("final"), "ACC_FINAL not rendered: " + out);
        check(out.contains(" p"), "missing parameter name: " + out);
    }

    static void testParameterOrderPreserved() {
        Textifier t = new Textifier();
        t.visitParameter("first", 0);
        t.visitParameter("second", 0);
        String out = dump(t);
        int i1 = out.indexOf("first");
        int i2 = out.indexOf("second");
        check(i1 >= 0 && i2 >= 0, "missing names: " + out);
        check(i1 < i2, "parameters not in visit order: " + out);
    }
}

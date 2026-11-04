/*
 * @test
 * @summary Verifies ASMifier.visitAttribute emits the attribute comment and handles non-ASMifiable and null attributes
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) throws Exception {
        testSingleAttributeEmitsComment();
        testNonAsmifiableAttributeEmitsNoVisitCall();
        testAttributesAccumulateInOrder();
        testNullAttributeThrowsNullPointerException();
    }

    static Attribute attr(String type) {
        return new Attribute(type) {};
    }

    static String print(ASMifier v) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        v.print(pw);
        pw.flush();
        return sw.toString();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSingleAttributeEmitsComment() {
        ASMifier v = new ASMifier();
        v.visitAttribute(attr("Foo"));
        String out = print(v);
        check(out.contains("// ATTRIBUTE Foo"), "missing attribute comment: " + out);
    }

    static void testNonAsmifiableAttributeEmitsNoVisitCall() {
        ASMifier v = new ASMifier();
        v.visitAttribute(attr("Bar"));
        String out = print(v);
        check(out.contains("// ATTRIBUTE Bar"), "missing comment for Bar: " + out);
        check(!out.contains("visitAttribute(attr)"),
              "unexpected visitAttribute call for non-ASMifiable attribute: " + out);
    }

    static void testAttributesAccumulateInOrder() {
        ASMifier v = new ASMifier();
        v.visitAttribute(attr("A"));
        v.visitAttribute(attr("B"));
        String out = print(v);
        int ia = out.indexOf("// ATTRIBUTE A");
        int ib = out.indexOf("// ATTRIBUTE B");
        check(ia >= 0, "missing A: " + out);
        check(ib >= 0, "missing B: " + out);
        check(ia < ib, "attributes not in visit order: " + out);
    }

    static void testNullAttributeThrowsNullPointerException() {
        ASMifier v = new ASMifier();
        try {
            v.visitAttribute(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null attribute");
    }
}

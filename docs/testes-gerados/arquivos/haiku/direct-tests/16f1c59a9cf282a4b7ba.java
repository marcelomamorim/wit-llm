/*
 * @test
 * @summary Verifies that ASMifier.visitUse appends the generated visitUse call to its printed output
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) throws Exception {
        singleUse();
        multipleUsesAreAppendedInOrder();
    }

    static void singleUse() {
        ASMifier asm = new ASMifier();
        asm.visitUse("java.foo.Service");
        String out = print(asm);
        String expected = "mdv.visitUse(\"java.foo.Service\");\n";
        if (!expected.equals(out)) {
            throw new AssertionError("expected [" + expected + "] but was [" + out + "]");
        }
    }

    static void multipleUsesAreAppendedInOrder() {
        ASMifier asm = new ASMifier();
        asm.visitUse("a.A");
        asm.visitUse("b.B");
        String out = print(asm);
        String expected = "mdv.visitUse(\"a.A\");\nmdv.visitUse(\"b.B\");\n";
        if (!expected.equals(out)) {
            throw new AssertionError("expected [" + expected + "] but was [" + out + "]");
        }
    }

    static String print(ASMifier asm) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        asm.print(pw);
        pw.flush();
        return sw.toString();
    }
}

/*
 * @test
 * @summary Verify ASMifier.visitMainClass appends the mdv.visitMainClass call to the printed output
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testSingleMainClass();
        testMultipleMainClassesKeepOrder();
    }

    private static String print(ASMifier a) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        a.print(pw);
        pw.flush();
        return sw.toString();
    }

    private static void testSingleMainClass() {
        ASMifier a = new ASMifier();
        a.visitMainClass("com/example/Main");
        String out = print(a);
        String expected = "mdv.visitMainClass(\"com/example/Main\");";
        if (!out.contains(expected)) {
            throw new RuntimeException("Missing line: " + expected + " in output: " + out);
        }
    }

    private static void testMultipleMainClassesKeepOrder() {
        ASMifier a = new ASMifier();
        a.visitMainClass("first/Main");
        a.visitMainClass("second/Main");
        String out = print(a);
        int i1 = out.indexOf("mdv.visitMainClass(\"first/Main\");");
        int i2 = out.indexOf("mdv.visitMainClass(\"second/Main\");");
        if (i1 < 0 || i2 < 0) {
            throw new RuntimeException("Missing visitMainClass lines in output: " + out);
        }
        if (i1 >= i2) {
            throw new RuntimeException("Lines out of order in output: " + out);
        }
    }
}

/*
 * @test
 * @summary Verifies Textifier.visitLdcInsn output for String, Type, Integer and null constants
 * @modules java.base/jdk.internal.org.objectweb.asm
 *          java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    static String ldc(Object cst) {
        Textifier t = new Textifier();
        t.visitLdcInsn(cst);
        StringWriter sw = new StringWriter();
        t.print(new PrintWriter(sw));
        return sw.toString();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        String s = ldc("abc");
        check(s.contains("LDC \"abc\""), "String constant: " + s);

        String ty = ldc(Type.getObjectType("java/lang/String"));
        check(ty.contains("LDC Ljava/lang/String;.class"), "Type constant: " + ty);

        String i = ldc(Integer.valueOf(42));
        check(i.contains("LDC 42"), "Integer constant: " + i);

        String n = ldc(null);
        check(n.contains("LDC null"), "null constant: " + n);

        Textifier shared = new Textifier();
        shared.visitLdcInsn(Integer.valueOf(1));
        shared.visitLdcInsn(Integer.valueOf(2));
        StringWriter sw = new StringWriter();
        shared.print(new PrintWriter(sw));
        String both = sw.toString();
        check(both.contains("LDC 1") && both.contains("LDC 2"), "Sequential calls: " + both);
    }
}

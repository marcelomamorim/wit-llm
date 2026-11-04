/*
 * @test
 * @summary Checks Textifier.visitRequire output for requires directives with access flags and version
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    private static String dump(Textifier t) {
        StringWriter sw = new StringWriter();
        t.print(new PrintWriter(sw));
        return sw.toString();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Textifier t1 = new Textifier();
        t1.visitRequire("java.base", Opcodes.ACC_TRANSITIVE | Opcodes.ACC_STATIC_PHASE, "11");
        String out1 = dump(t1);
        check(out1.contains("requires transitive static java.base;"), out1);
        check(out1.contains("access flags 0x60"), out1);
        check(out1.contains("// version 11"), out1);

        Textifier t2 = new Textifier();
        t2.visitRequire("java.util", 0, null);
        String out2 = dump(t2);
        check(out2.contains("requires java.util;"), out2);
        check(out2.contains("access flags 0x0"), out2);
        check(!out2.contains("transitive"), out2);
        check(!out2.contains("static "), out2);
        check(!out2.contains("// version"), out2);

        Textifier t3 = new Textifier();
        t3.visitRequire("foo.bar", Opcodes.ACC_TRANSITIVE, null);
        String out3 = dump(t3);
        check(out3.contains("requires transitive foo.bar;"), out3);
        check(out3.contains("access flags 0x20"), out3);
        check(!out3.contains("// version"), out3);

        Textifier t4 = new Textifier();
        t4.visitRequire("baz", Opcodes.ACC_STATIC_PHASE, "1.2");
        String out4 = dump(t4);
        check(out4.contains("requires static baz;"), out4);
        check(!out4.contains("transitive"), out4);
        check(out4.contains("access flags 0x40"), out4);
        check(out4.contains("// version 1.2"), out4);
    }
}

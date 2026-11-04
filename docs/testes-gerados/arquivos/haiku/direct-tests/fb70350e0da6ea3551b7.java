/*
 * @test
 * @summary Checks the ASMifier.visitRequire output for module requires with and without a version
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) throws Exception {
        ASMifier withVersion = new ASMifier();
        withVersion.visitRequire("java.base", 0, "17");
        String out1 = dump(withVersion);
        check(out1.contains("mdv.visitRequire(\"java.base\", "), "module name not emitted: " + out1);
        check(out1.contains("\"17\");"), "version not emitted: " + out1);

        ASMifier withNullVersion = new ASMifier();
        withNullVersion.visitRequire("java.desktop", 0, null);
        String out2 = dump(withNullVersion);
        check(out2.contains("mdv.visitRequire(\"java.desktop\", "), "module name not emitted: " + out2);
        check(out2.contains("null);"), "null version not emitted as null: " + out2);
        check(!out2.contains("\"null\""), "null version emitted as string: " + out2);
    }

    private static String dump(ASMifier a) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        a.print(pw);
        pw.flush();
        return sw.toString();
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}

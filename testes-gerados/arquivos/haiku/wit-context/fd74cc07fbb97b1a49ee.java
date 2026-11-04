/*
 * @test
 * @summary Smoke test for Textifier.visitRequire with valid, null and boundary inputs
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {
    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitRequire("java.base", 0, null);
        t.visitRequire("java.base", 0x0020, "11");
        t.visitRequire("m.x", 0x0040 | 0x0020, "1.0");
        t.visitRequire("", -1, null);
        t.visitRequire(null, 0, null);
        t.visitRequire("a.b", 0x7fffffff, "");
        for (int i = 0; i < 3; i++) {
            t.visitRequire("repeat", 0, "v" + i);
        }
    }
}

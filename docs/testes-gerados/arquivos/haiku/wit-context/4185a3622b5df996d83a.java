/*
 * @test
 * @summary Checks that Textifier.visitSource completes without exception for null, empty, ASCII and non-ASCII inputs, including repeated and large inputs
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {
    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitSource(null, null);
        t.visitSource("A.java", null);
        t.visitSource(null, "debug");
        t.visitSource("A.java", "debug");
        t.visitSource("", "");
        t.visitSource("ção.java", "éè");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append('x');
        }
        String big = sb.toString();
        t.visitSource(big, big);
        t.visitSource(null, null);
        t.visitSource("B.java", "second");
        Textifier t2 = new Textifier();
        t2.visitSource(null, "only-debug");
        t2.visitSource("C.java", null);
    }
}

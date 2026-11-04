/*
 * @test
 * @summary Regression checks for Textifier.visitParameter with null, empty and regular names and several access flags
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {
    public static void main(String[] args) {
        Textifier t = new Textifier();
        t.visitParameter("arg0", 0);
        t.visitParameter(null, 0x0010);
        t.visitParameter("", 0x0001);
        t.visitParameter("second", 0x0001 | 0x0010);
        t.visitParameter("third", 0);
        Textifier t2 = new Textifier();
        t2.visitParameter("a", 0);
        t2.visitParameter("b", 0x0001);
    }
}
/*
 * @test
 * @summary Smoke test for ASMifier.visitMethodEnd on fresh and reused instances
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier first = new ASMifier();
        first.visitMethodEnd();
        first.visitMethodEnd();

        ASMifier second = new ASMifier();
        second.visitMethodEnd();
    }
}

/*
 * @test
 * @summary Basic observable behavior of ASMifier.visitAnnotationDefault
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier visitor = new ASMifier();
        ASMifier av = visitor.visitAnnotationDefault();
        if (av == null) {
            throw new RuntimeException("visitAnnotationDefault returned null");
        }
        if (av == visitor) {
            throw new RuntimeException("visitAnnotationDefault returned the receiver");
        }
        ASMifier av2 = visitor.visitAnnotationDefault();
        if (av2 == null) {
            throw new RuntimeException("second call returned null");
        }
        ASMifier nested = av.visitAnnotationDefault();
        if (nested == null) {
            throw new RuntimeException("nested call returned null");
        }
    }
}

/*
 * @test
 * @summary Checks that ASMifier.visitMaxs completes normally for boundary and repeated inputs
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier asm = new ASMifier();
        asm.visitMaxs(0, 0);
        asm.visitMaxs(1, 2);
        asm.visitMaxs(-1, Integer.MAX_VALUE);
        asm.visitMaxs(Integer.MIN_VALUE, Integer.MIN_VALUE);
        asm.visitMaxs(Integer.MAX_VALUE, Integer.MAX_VALUE);

        ASMifier second = new ASMifier();
        second.visitMaxs(3, 4);
        second.visitMaxs(3, 4);
        second.visitMaxs(0, 0);
    }
}

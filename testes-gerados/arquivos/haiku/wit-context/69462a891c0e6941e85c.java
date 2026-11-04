/*
 * @test
 * @summary Checks that ASMifier.visitArray returns a new non-null ASMifier visitor for each call
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier printer = new ASMifier();

        ASMifier av = printer.visitArray("values");
        if (av == null) {
            throw new AssertionError("visitArray returned null");
        }
        if (av == printer) {
            throw new AssertionError("visitArray returned the receiver instead of a new visitor");
        }

        ASMifier second = printer.visitArray("other");
        if (second == null) {
            throw new AssertionError("second visitArray call returned null");
        }
        if (second == av) {
            throw new AssertionError("consecutive visitArray calls returned the same visitor");
        }

        ASMifier nested = av.visitArray("inner");
        if (nested == null) {
            throw new AssertionError("nested visitArray returned null");
        }
        if (nested == av) {
            throw new AssertionError("nested visitArray returned its receiver");
        }
    }
}

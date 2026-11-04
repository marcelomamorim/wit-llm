/*
 * @test
 * @summary Verifies that ASMifier.visitMethodAttribute accepts method attributes without failing
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier visitor = new ASMifier();
        visitor.visitMethodAttribute(new Attribute("Dummy") {});
        visitor.visitMethodAttribute(new Attribute("Other") {});
        ASMifier another = new ASMifier();
        another.visitMethodAttribute(new Attribute("Dummy") {});
    }
}

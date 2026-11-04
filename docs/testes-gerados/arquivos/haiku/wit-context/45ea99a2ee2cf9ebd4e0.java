/*
 * @test
 * @summary Smoke test for ASMifier.visitMethodAttribute delegating to visitAttribute without throwing for a valid attribute
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier printer = new ASMifier();
        Attribute attr = new Attribute("WitupTestAttr") {};
        printer.visitMethodAttribute(attr);
        if (printer == null) {
            throw new AssertionError("printer must not be null");
        }
    }
}

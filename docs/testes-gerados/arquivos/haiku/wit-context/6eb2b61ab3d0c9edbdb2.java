/*
 * @test
 * @summary Smoke test for ASMifier.visitFieldAttribute with non-null attributes
 * @modules java.base/jdk.internal.org.objectweb.asm java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier printer = new ASMifier();
        Attribute first = new Attribute("WitupFirst") { };
        Attribute second = new Attribute("WitupSecond") { };
        printer.visitFieldAttribute(first);
        printer.visitFieldAttribute(second);
        printer.visitFieldAttribute(first);
    }
}

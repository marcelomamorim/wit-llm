/*
 * @test
 * @summary Tests for ASMifier.visitFieldAttribute method behavior.
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitFieldAttributeWithNull();
        testVisitFieldAttributeWithValidAttribute();
    }

    public static void testVisitFieldAttributeWithNull() {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitFieldAttribute(null);
        } catch (NullPointerException e) {
            System.out.println("Passed null attribute test");
            return;
        }
        System.err.println("Failed null attribute test: no exception thrown");
        System.exit(1);
    }

    public static void testVisitFieldAttributeWithValidAttribute() {
        ASMifier asmifier = new ASMifier();
        Attribute attr = new Attribute("TestAttribute") {};
        try {
            asmifier.visitFieldAttribute(attr);
            System.out.println("Passed valid attribute test");
        } catch (Exception e) {
            System.err.println("Failed valid attribute test: " + e);
            System.exit(1);
        }
    }
}

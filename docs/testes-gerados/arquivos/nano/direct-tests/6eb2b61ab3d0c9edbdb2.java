/*
 * @test
 * @summary Test ASMifier.visitFieldAttribute method for correct invocation and behavior
 * @run main ASMifierWitupTest
 */

import java.io.PrintWriter;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitFieldAttributeWithNull();
        testVisitFieldAttributeWithMockAttribute();
    }

    static void testVisitFieldAttributeWithNull() {
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

    static void testVisitFieldAttributeWithMockAttribute() {
        Attribute mockAttr = new Attribute("Mock") {
            @Override
            public void write(PrintWriter pw) {
                pw.println("Mock attribute");
            }
        };
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitFieldAttribute(mockAttr);
            System.out.println("Passed mock attribute test");
        } catch (Exception e) {
            System.err.println("Failed mock attribute test: " + e);
            System.exit(1);
        }
    }
}

/*
 * @test
 * @summary Checks that Textifier.visitFieldAttribute records the attribute through the public print(PrintWriter) output
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) throws Exception {
        testSingleFieldAttributeIsPrinted();
        testMultipleFieldAttributesAreAllPrinted();
    }

    private static String printOf(Textifier t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.print(pw);
        pw.flush();
        return sw.toString();
    }

    private static void testSingleFieldAttributeIsPrinted() {
        Textifier t = new Textifier();
        Attribute attr = new Attribute("WitupFieldAttr") { };
        t.visitFieldAttribute(attr);
        String out = printOf(t);
        if (!out.contains("ATTRIBUTE") || !out.contains("WitupFieldAttr")) {
            throw new RuntimeException("Field attribute not printed: " + out);
        }
    }

    private static void testMultipleFieldAttributesAreAllPrinted() {
        Textifier t = new Textifier();
        t.visitFieldAttribute(new Attribute("FirstWitupAttr") { });
        t.visitFieldAttribute(new Attribute("SecondWitupAttr") { });
        String out = printOf(t);
        if (!out.contains("FirstWitupAttr") || !out.contains("SecondWitupAttr")) {
            throw new RuntimeException("Not all field attributes printed: " + out);
        }
        if (out.indexOf("FirstWitupAttr") > out.indexOf("SecondWitupAttr")) {
            throw new RuntimeException("Field attributes printed out of order: " + out);
        }
    }
}

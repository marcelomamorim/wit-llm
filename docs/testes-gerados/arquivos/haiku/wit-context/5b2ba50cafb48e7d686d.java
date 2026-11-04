/*
 * @test
 * @summary Checks public visitArray behavior of Textifier for named and unnamed arrays
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main TextifierWitupTest
 */

import java.util.List;

import jdk.internal.org.objectweb.asm.util.Textifier;

public class TextifierWitupTest {

    public static void main(String[] args) {
        testNamedArrayReturnsChild();
        testUnnamedArrayReturnsChild();
        testNamedArrayRecordsOpeningText();
        testRepeatedCallsReturnDistinctChildren();
    }

    static void testNamedArrayReturnsChild() {
        Textifier tx = new Textifier();
        Textifier child = tx.visitArray("arr");
        if (child == null) {
            throw new AssertionError("visitArray(name) returned null");
        }
    }

    static void testUnnamedArrayReturnsChild() {
        Textifier tx = new Textifier();
        Textifier child = tx.visitArray(null);
        if (child == null) {
            throw new AssertionError("visitArray(null) returned null");
        }
    }

    static void testNamedArrayRecordsOpeningText() {
        Textifier tx = new Textifier();
        tx.visitArray("arr");
        List<?> text = tx.getText();
        boolean found = false;
        for (Object o : text) {
            if ("arr={".equals(o)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new AssertionError("expected element \"arr={\" in text: " + text);
        }
    }

    static void testRepeatedCallsReturnDistinctChildren() {
        Textifier tx = new Textifier();
        Textifier a = tx.visitArray("a");
        Textifier b = tx.visitArray("b");
        if (a == null || b == null) {
            throw new AssertionError("visitArray returned null on repeated call");
        }
        if (a == b) {
            throw new AssertionError("repeated visitArray returned the same child");
        }
    }
}

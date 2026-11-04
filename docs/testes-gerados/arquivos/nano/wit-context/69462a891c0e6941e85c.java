/*
 * @test
 * @summary Test for ASMifier.visitArray method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitArrayWithValidName();
        testVisitArrayWithNullName();
        testVisitArrayWithEmptyName();
    }

    private static void testVisitArrayWithValidName() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitArray("TestArray");
            if (result == null) {
                throw new AssertionError("Expected non-null result for valid name");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid name", e);
        }
    }

    private static void testVisitArrayWithNullName() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitArray(null);
            if (result == null) {
                throw new AssertionError("Expected non-null result for null name");
            }
        } catch (NullPointerException e) {
            // Acceptable if method throws NullPointerException
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null name", e);
        }
    }

    private static void testVisitArrayWithEmptyName() {
        ASMifier asmifier = new ASMifier();
        try {
            ASMifier result = asmifier.visitArray("");
            if (result == null) {
                throw new AssertionError("Expected non-null result for empty name");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty name", e);
        }
    }
}

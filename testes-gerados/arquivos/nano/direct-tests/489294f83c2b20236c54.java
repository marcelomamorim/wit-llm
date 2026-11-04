/* @test    @summary Test the append method of Norm2AllModes for correct concatenation behavior.    @run main Norm2AllModesWitupTest */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testAppendWithNonEmptyBuilders();
        testAppendWithEmptyFirstBuilder();
        testAppendWithEmptySecondSequence();
        testAppendWithNullSequence();
    }

    private static void testAppendWithNonEmptyBuilders() {
        StringBuilder sb1 = new StringBuilder("Hello ");
        StringBuilder sb2 = new StringBuilder("World");
        StringBuilder result = Norm2AllModes.append(sb1, sb2);
        assert result == sb1 : "Result should be the first StringBuilder";
        assert sb1.toString().equals("Hello World") : "Concatenation failed";
    }

    private static void testAppendWithEmptyFirstBuilder() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("Test");
        StringBuilder result = Norm2AllModes.append(sb1, sb2);
        assert result == sb1 : "Result should be the first StringBuilder";
        assert sb1.toString().equals("Test") : "Concatenation with empty first builder failed";
    }

    private static void testAppendWithEmptySecondSequence() {
        StringBuilder sb1 = new StringBuilder("Data");
        StringBuilder sb2 = new StringBuilder();
        StringBuilder result = Norm2AllModes.append(sb1, sb2);
        assert result == sb1 : "Result should be the first StringBuilder";
        assert sb1.toString().equals("Data") : "Concatenation with empty second sequence failed";
    }

    private static void testAppendWithNullSequence() {
        StringBuilder sb1 = new StringBuilder("NullTest");
        try {
            Norm2AllModes.append(sb1, null);
            assert false : "Expected NullPointerException when second is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}

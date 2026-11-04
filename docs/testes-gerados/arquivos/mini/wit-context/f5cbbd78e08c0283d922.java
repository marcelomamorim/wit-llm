/*
 * @test
 * @summary Test java.lang.Byte.valueOf(String s) for NumberFormatException on null input and normal parsing
 * @run main ByteWitupTest
 */

public class ByteWitupTest {
    public static void main(String[] args) {
        testValueOfNull();
        testValueOfValidString();
        testValueOfInvalidString();
    }

    private static void testValueOfNull() {
        try {
            java.lang.Byte.valueOf((String) null);
            throw new RuntimeException("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testValueOfValidString() {
        java.lang.Byte b = java.lang.Byte.valueOf("127");
        if (b.byteValue() != 127) {
            throw new RuntimeException("Expected 127 but got " + b);
        }
        b = java.lang.Byte.valueOf("-128");
        if (b.byteValue() != -128) {
            throw new RuntimeException("Expected -128 but got " + b);
        }
        b = java.lang.Byte.valueOf("0");
        if (b.byteValue() != 0) {
            throw new RuntimeException("Expected 0 but got " + b);
        }
    }

    private static void testValueOfInvalidString() {
        String[] invalidInputs = {"128", "-129", "abc", "", " ", "12.3"};
        for (String s : invalidInputs) {
            try {
                java.lang.Byte.valueOf(s);
                throw new RuntimeException("Expected NumberFormatException for input: '" + s + "'");
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}

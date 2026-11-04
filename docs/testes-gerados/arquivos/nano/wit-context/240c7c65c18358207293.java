/*
 * @test
 * @summary Tests for StringUTF16.codePointCountSB to verify behavior with invalid indices.
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testCodePointCountSBWithInvalidIndices();
    }

    private static void testCodePointCountSBWithInvalidIndices() {
        byte[] validBytes = new byte[10];
        int validBegin = 0;
        int validEnd = 5;
        int length = validBytes.length;
        
        // Caso 1: beginIndex > endIndex
        try {
            StringUTF16.codePointCountSB(validBytes, 3, 2);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for beginIndex > endIndex");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Exception for beginIndex > endIndex");
        }
        
        // Caso 2: endIndex > length do array
        try {
            StringUTF16.codePointCountSB(validBytes, 0, length + 1);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for endIndex > length");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Exception for endIndex > length");
        }
        
        // Caso 3: beginIndex < 0
        try {
            StringUTF16.codePointCountSB(validBytes, -1, 3);
            System.out.println("Failed: Expected StringIndexOutOfBoundsException for beginIndex < 0");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Passed: Exception for beginIndex < 0");
        }
        
        // Caso 4: beginIndex == endIndex (não deve lançar exceção, resultado esperado é 0)
        try {
            int result = StringUTF16.codePointCountSB(validBytes, 2, 2);
            if (result == 0) {
                System.out.println("Passed: No exception for beginIndex == endIndex, result=0");
            } else {
                System.out.println("Failed: Unexpected result for beginIndex == endIndex: " + result);
            }
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for beginIndex == endIndex");
        }
    }
}

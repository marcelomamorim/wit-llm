/*
 * @test
 * @summary Testa o método insert de StringBuilder com float, verificando exceções de índice.
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithInvalidOffset();
        testInsertWithValidOffset();
    }

    private static void testInsertWithInvalidOffset() {
        StringBuilder sb = new StringBuilder("abc");
        float value = 1.23f;
        int invalidOffset = -1; // menor que 0
        try {
            sb.insert(invalidOffset, value);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for offset " + invalidOffset);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for offset " + invalidOffset);
        }
        invalidOffset = 4; // maior que length
        try {
            sb.insert(invalidOffset, value);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for offset " + invalidOffset);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for offset " + invalidOffset);
        }
    }

    private static void testInsertWithValidOffset() {
        StringBuilder sb = new StringBuilder("abc");
        float value = 1.23f;
        int validOffset = 1; // dentro do intervalo
        try {
            StringBuilder result = sb.insert(validOffset, value);
            if (result != sb) {
                System.out.println("FAIL: insert should return the same StringBuilder instance");
            } else {
                System.out.println("PASS: insert with valid offset succeeded");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid offset: " + e);
        }
    }
}

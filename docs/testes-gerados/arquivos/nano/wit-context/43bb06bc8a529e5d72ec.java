/*
 * @test
 * @summary Test for java.util.Scanner.remove()
 * @run main ScannerWitupTest
 */

import java.util.*;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        Scanner scanner = new Scanner("sample input");
        try {
            scanner.remove();
            // Se nenhuma exceção for lançada, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}

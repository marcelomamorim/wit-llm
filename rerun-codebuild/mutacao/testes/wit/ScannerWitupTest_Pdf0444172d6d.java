/*
 * @test
 * @summary Test for java.util.Scanner.remove()
 * @run main ScannerWitupTest_Pdf0444172d6d
 */

import java.util.*;

public class ScannerWitupTest_Pdf0444172d6d {

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

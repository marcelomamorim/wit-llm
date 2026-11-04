/* @test    @summary Test for setValue method in ConcurrentHashMap to verify exception throwing behavior    @run main ConcurrentHashMapWitupTest */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            // Invoca o método setValue, que deve lançar UnsupportedOperationException
            map.setValue("test");
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Exceção esperada, o teste passa
        }
    }
}

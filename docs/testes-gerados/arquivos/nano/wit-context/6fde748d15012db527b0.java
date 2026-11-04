/*
 * @test    @summary Test for Collections.computeIfAbsent to verify behavior and exception handling.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Function;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testComputeIfAbsentThrowsException();
    }

    public static void testComputeIfAbsentThrowsException() {
        try {
            // Invoca o método com um lambda que não é usado, pois o método lança exceção imediatamente.
            Collections.computeIfAbsent(null, k -> "value");
            // Se não lançar exceção, o teste falha.
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Teste bem-sucedido: a exceção esperada foi lançada.
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}

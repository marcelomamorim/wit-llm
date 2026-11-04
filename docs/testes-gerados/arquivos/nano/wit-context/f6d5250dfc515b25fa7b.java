/*
 * @test
 * @summary Tests for java.util.stream.ReduceOps class methods
 * @run main ReduceOpsWitupTest
 */

import java.util.stream.ReduceOps;
import java.util.function.LongBinaryOperator;

public class ReduceOpsWitupTest {

    public static void main(String[] args) {
        testMakeLongWithNullOperator();
    }

    public static void testMakeLongWithNullOperator() {
        long identity = 0L;
        LongBinaryOperator operator = null;
        try {
            ReduceOps.makeLong(identity, operator);
            // Se não lançar exceção, falha o teste
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Teste passou, exceção esperada
        }
    }
}

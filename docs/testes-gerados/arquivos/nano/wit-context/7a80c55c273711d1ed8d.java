/*
 * @test
 * @summary Testa o construtor Properties(int initialCapacity) com valores válidos e inválidos.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testValidInitialCapacity();
        testNegativeInitialCapacity();
        testZeroInitialCapacity();
    }

    static void testValidInitialCapacity() {
        try {
            Properties p = new Properties(10);
            if (p == null) {
                throw new AssertionError("Properties instance should not be null for valid capacity");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid capacity: " + e);
        }
    }

    static void testNegativeInitialCapacity() {
        try {
            Properties p = new Properties(-5);
            // Espera-se que uma IllegalArgumentException seja lançada
            throw new AssertionError("Expected IllegalArgumentException for negative capacity, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testZeroInitialCapacity() {
        try {
            Properties p = new Properties(0);
            if (p == null) {
                throw new AssertionError("Properties instance should not be null for zero capacity");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero capacity: " + e);
        }
    }
}

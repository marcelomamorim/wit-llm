/* @test
   @summary Testa o método setDSTSavings para valores positivos e negativos.
   @run main SimpleTimeZoneWitupTest_Pea54d74f5a9f
 */
import java.util.SimpleTimeZone;
public class SimpleTimeZoneWitupTest_Pea54d74f5a9f {
    public static void main(String[] args) {
        testSetDSTSavingsPositive();
        testSetDSTSavingsZero();
        testSetDSTSavingsNegative();
    }
    private static void testSetDSTSavingsPositive() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        tz.setDSTSavings(3600000); // 1 hora
        // Verifica se o valor foi atualizado corretamente
        if (tz.getDSTSavings() != 3600000) {
            throw new AssertionError("Failed: valor esperado 3600000, mas foi " + tz.getDSTSavings());
        }
    }
    private static void testSetDSTSavingsZero() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        try {
            tz.setDSTSavings(0);
            throw new AssertionError("Expected IllegalArgumentException for zero value");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
    private static void testSetDSTSavingsNegative() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "UTC");
        try {
            tz.setDSTSavings(-1000);
            throw new AssertionError("Expected IllegalArgumentException para valor negativo");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}

/* @test
   @summary Testa o método get de java.time.Period para diferentes unidades temporais.
   @run main PeriodWitupTest_P876d2ae699d9
 */
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;

public class PeriodWitupTest_P876d2ae699d9 {
    public static void main(String[] args) {
        testGetYears();
        testGetMonths();
        testGetDays();
        testGetUnsupportedUnit();
    }

    private static void testGetYears() {
        Period p = Period.of(2, 3, 4);
        long result = p.get(ChronoUnit.YEARS);
        if (result != 2) {
            throw new AssertionError("Expected 2, got " + result);
        }
    }

    private static void testGetMonths() {
        Period p = Period.of(2, 3, 4);
        long result = p.get(ChronoUnit.MONTHS);
        if (result != 3) {
            throw new AssertionError("Expected 3, got " + result);
        }
    }

    private static void testGetDays() {
        Period p = Period.of(2, 3, 4);
        long result = p.get(ChronoUnit.DAYS);
        if (result != 4) {
            throw new AssertionError("Expected 4, got " + result);
        }
    }

    private static void testGetUnsupportedUnit() {
        Period p = Period.of(1, 2, 3);
        try {
            p.get(ChronoUnit.HOURS);
            throw new AssertionError("Expected UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            // esperado
        }
    }
}

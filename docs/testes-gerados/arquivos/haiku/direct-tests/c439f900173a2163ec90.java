/*
 * @test
 * @summary Verifies Instant.now(Clock) returns the clock instant and rejects a null clock
 * @run main InstantWitupTest
 */

public class InstantWitupTest {

    public static void main(String[] args) throws Exception {
        Instant fixed = Instant.ofEpochSecond(1500000000L, 123456789);
        Clock fixedClock = Clock.fixed(fixed, ZoneOffset.UTC);
        check(fixed.equals(Instant.now(fixedClock)), "Instant.now must return the fixed clock instant");

        Clock offsetClock = Clock.offset(fixedClock, Duration.ofSeconds(90));
        Instant expectedOffset = fixed.plusSeconds(90);
        check(expectedOffset.equals(Instant.now(offsetClock)), "Instant.now must reflect the clock offset");

        Instant epoch = Instant.now(Clock.fixed(Instant.EPOCH, ZoneOffset.UTC));
        check(Instant.EPOCH.equals(epoch), "Instant.now must return EPOCH for an EPOCH clock");

        boolean npe = false;
        try {
            Instant.now(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "Instant.now(null) must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}

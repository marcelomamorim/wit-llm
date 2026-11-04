/*
 * @test
 * @summary Verifies that ZoneRulesProvider.registerProvider rejects a null provider with NullPointerException
 * @run main ZoneRulesProviderWitupTest
 */

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        try {
            java.time.zone.ZoneRulesProvider.registerProvider(null);
            throw new AssertionError("NullPointerException expected for null provider");
        } catch (NullPointerException e) {
            if (!"provider".equals(e.getMessage())) {
                throw new AssertionError("Unexpected NPE message: " + e.getMessage());
            }
        }
    }
}

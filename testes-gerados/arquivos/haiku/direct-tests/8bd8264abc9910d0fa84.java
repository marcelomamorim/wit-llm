/*
 * @test
 * @summary registerProvider rejects a null provider with NullPointerException whose message is "provider"
 * @run main ZoneRulesProviderWitupTest
 */

import java.time.zone.ZoneRulesProvider;

public class ZoneRulesProviderWitupTest {

    public static void main(String[] args) {
        try {
            ZoneRulesProvider.registerProvider(null);
            throw new RuntimeException("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            if (!"provider".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage(), e);
            }
        }
    }
}

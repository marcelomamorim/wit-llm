/*
 * @test
 * @summary Witup-generated tests for jdk.internal.module.Checks.requireServiceProviderName
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */
import jdk.internal.module.Checks;

public class ChecksWitupTest {
    public static void main(String[] args) {
        String valid = "p.q.Provider";
        if (!valid.equals(Checks.requireServiceProviderName(valid))) {
            throw new RuntimeException("valid provider name was not returned unchanged");
        }
        expectIllegalArgument("p..Provider");
        expectIllegalArgument("1abc.Provider");
        expectIllegalArgument(null);
    }

    static void expectIllegalArgument(String name) {
        try {
            Checks.requireServiceProviderName(name);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for: " + name);
    }
}

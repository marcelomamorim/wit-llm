/*
 * @test
 * @summary Locale.getDisplayName(null) must throw NullPointerException
 * @run main LocaleWitupTest
 */
public class LocaleWitupTest {
    public static void main(String[] args) {
        Locale locale = new Locale("en", "US");
        boolean thrown = false;
        try {
            locale.getDisplayName(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("NullPointerException expected for null inLocale");
        }
    }
}

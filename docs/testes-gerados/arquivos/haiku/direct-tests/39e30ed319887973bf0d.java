/*
 * @test
 * @summary ResourceBundle.getCandidateLocales returns candidate locales and rejects null baseName or locale
 * @run main ResourceBundleWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        ResourceBundle rb = new ResourceBundle();

        List<Locale> us = rb.getCandidateLocales("base", Locale.US);
        check(us.size() == 3, "US candidates size expected 3 but was " + us.size());
        check(Locale.US.equals(us.get(0)), "first US candidate should be en_US");
        check(Locale.ENGLISH.equals(us.get(1)), "second US candidate should be en");
        check(Locale.ROOT.equals(us.get(2)), "last US candidate should be ROOT");

        List<Locale> root = rb.getCandidateLocales("base", Locale.ROOT);
        check(root.size() == 1, "ROOT candidates size expected 1 but was " + root.size());
        check(Locale.ROOT.equals(root.get(0)), "ROOT candidate should be ROOT");

        us.clear();
        List<Locale> again = rb.getCandidateLocales("base", Locale.US);
        check(again.size() == 3, "returned list should be a fresh copy after external modification");

        expectNpe(() -> rb.getCandidateLocales(null, Locale.US), "null baseName should throw NullPointerException");
        expectNpe(() -> rb.getCandidateLocales("base", null), "null locale should throw NullPointerException");
    }

    private static void expectNpe(Runnable r, String msg) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(msg);
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}

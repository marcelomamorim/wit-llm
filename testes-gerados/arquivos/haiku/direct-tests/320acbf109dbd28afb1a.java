/*
 * @test
 * @summary Verifies Subject.getPrincipals().addAll return values, duplicate handling and null collection rejection
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static final class P implements Principal {
        private final String name;
        P(String name) { this.name = name; }
        public String getName() { return name; }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Subject s = new Subject();
        Set<Principal> ps = s.getPrincipals();

        P a = new P("a");
        P b = new P("b");
        List<Principal> items = new ArrayList<>();
        items.add(a);
        items.add(b);

        check(ps.addAll(items), "addAll of new principals must return true");
        check(ps.size() == 2, "size must be 2 after adding two principals");
        check(ps.contains(a) && ps.contains(b), "both principals must be present");

        check(!ps.addAll(items), "addAll of already present principals must return false");
        check(ps.size() == 2, "size must remain 2 after duplicate addAll");

        check(!ps.addAll(new ArrayList<Principal>()), "addAll of empty collection must return false");

        boolean npe = false;
        try {
            ps.addAll(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "addAll(null) must throw NullPointerException");
    }
}

/*
 * @test
 * @summary Verifies Subject.getPrincipals(Class) filtering and null-argument rejection
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.HashSet;
import java.util.Collections;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static class UserP implements Principal {
        private final String name;
        UserP(String name) { this.name = name; }
        public String getName() { return name; }
    }

    static class SubUserP extends UserP {
        SubUserP(String name) { super(name); }
    }

    static class GroupP implements Principal {
        private final String name;
        GroupP(String name) { this.name = name; }
        public String getName() { return name; }
    }

    static class OtherP implements Principal {
        public String getName() { return "other"; }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Set<Principal> ps = new HashSet<>();
        ps.add(new UserP("alice"));
        ps.add(new GroupP("staff"));
        ps.add(new SubUserP("bob"));

        Subject s = new Subject(false, ps, Collections.emptySet(), Collections.emptySet());

        Set<Principal> all = s.getPrincipals(Principal.class);
        check(all != null, "getPrincipals(Principal.class) returned null");
        check(all.size() == 3, "expected 3 principals, got " + all.size());

        Set<UserP> users = s.getPrincipals(UserP.class);
        check(users.size() == 2, "expected 2 UserP (including subclass), got " + users.size());

        Set<SubUserP> subUsers = s.getPrincipals(SubUserP.class);
        check(subUsers.size() == 1, "expected 1 SubUserP, got " + subUsers.size());

        Set<GroupP> groups = s.getPrincipals(GroupP.class);
        check(groups.size() == 1, "expected 1 GroupP, got " + groups.size());

        Set<OtherP> others = s.getPrincipals(OtherP.class);
        check(others != null, "getPrincipals(OtherP.class) returned null");
        check(others.isEmpty(), "expected empty set for unmatched class");

        Subject empty = new Subject();
        check(empty.getPrincipals(UserP.class).isEmpty(), "empty subject should yield no UserP");

        boolean npe = false;
        try {
            s.getPrincipals(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "getPrincipals(null) must throw NullPointerException");
    }
}

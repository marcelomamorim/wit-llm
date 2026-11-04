/*
 * @test
 * @summary Verifies Subject set view remove(Object) for principals and credentials, including null handling
 * @run main SubjectWitupTest
 */

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;

public class SubjectWitupTest {

    static final class P implements Principal {
        private final String name;

        P(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean equals(Object o) {
            if (!(o instanceof P)) {
                return false;
            }
            return ((P) o).name.equals(name);
        }

        public int hashCode() {
            return name.hashCode();
        }

        public String toString() {
            return name;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        P alice = new P("alice");
        P bob = new P("bob");
        Set<Principal> ps = new HashSet<>();
        ps.add(alice);
        ps.add(bob);
        Subject s = new Subject(false, ps, new HashSet<Object>(), new HashSet<Object>());

        check(s.getPrincipals().size() == 2, "initial principal count");
        check(s.getPrincipals().remove(alice), "remove existing principal should return true");
        check(!s.getPrincipals().contains(alice), "removed principal should be absent");
        check(!s.getPrincipals().remove(alice), "removing again should return false");
        check(s.getPrincipals().size() == 1, "size after removal");
        check(s.getPrincipals().remove(new P("bob")), "remove should use equals()");
        check(s.getPrincipals().isEmpty(), "principals should be empty");

        s.getPublicCredentials().add("cert");
        check(!s.getPublicCredentials().remove("missing"), "absent public credential should return false");
        check(s.getPublicCredentials().remove("cert"), "remove existing public credential should return true");
        check(s.getPublicCredentials().isEmpty(), "public credentials should be empty");

        s.getPrivateCredentials().add("key");
        check(s.getPrivateCredentials().remove("key"), "remove existing private credential should return true");
        check(!s.getPrivateCredentials().remove("key"), "removing private credential again should return false");

        boolean npe = false;
        try {
            s.getPrincipals().remove(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "remove(null) should throw NullPointerException");
    }
}

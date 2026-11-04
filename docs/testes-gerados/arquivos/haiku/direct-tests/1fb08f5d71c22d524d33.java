/*
 * @test
 * @summary Smoke test for the public Policy API reachable without a SecurityManager
 * @run main PolicyWitupTest
 */

import java.security.Policy;

public class PolicyWitupTest {
    public static void main(String[] args) throws Exception {
        Policy policy = Policy.getPolicy();
        if (policy == null) {
            throw new RuntimeException("Policy.getPolicy() returned null");
        }
    }
}

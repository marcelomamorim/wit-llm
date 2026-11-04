/* @test    @summary Test UniqueIdentity constructor with DerInputStream for exception paths    @run main UniqueIdentityWitupTest */

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.UniqueIdentity;

public class UniqueIdentityWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorIOExceptionPath1();
        testConstructorIOExceptionPath2();
        testConstructorIOExceptionPath3();
    }

    private static void testConstructorIOExceptionPath1() {
        DerInputStream dis = new DerInputStream() {
            @Override
            public DerValue getDerValue() throws IOException {
                throw new IOException("Simulated IOException in getDerValue");
            }
        };
        try {
            new UniqueIdentity(dis);
            System.out.println("Path 1: Failed - exception not thrown");
        } catch (IOException e) {
            System.out.println("Path 1: Passed - caught expected IOException: " + e.getMessage());
        }
    }

    private static void testConstructorIOExceptionPath2() {
        DerInputStream dis = new DerInputStream() {
            private int callCount = 0;
            @Override
            public DerValue getDerValue() throws IOException {
                callCount++;
                if (callCount == 1) {
                    return new DerValue();
                } else {
                    throw new IOException("Simulated IOException in getDerValue after first call");
                }
            }
        };
        try {
            new UniqueIdentity(dis);
            System.out.println("Path 2: Failed - exception not thrown");
        } catch (IOException e) {
            System.out.println("Path 2: Passed - caught expected IOException: " + e.getMessage());
        }
    }

    private static void testConstructorIOExceptionPath3() {
        DerInputStream dis = new DerInputStream() {
            private int callCount = 0;
            @Override
            public DerValue getDerValue() throws IOException {
                callCount++;
                if (callCount == 1) {
                    return new DerValue();
                } else {
                    throw new IOException("Simulated IOException in getDerValue after first call");
                }
            }
        };
        try {
            new UniqueIdentity(dis);
            System.out.println("Path 3: Failed - exception not thrown");
        } catch (IOException e) {
            System.out.println("Path 3: Passed - caught expected IOException: " + e.getMessage());
        }
    }
}

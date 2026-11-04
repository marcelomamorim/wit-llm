/*
 * @test
 * @summary Checks public Date(String) constructor behavior: parsing, consistency with Date.parse and null handling
 * @run main DateWitupTest
 */

import java.util.Date;

public class DateWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Date d1 = new Date("Jan 1 2000");
        Date e1 = new Date(100, 0, 1);
        check(d1.getTime() == e1.getTime(),
              "Date(\"Jan 1 2000\") should match Date(100,0,1)");

        Date d2 = new Date("Jan 1 2000 10:20:30");
        Date e2 = new Date(100, 0, 1, 10, 20, 30);
        check(d2.getTime() == e2.getTime(),
              "Date with time fields should match Date(100,0,1,10,20,30)");

        check(new Date("Jan 1 2000").getTime() == Date.parse("Jan 1 2000"),
              "Date(String) should be consistent with Date.parse(String)");

        check(new Date("Jan 1 2000 10:20:30").getTime()
                == Date.parse("Jan 1 2000 10:20:30"),
              "Date(String) with time should be consistent with Date.parse");

        boolean npe = false;
        try {
            new Date((String) null);
        } catch (NullPointerException ex) {
            npe = true;
        }
        check(npe, "Date((String) null) should throw NullPointerException");
    }
}

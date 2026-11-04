/* @test    @summary Test default constructor of IntSummaryStatistics    @run main IntSummaryStatisticsWitupTest */

import java.util.IntSummaryStatistics;

public class IntSummaryStatisticsWitupTest {
    public static void main(String[] args) {
        IntSummaryStatistics stats = new IntSummaryStatistics();
        assert stats.getCount() == 0 : "Count should be zero";
        assert stats.getSum() == 0 : "Sum should be zero";
        assert stats.getMin() == Integer.MAX_VALUE : "Min should be Integer.MAX_VALUE";
        assert stats.getMax() == Integer.MIN_VALUE : "Max should be Integer.MIN_VALUE";
        System.out.println("All assertions passed.");
    }
}

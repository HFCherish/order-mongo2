import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CountTime {
    public int sum(int[] timeSlice) {
        int sum  = 0;
        for(int i: timeSlice)
            sum += timeSlice[i];
        return sum;
    }

    @Test
    public void countSum() {
        int[] expected = new int[]{5, 3, 8, 8, 10,2,4,3,1,2,4,2,4,4,2,6,9,2,5, 4,1,8,3,5,11,1,5,10,6,4,3,19,3,3,1,2,12,2,2,7,2,3, 10, 6,7,4,1};
        int[] real = new int[]{5,1,11,5,11,4,5,3,1,2,4,2,4,4,2,5,12,2,4,2,1,7,2,6,11,1,3,22,5,11,12,7,3,1,1,8,2,1,6,2,4,8,4,5,3,4};

        assertThat(sum(real), is(sum(expected)));
    }
}

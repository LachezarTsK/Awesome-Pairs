
import java.util.Map;
import java.util.HashMap;

public class Solution {

    /*
    Map Keys: log2 of the array values.
    Map Values: number of occurences for each key.
     */
    public Map<Integer, Integer> map_log2ArrayValue_to_frequency = new HashMap<Integer, Integer>();

    /*
    By the problem design on geeksforgeeks.org, we have to work
    around the given method 'long pairs(int N, int arr[])'.
    The method name is left as it is, so that the code can be run directly 
    on the website, without any modifications.
     */
    public long pairs(int N, int arr[]) {
        calculate_log2ArrayValue(arr);
        return countPairs();
    }

    /*
    Due to the strict time limits, the approach of simply iterating through
    the array, doing the bitwise AND, bitwise XOR and then
    comparing the values, will exceed by far the time limit.
    
    So, the approach applied here is as follows: 
    if the bitwise AND is to be strictly larger than the bitwsie XOR 
    for two values, then these values have to have their leading bit 
    at the same position in their binary representation.
    
    Therefore, in order to calculate which pairs have bitwise AND 
    strictly larger than bitwise XOR, we have to find 
    the position of leading bit for each array value and record 
    the number of occurences for this position.
    
    The position of the leading bit can be found
    by calculating 'Math.floor(log2_arrayValue)' 
    or casting it to integer value '(int)log2_arrayValue'.
    
    To utilize the built-in Java Math Library, which does 
    not have a logarithm of base 2, we will apply the following
    properties of logarithms: logA_B / logA_C = logC_B. 
    
    Thus, we obtain the log2_arrayValue by calculating 
    log10_arrayValue / log10_2 = log2_arrayValue.
    And then update their respective frequency in the map.
    
    With this approach, the solution is well below the stipulated
    time limit of 1,79 seconds: it takes all test cases below 
    half this time, appriximately 0,72 seconds.
     */
    public void calculate_log2ArrayValue(int[] arr) {

        double log10_2 = Math.log10(2);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                double logTenValue = Math.log10(arr[i]);
                int value = (int) (logTenValue / log10_2);
                fillMap(map_log2ArrayValue_to_frequency, value);
            }
        }

    }

    /*
    Counts all possible combinations for picking two integers 
    with the same leading digit, where the order of selection does not matter.
    
    @return A long integer, representing the maximum number of awesome paris.
     */
    public long countPairs() {

        long count = 0;
        for (int n : map_log2ArrayValue_to_frequency.keySet()) {
            int frequency = map_log2ArrayValue_to_frequency.get(n);
            long current = ((long) frequency * (frequency - 1)) / 2;
            count += current;
        }
        return count;
    }

    public void fillMap(Map<Integer, Integer> map, int value) {
        if (!map.containsKey(value)) {
            map.put(value, 1);
        } else {
            int freq = map.get(value) + 1;
            map.put(value, freq);
        }
    }
}

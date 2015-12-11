package ngordnet;
import java.util.*;

public class YearlyRecord {

    HashMap<String, Integer> yearlyRecordCount;
    TreeMap<String, Number> orderedRecord;

    int wordCount;
    boolean cached;



    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        /** stores qord followed by number of times used per year */
        yearlyRecordCount = new HashMap<String, Integer>();
        /** stores words and counts in ascending order of count */
        orderedRecord = new TreeMap<String, Number>(new yearComparator());
        wordCount = 0;
        cached = false;
    }

    private class yearComparator implements Comparator<String> {
        @Override
        public int compare(String x, String y) {

            if (orderedRecord.get(y).doubleValue() >= orderedRecord.get(x).doubleValue()) {
                return -1;

            } else {
                return 1;
            }
        }
    }



    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        yearlyRecordCount = otherCountMap;
        orderedRecord = new TreeMap<String, Number>(new yearComparator());
        orderedRecord.putAll(yearlyRecordCount);
    }


    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return yearlyRecordCount.get(word);
    }


    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        yearlyRecordCount.put(word, count);
        orderedRecord.put(word, count);
    }




    /** Returns the number of words recorded this year. */
    public int size() {
        return yearlyRecordCount.size();
    }


    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        return orderedRecord.keySet();
    }


    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        return orderedRecord.values();
    }



    /** Returns rank of WORD. Most common word is rank 1.
      * If two words have the same rank, break ties arbitrarily.
      * No two words should have the same rank.
      * Used subMap method which returns a view of the portion of this map
      * whose keys range from word to last key of orderedRecord.
      */
    public int rank(String word) {
        return orderedRecord.subMap(word, true, orderedRecord.lastKey(), true).size() + 1;
    }
}






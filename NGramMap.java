package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import edu.princeton.cs.introcs.In;

public class NGramMap {
    private TimeSeries<Long> tseries;
    private HashMap<String, TimeSeries<Integer>> wrdCount;
    private HashMap<Integer, YearlyRecord> yearMap;



    public NGramMap(String wrdsFileName, String countsFileName) {
        wrdCount = new HashMap<String, TimeSeries<Integer>>();
        tseries = new TimeSeries<Long>();
        yearMap = new HashMap<Integer, YearlyRecord>();
        In wrdsFile = new In(wrdsFileName);
        In countsFile = new In(countsFileName);

        while (wrdsFile.hasNextLine()) {
            String line = wrdsFile.readLine();
            String[] wrds = line.split("\t");
            String wrd = wrds[0];
            if (!wrdCount.containsKey(wrd)) {
                wrdCount.put(wrd.intern(), new TimeSeries<Integer>());
            }
            int year = Integer.parseInt(wrds[1]);
            int count = Integer.parseInt(wrds[2]);
            wrdCount.get(wrd).put(year, count);
            if (yearMap.containsKey(year)) {
                YearlyRecord yr = getRecord(year);
                yr.put(wrd.intern(), count);
                yearMap.put(year, yr);
            } else {
                YearlyRecord yr = new YearlyRecord();
                yr.put(wrd.intern(), count);
                yearMap.put(year, yr);
            }
        }
        while (countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            String[] wrds = line.split(",");
            int year = Integer.parseInt(wrds[0]);
            long count = Long.parseLong(wrds[1]);
            tseries.put(year, count);
        }
    }

    public boolean isInMap(String wrd) {
        return wrdCount.containsKey(wrd);
    }

    public TimeSeries<Integer> countHistory(String wrd) {
        return wrdCount.get(wrd);
    }

    public TimeSeries<Integer> countHistory(String wrd, int startYear, int endYear) {
        TimeSeries<Integer> result =
            new TimeSeries<Integer>(countHistory(wrd), startYear, endYear);
        return result;
    }

    public int countInYear(String wrd, int year) {
        return wrdCount.get(wrd).get(year);
    }

    public YearlyRecord getRecord(int year) {
        return yearMap.get(year);
    }

    public TimeSeries<Double> processedHistory(int startYear,
                                int endYear, YearlyRecordProcessor year1) {
        TimeSeries<Double> result = new TimeSeries<Double>();
        for (int year : yearMap.keySet()) {
            if (year <= endYear && year >= startYear) {
                result.put(year, year1.process(yearMap.get(year)));
            }
        }
        return result;
    }

    public TimeSeries<Double> processedHistory(YearlyRecordProcessor year1) {
        TimeSeries<Double> result = new TimeSeries<Double>();
        for (Integer year : yearMap.keySet()) {
            result.put(year, year1.process(yearMap.get(year)));
        }
        return result;
    }

    public TimeSeries<Double> summedWeightHistory(Collection<String> wrds) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        for (String wrd : wrds) {
            sum = sum.plus(weightHistory(wrd));
        }
        return sum;
    }

    public TimeSeries<Double> summedWeightHistory(Collection<String> wrds,
                                    int startYear, int endYear) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        for (String wrd : wrds) {
            if (isInMap(wrd)) {
                sum = sum.plus(weightHistory(wrd, startYear, endYear));
            }
        }
        return sum;
    }

    public TimeSeries<Long> totalCountHistory() {
        return tseries;
    }

    public TimeSeries<Double> weightHistory(String wrd) {
        if (!isInMap(wrd)) {
            return new TimeSeries<Double>();
        }
        TimeSeries<Integer> counts = countHistory(wrd);
        TimeSeries<Long> totals = totalCountHistory();
        return counts.dividedBy(totals);
    }

    public TimeSeries<Double> weightHistory(String wrd, int startYear, int endYear) {
        if (!isInMap(wrd)) {
            return new TimeSeries<Double>();
        }
        TimeSeries<Integer> counts = countHistory(wrd, startYear, endYear);
        TimeSeries<Long> totals = new TimeSeries<Long>(totalCountHistory(), startYear, endYear);
        return counts.dividedBy(totals);
    }
}

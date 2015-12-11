package ngordnet;
import java.util.*;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
      super();
    }


    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have
      * to implement it if you don't want to. */
    // private NavigableSet<Integer> validYears(int startYear, int endYear) {

    // }



    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR.
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
      super();

      for (int i : ts.keySet()) {
        if (i <= endYear && i >= startYear) {
          put(i, ts.get(i));
        }
      }
    }




    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
      putAll(ts);
    }



    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
      Collection<Number> val = years();
      TimeSeries<Double> result = new TimeSeries<Double>();

      for (Number n : val) {
        if (ts.containsKey(n)) {
          result.put(n.intValue(), (get(n).doubleValue() / ts.get(n).doubleValue()));
        } else {
          throw new IllegalArgumentException();
        }
      }

      return result;
    }



    /** Returns the sum of this time series with the given ts. The result is a
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
      TimeSeries<Double> result = new TimeSeries<Double>();

      for (Number n : years()) {
        if (ts.containsKey(n)) {
          result.put(n.intValue(), get(n).doubleValue() + ts.get(n).doubleValue());
        }
      }

      for (Number n : ts.years()) {
        if (!result.containsKey(n)) {
          result.put(n.intValue(), ts.get(n).doubleValue());
        }
      }

      return result;
    }



    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
      LinkedHashSet<Number> result = new LinkedHashSet<Number>();
      for (Integer i : keySet()) {
        Number j = i;
        result.add(j);
      }

      return result;
    }



    /** Returns all data for this time series.
      * Must be in the same order as years(). */
    public Collection<Number> data() {
      LinkedList<Number> result = new LinkedList<Number>();

      for (T i : values()) {
        Number n = (Number) i;
        result.add(n);
      }

      return result;
    }
}

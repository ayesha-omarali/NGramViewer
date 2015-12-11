package ngordnet;

public class WordLengthProcessor implements YearlyRecordProcessor {

    public double process(YearlyRecord year) {
        long total = 0;
        long count = 0; 
        for (String s : year.words()) {
            total = total + 1;
            count = count + s.length();
        }
        return count / total;
    }

    public String title() {
        return "Average Word Length";
    }

    public String xlabel() {
        return "Year";
    }

    public String ylabel() {
        return "Word Length";
    }

    public String legend() {
        return "Average";
    }


}
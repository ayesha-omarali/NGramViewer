package ngordnet;
import com.xeiam.xchart.QuickChart;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.SwingWrapper;
import com.xeiam.xchart.ChartBuilder;
import javax.swing.JFrame;
import java.util.Collection;
import java.util.Set;
import java.util.LinkedList;
import java.util.ArrayList;


public class Plotter {

    public static void plotTS(TimeSeries<? extends Number> timeSers, String title,
            String xlabel, String ylabel, String legend) {
        Collection years = timeSers.years();
        Collection counts = timeSers.data();

        Chart chart = QuickChart.getChart(title, ylabel, xlabel, legend, years, counts);
        new SwingWrapper(chart).displayChart();
    }

    public static void plotAllWords(NGramMap nGramMap, String[] words,
                                    int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600)
            .xAxisTitle("years").yAxisTitle("data").build();
        int seriesAdded = 0;
        for (String word : words) {
            if (nGramMap.isInMap(word)) {
                TimeSeries bundle = nGramMap.weightHistory(word, startYear, endYear);
                chart.addSeries(word, bundle.years(), bundle.data());
                seriesAdded = seriesAdded + 1;
            } else {
                if (!word.equals("")) {
                    System.out.println("No data for " + word);
                }
            }
        }
        if (seriesAdded != 0) {
            JFrame frame = new SwingWrapper(chart).displayChart();
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    }

    public static void plotProcessedHistory(NGramMap nGramMap, int startYear,
            int endYear, YearlyRecordProcessor yrp) {
        TimeSeries<Double> phist = nGramMap
                .processedHistory(startYear, endYear, yrp);
        plotTS(phist, "Length of Average Word vs. Year", "Year",
                "Length of Average Word", "Length of Average Word");
    }

    public static void plotCountHistory(NGramMap nGramMap, String word,
            int startYear, int endYear) {
        TimeSeries countHistory = nGramMap.countHistory(word, startYear, endYear);
        plotTS(countHistory, "Popularity", "year", "count", word);
    }



    public static void plotCategoryWeights(NGramMap nGramMap, WordNet wordNet,
                       String[] catLabels, int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600)
            .xAxisTitle("years").yAxisTitle("data").build();
        int seriesAdded = 0;
        for (String catLabel : catLabels) {
            if (wordNet.isNoun(catLabel)) {
                Set<String> words = wordNet.hyponyms(catLabel);
                TimeSeries<Double> bundle = nGramMap.summedWeightHistory(words, startYear, endYear);
                if (bundle.size() != 0) {
                    chart.addSeries(catLabel, bundle.years(), bundle.data());
                    seriesAdded = seriesAdded + 1;
                } else {
                    System.out.println("No data for " + catLabel);
                }
            } else if (!catLabel.equals(" ") && !catLabel.equals("")) {
                System.out.println("The word " + catLabel + " was not found.");
            }
        }
        if (seriesAdded != 0) {
            new SwingWrapper(chart).displayChart();
        }
    }


    public static void plotWeightHistory(NGramMap nGramMap, String word,
            int startYear, int endYear) {
        TimeSeries weightHistory = nGramMap.weightHistory(word, startYear, endYear);
        plotTS(weightHistory, "Popularity", "year", "weight", word);
    }


    public static void plotCategoryWeights(NGramMap nGramMap, WordNet wordNet,
            String catLabel, int startYear, int endYear) {
        Set words = wordNet.hyponyms(catLabel);
        TimeSeries summedWeightHistory = nGramMap.summedWeightHistory(words, startYear, endYear);
        plotTS(summedWeightHistory, "Popularity", "year", "weight", catLabel);
    }


    public static void plotZipfsLaw(NGramMap nGramMap, int year) {
        YearlyRecord yr = nGramMap.getRecord(year);
        Collection yearCounts = yr.counts();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for (int i = yearCounts.size(); i >=1; i --) {
          temp.add(i);
        }
        Collection yearRanks = temp;

        Chart chart = new ChartBuilder().width(8).height(6)
                .xAxisTitle("Rank").yAxisTitle("Count").build();
        chart.getStyleManager().setYAxisLogarithmic(true);
        chart.getStyleManager().setXAxisLogarithmic(true);

        chart.addSeries("Zipf's Law", yearRanks, yearCounts);
        new SwingWrapper(chart).displayChart();
    }


}

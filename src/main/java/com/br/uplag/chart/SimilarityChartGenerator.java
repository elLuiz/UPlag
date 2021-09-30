package com.br.uplag.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class SimilarityChartGenerator {
    private static final Logger LOGGER = Logger.getLogger(SimilarityChartGenerator.class.getSimpleName());
    private final String directory;

    public SimilarityChartGenerator(String directory) {
        this.directory = directory;
    }

    public void createBarChart(double[] similarity) {
        HistogramDataset histogramDataset = createDataSet(similarity);
        JFreeChart jFreeChartHistogram = createHistogram(histogramDataset);
        XYPlot xyPlot = (XYPlot) jFreeChartHistogram.getPlot();
        NumberAxis numberAxis = (NumberAxis) xyPlot.getDomainAxis();
        numberAxis.setRange(0.0, 100.0);
        numberAxis.setTickUnit(new NumberTickUnit(10.0));
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setTickUnit(new NumberTickUnit(1.0));
        shouldSaveChart(jFreeChartHistogram);
    }

    private HistogramDataset createDataSet(double[] similarity) {
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.addSeries("", similarity, 10);
        return histogramDataset;
    }

    private JFreeChart createHistogram(HistogramDataset histogramDataset) {
        return ChartFactory.createHistogram("Histogram",
                "Percentage", "Frequency", histogramDataset, PlotOrientation.VERTICAL, false, false, false);
    }

    private void shouldSaveChart(JFreeChart jFreeChartHistogram) {
        try {
            ChartUtils.saveChartAsPNG(new File(directory + "/histogram.png"), jFreeChartHistogram, 2500, 900);
        } catch (IOException ioException) {
            LOGGER.severe("Invalid directory");
            LOGGER.severe(ioException.getMessage());
        }
    }
}
package com.br.uplag.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
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
        shouldSaveChart(jFreeChartHistogram);
    }

    private HistogramDataset createDataSet(double[] similarity) {
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.addSeries("", similarity, 20);
        return histogramDataset;
    }

    private JFreeChart createHistogram(HistogramDataset histogramDataset) {
        return ChartFactory.createHistogram("Histogram",
                "Percentage", "Frequency", histogramDataset, PlotOrientation.VERTICAL, false, false, false);
    }

    private void shouldSaveChart(JFreeChart jFreeChartHistogram) {
        try {
            ChartUtilities.saveChartAsPNG(new File(directory + "/histogram.png"), jFreeChartHistogram, 700, 500);
        } catch (IOException ioException) {
            LOGGER.severe("Invalid directory");
            LOGGER.severe(ioException.getMessage());
        }
    }
}
package frontend;

import c.misc.StylizationRegex;

public class StylizationAnalyzer extends FrontEND{
    private static StylizationAnalyzer stylizationAnalyzer;
    private StylizationAnalyzer() {}

    public static StylizationAnalyzer getInstance() {
        if (stylizationAnalyzer == null)
            stylizationAnalyzer = new StylizationAnalyzer();
        return stylizationAnalyzer;
    }

    public String beautifyTokenization(String code, StylizationRegex stylizationRegex) {
        return compileMatcher(code, stylizationRegex.getRegex(), stylizationRegex.getToken());
    }
}

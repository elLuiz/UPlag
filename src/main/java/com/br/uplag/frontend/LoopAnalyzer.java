package com.br.uplag.frontend;

import com.br.uplag.c.loops.LoopRegex;

public class LoopAnalyzer extends FrontEND {
    private static LoopAnalyzer loopAnalyzer;
    private LoopAnalyzer() {}

    public static LoopAnalyzer getInstance() {
        if (loopAnalyzer == null)
            loopAnalyzer = new LoopAnalyzer();
        return loopAnalyzer;
    }

    public String tokenize(String codeText) {
        return compileMatcher(codeText, LoopRegex.LOOP_REGEX.getRegex(), LoopRegex.LOOP_REGEX.getToken());
    }
}

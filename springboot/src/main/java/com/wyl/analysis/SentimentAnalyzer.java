package com.wyl.analysis;

import java.util.HashMap;
import java.util.Map;

public class SentimentAnalyzer {
    private final Map<String, Double> emotionDict;

    public SentimentAnalyzer() {
        emotionDict = new HashMap<>();
        // 积极情感词
        emotionDict.put("喜欢", 0.8);
        emotionDict.put("好看", 0.8);
        emotionDict.put("精彩", 0.9);
        emotionDict.put("优秀", 0.9);
        emotionDict.put("感人", 0.7);
        emotionDict.put("完美", 1.0);
        emotionDict.put("棒", 0.8);
        emotionDict.put("精良", 0.7);
        emotionDict.put("推荐", 0.7);
        emotionDict.put("期待", 0.6);
        emotionDict.put("震撼", 0.8);
        emotionDict.put("享受", 0.7);
        emotionDict.put("出色", 0.8);
        emotionDict.put("赞", 0.8);
        emotionDict.put("有趣", 0.6);

        // 消极情感词
        emotionDict.put("失望", 0.2);
        emotionDict.put("无聊", 0.3);
        emotionDict.put("糟糕", 0.2);
        emotionDict.put("差", 0.3);
        emotionDict.put("烂", 0.1);
        emotionDict.put("浪费", 0.3);
        emotionDict.put("乏味", 0.4);
        emotionDict.put("枯燥", 0.4);
        emotionDict.put("尴尬", 0.3);
        emotionDict.put("垃圾", 0.1);
        emotionDict.put("难看", 0.2);
        emotionDict.put("劣质", 0.2);
        emotionDict.put("无趣", 0.4);
        emotionDict.put("不值", 0.3);
        emotionDict.put("后悔", 0.2);
    }

    public double analyzeSentiment(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0.5; // 空文本返回中性得分
        }

        try {
            double totalScore = 0.0;
            int matchCount = 0;

            // 对评论文本进行分词（这里使用简单的字符匹配，实际项目中可以使用专业分词工具）
            for (Map.Entry<String, Double> entry : emotionDict.entrySet()) {
                String keyword = entry.getKey();
                if (text.contains(keyword)) {
                    totalScore += entry.getValue();
                    matchCount++;
                }
            }

            // 如果找到情感词，返回平均得分
            if (matchCount > 0) {
                return totalScore / matchCount;
            }

            // 如果没有找到任何情感词，返回中性得分
            return 0.5;

        } catch (Exception e) {
            System.err.println("情感分析过程中出错: " + e.getMessage());
            return 0.5; // 出错时返回中性得分
        }
    }
}

package cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment;

/**
 * 单独的单词语言片段
 */
public class SingleWordSegment implements LanguageSegment{

    private String word;

    public SingleWordSegment(String word) {
        this.word = word;
    }

    public String getWord(){
        return word;
    }
}

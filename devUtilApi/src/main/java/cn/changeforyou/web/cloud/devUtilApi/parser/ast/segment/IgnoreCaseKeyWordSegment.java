package cn.changeforyou.web.cloud.devUtilApi.parser.ast.segment;

/***
 * 忽视大小写的关键词语段
 */
public class IgnoreCaseKeyWordSegment implements KeyWordSegment{

    private String keyWord;

    public IgnoreCaseKeyWordSegment(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String getKeyWord() {
        return keyWord;
    }

    @Override
    public boolean ignoreCase(){
        return true;
    }

    @Override
    public String getWord() {
        return keyWord;
    }
}

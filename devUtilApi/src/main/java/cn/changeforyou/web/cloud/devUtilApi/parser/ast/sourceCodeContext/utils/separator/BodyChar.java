package cn.changeforyou.web.cloud.devUtilApi.parser.ast.sourceCodeContext.utils.separator;

/**
 * 体符号
 */
public interface BodyChar extends SeparatorChar {

    /**
     * 获取关注的符号
     * @return
     */
    char[] getNoticedChars();

    /**
     * 获取对立的体符号
     * @return
     */
    char getOppositeChar();

    /**
     * 获取相反的BodyChar
     * @return
     */
    BodyChar getOppositeBodyChar();

    /**
     * 获取bodyChar相反的分隔符
     * @param bodyChar
     * @return
     */
    static BodyChar getOppositeBodyChar(BodyChar bodyChar) {
        return BodyCharEnum.getBodyChar(bodyChar.getOppositeChar());
    }
}

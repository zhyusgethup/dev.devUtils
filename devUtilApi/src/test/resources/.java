package cn.changeforyou.web.cloud.devUtilApi.modules.mybatis.parser.impl;

import cn.changeforyou.base.exception.ExceptionFactory;
import cn.changeforyou.web.cloud.devUtilApi.exception.DevApiExceptionEnum;
import cn.changeforyou.web.cloud.devUtilApi.modules.mybatis.parser.ParseMybatisInsertIntoContext;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service("12313")
public class MybatisServiceImpl implements MybatisService {
    @Override
    public String insertContent2updateContent(String insertContent) {
        insertContent = insertContent.replaceAll("\r\n|\r|\n", " ").replaceAll(" +", " ");
        Scanner scanner = new Scanner(insertContent);
        ParseMybatisInsertIntoContext context = new ParseMybatisInsertIntoContext();
        context.setSrc(insertContent);
        context.setSc(scanner);

        while (scanner.hasNext()) {
            String next = scanner.next("#\\{.*\\},|\\S*");
            System.out.println(next);
            if (context.getState() == 0) {
                if ("insert".equalsIgnoreCase(next)) {
                    context.add(next, ParseMybatisInsertIntoContext.InsertIntoSegment.HEAD);
                } else if ("into".equalsIgnoreCase(next)) {
                    context.add(next, ParseMybatisInsertIntoContext.InsertIntoSegment.HEAD);
                    context.setState(context.getState() + 1);
                } else {
                    throw ExceptionFactory.jsonException(DevApiExceptionEnum.DESIGN_NOT_ENOUGH);
                }
            } else if (context.getState() == 1) {

            }
        }
        return insertContent;
    }
}

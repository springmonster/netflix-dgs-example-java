package com.codegen.graphqldgs.datafetchers;

import com.codegen.graphqldgs.types.Page;
import com.codegen.graphqldgs.types.Score;
import com.codegen.graphqldgs.types.SubjectEnum;
import com.codegen.graphqldgs.types.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;
import java.util.Map;

@DgsComponent
public class UserDatafetcher {

    private final List<User> userList = List.of(
            User.newBuilder().id(1).age(19).name("张三").build(),
            User.newBuilder().id(2).age(20).name("李四").build());

    private final Map<String, Score> scoreMap = Map.of(
            "MATH_1", Score.newBuilder().score(1).subject(SubjectEnum.MATH).build(),
            "MATH_2", Score.newBuilder().score(2).subject(SubjectEnum.MATH).build(),
            "ENGLISH_1", Score.newBuilder().score(3).subject(SubjectEnum.ENGLISH).build(),
            "ENGLISH_2", Score.newBuilder().score(4).subject(SubjectEnum.ENGLISH).build()
    );

    @DgsQuery
    public List<User> user(@InputArgument Page page) {
        return userList.subList(0, page.getPageSize());
    }

    @DgsData(parentType = "User")
    public String name(@InputArgument String name) {
        return name;
    }

    @DgsData(parentType = "User")
    public List<Score> score(@InputArgument String subject,DgsDataFetchingEnvironment dfe) {

        //获取User
        User user = dfe.getSource();
        return List.of(scoreMap.getOrDefault(subject +"_"+ user.getId(), new Score()));
    }

}
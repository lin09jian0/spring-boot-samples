package com.jim.spring.boot.graphql.config;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.jim.spring.boot.graphql.bean.User;
import com.jim.spring.boot.graphql.bean.User1;
import com.jim.spring.boot.graphql.service.UserService;
import graphql.GraphQL;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;

/**
 * graphQL 配置类
 * 参考：https://graphql-java.readthedocs.io/en/latest/schema.html
 *      http://graphql.cn/learn/schema/#type-system
 * @author jim lin
 * 2018/9/30.
 */
@Configuration
public class GraphQLConfiguration {

    @Autowired
    private UserService userService;

    /**
     *  直接用java定义graphQL的语法
     */
//    @Bean
    public GraphQLSchema graphQLSchema(){
        User user = new User();
        user.setUserName("hello");
        user.setPhone("18222221111");
        user.setUserID("1");
        User user2 = new User();
        user2.setUserName("not");
        user2.setPhone("18222221111");
        user2.setUserID("1");
        GraphQLObjectType objectType = GraphQLObjectType.newObject()
                .name("user")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("userName")
                        .type(GraphQLString)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("phone")
                        .type(GraphQLString)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("sex")
                        .type(GraphQLString)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("age")
                        .type(GraphQLInt)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("userID")
                        .type(GraphQLString)
                ).build();
        GraphQLObjectType queryTyepe = GraphQLObjectType.newObject()
                .name("userQuery")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("user")
                        .type(objectType)
                        .dataFetcher(dataFetchingEnvironment -> {
                            if ("hello".equals(dataFetchingEnvironment.getArgument("userName"))){
                                return user;
                            }
                            return user2;
                        })
                        .argument(GraphQLArgument.newArgument().name("userName").type(GraphQLString).build())
                )
                .build();
        return GraphQLSchema.newSchema().query(queryTyepe).build();
    }

    /**
     * 原生的解析方法,我们如果不使用graphQL自带的servlet，可以参考此种方式来进行自己解析graphQL语法。
     */
    @Bean
    public GraphQL graphQL(){
        User user = new User();
        user.setUserName("hello");
        user.setPhone("18222221111");
        user.setUserID("1");
        User user2 = new User();
        user2.setUserName("not");
        user2.setPhone("18222221111");
        user2.setUserID("1");
        GraphQLObjectType objectType = GraphQLObjectType.newObject()
                .name("user")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("userName")
                        .type(GraphQLString)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("phone")
                        .type(GraphQLString)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("sex")
                        .type(GraphQLString)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("age")
                        .type(GraphQLInt)
                ).field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("userID")
                        .type(GraphQLString)
                ).build();
        GraphQLObjectType queryTyepe = GraphQLObjectType.newObject()
                .name("userQuery")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("user")
                        .type(objectType)
                        .dataFetcher(dataFetchingEnvironment -> {
                            if ("hello".equals(dataFetchingEnvironment.getArgument("userName"))){
                                return user;
                            }
                            return user2;
                        })
                        .argument(GraphQLArgument.newArgument().name("userName").type(GraphQLString).build())
                )
                .build();
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema().query(queryTyepe).build();
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        System.out.println("=============="+graphQL.execute("{user{userName,phone,userID}}").getData());
        System.out.println("=============="+graphQL.execute("{user(userName: \"hello\"){userName,phone,userID}}").getData());
        return graphQL;
    }


    /**
     * graphQL集成spring boot 的时候会集成graphql-java-servlet，启动以后会自己暴露/graphql,如果修改默认暴露类，可以使用graphql.servlet.mapping=
     * 下面的做法是定义graphQL的Schema ，然后加载。
     * 启动以后我们可以访问：localhost:6777/graphql  ，请求的body为：{"query": "{user(userName: \"hello\"){userName,phone,userID} user1{userName}} "} 来获取信息
     * 返回样例报文：
     * {
     *     "data": {
     *         "user": {
     *             "userName": "hello",
     *             "phone": "18222221111",
     *             "userID": "1"
     *         },
     *         "user1": {
     *             "userName": "default"
     *         }
     *     }
     * }
     */
    @Bean
    public GraphQLSchema schema() {
        SchemaParser schemaParser = new SchemaParser();
        InputStream inputStream = GraphQLConfiguration.class.getClassLoader().getResourceAsStream("graphQL/user.graphqls");
        String schema = null;
        try {
            schema = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("QueryType",typeWiring-> typeWiring
                        .dataFetcher("user",dataFetchingEnvironment -> userService.getUser(dataFetchingEnvironment.getArgument("userName")))
                        .dataFetcher("user1",dataFetchingEnvironment -> new User1())
                )
                .build();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }
}

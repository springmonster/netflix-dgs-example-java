# DGS

- [DGS](https://netflix.github.io/dgs/)
- [DGS Github](https://github.com/Netflix/dgs-framework)

## module说明

| Module                      | 说明                                                                                   |
|-----------------------------|--------------------------------------------------------------------------------------|
| [✅a-start](./a-start)       | 简单的使用，使用多个`*.graphqls`，举例@DgsData.List                                               |
| [✅b-codegen](./b-codegen)   | 使用codegen，多module，type中带方法，使用常量在@DgsData指定parentType和field，添加@RequestHeader          | 
| [✅c-scalar](./c-scalar)     | 支持自定义类型                                                                              |                                    
| [✅d-http](./d-http)         | 支持Query，Mutation，Subscription，参数校验，支持Apollo Tracing                                  |           
| [✅e-file](./e-file)         | 支持文件上传下载                                                                             |                                     
| [✅f-auth](./f-auth)         | 支持认证和授权                                                                              |                                      
| [✅g-error](./g-error)       | 支持自定义错误类型                                                                            | 
| [✅h-ut](./h-ut)             | 支持单元测试，集成测试，支持自定义类型（custom scalar）的单元测试                                              | 
| [✅i-nplusone](./i-nplusone) | 解决N+1的问题，支持自定义Tracing                                                                | 
| [✅j-sample](./j-sample)     | 将Query和Mutation的配置分解到各个配置文件中，避免出现请求方法的爆炸                                             |
| [k-postg](./k-postg)              | 支持PostGraphile（Experimental）                                                         |
| [✅y-bff](./y-bff)           | 支持Client和Server，支持voyager的description，支持https://github.com/APIs-guru/graphql-voyager | 
| [✅z-domain](./z-domain)     | 支持Client和Server，支持https://github.com/APIs-guru/graphql-voyager                       |

## Intellij Idea Plugin的安装

- [GraphQL](https://plugins.jetbrains.com/plugin/8097-graphql)
- [DGS](https://plugins.jetbrains.com/plugin/17852-dgs)

## 问题

1. 是否支持多个`*.graphqls`文件？

> 支持，请查看`a-start`

2. 减少代码编写量是否支持？例如只编写`*.graphqls`，Request和Response自动生成

> 支持，请查看`b-codegen`

3. 自定义类型如何支持？例如Long，BigDecimal，UUID

> 请查看`c-scalar`，支持自定义类型，也搭配了`codegen`进行使用

4. 是否支持HTTP的所有方法？参数校验如何支持？

> 支持，请查看`d-http`

> 参数校验：列表，重命名，Optional，Validation

> 方法：Query，Mutation

5. 文件上传MultiPartFile如何支持？

> 请查看`e-file`
>
> 请查看https://github.com/jaydenseric/graphql-multipart-request-spec

6. 认证和授权如何支持？

> 请查看`f-auth`

7. 错误类型如何支持？

> 请查看`g-error`

8. 单元测试如何支持？

> 请查看`h-ut`

9. N+1问题如何支持？

> 请查看`i-nplusone`

10. GraphQL作为Client调用提供GraphQL的Server如何支持？

> 请查看`y-bff`和`z-domain`

11. type中添加method是否支持？

> 查看`b-codegen`

12. 如何获取`HttpServletRequest `?

```
@DgsQuery
public String user(DgsDataFetchingEnvironment dfe) {
    DgsWebMvcRequestData requestData = (DgsWebMvcRequestData) dfe.getDgsContext().getRequestData();
    ServletWebRequest webRequest = (ServletWebRequest) requestData.getWebRequest();
    HttpServletRequest httpServletRequest = webRequest.getRequest();
    return "";
}
```

13. Tracing是否支持？

> 查看`d-http`和`i-nplusone`

14. WebSocket是否支持？

> 支持，请查看`d-http`

16. file类型的download是否支持？

> 因为GraphQL的Response是作为JSON，所以无法使用Binary作为类型，解决方案有如下两种：

1. 将file转为BASE64 String，写入到Response的data中（不推荐）
2. 将file上传至文件服务器，返回文件的URL

## a-start

- Startup then visit http://localhost:10001/graphiql
- Input

```
{
    shows {
        title
        releaseYear
    }
}
------
{
  shows(titleFilter: "Ozark") {
    title
    releaseYear
  }
}
------
{
  showsWithDgsData {
    id
    title
    releaseYear
    actors {
      name
    }
  }
}
------
{
  user {
    id
    name
  }
}
```

## b-codegen

- root build.gradle

```
plugins {
    id "com.netflix.dgs.codegen" version "5.1.17" apply false
}
```

- module build.gradle

```
plugins {
    id "com.netflix.dgs.codegen"
}
```

- module build.gradle

```
generateJava{
    schemaPaths = ["${projectDir}/src/main/resources/schema"] // List of directories containing schema files
    packageName = 'com.codegen.graphqldgs' // The package name to use to generate sources
    generateClient = true // Enable generating the type safe query API
}
```

- check build folder
- Input，visit http://127.0.0.1:10002/graphiql

```
{
  shows {
    id
    title
    releaseYear
  }
}
------
{
  shows(titleFilter: "Ozark") {
    id
    title
    releaseYear
  }
}
```

## c-scalar

- Input，visit http://127.0.0.1:10003/graphiql

```
{
  shows {
    id
    title
    releaseYear
    price
    dateTime
    bigDecimal
    uuid
  }
}
```

## d-http

- Startup，visit http://127.0.0.1:10004/graphiql
- Input

```
{
  show(people: {name: "zhangsan"}) {
    id
    name
  }
  shows(personList: [{name: "zhangsan"}]) {
    id
    name
  }
}
------
{
  showWithGood {
    id
    name
  }
}
------
{
  showWithGood(good: {name: "Car"}) {
    id
    name
  }
}
------
mutation {
  addRating(title: "title", stars: 100) {
    avgStars
  }
  addRatingWithInput(input: {title: "title", stars: 200}) {
    avgStars
  }
}
------
mutation {
  addRating(title: "title", stars: 100) {
    avgStars
  }
  addRatingWithInput(input: {title: "hel", stars: 200}) {
    avgStars
  }
}
```

Use `Postman` to visit `Subscription`
![img.png](img.png)

## e-file

- Startup
- Input with `curl`

```
curl localhost:10005/graphql \
  -F operations='{ "query": "mutation upload($file: Upload!) { upload(file: $file) }" , "variables": { "file": null } }' \
  -F map='{ "0": ["variables.file"] }' \
  -F 0=@1.png
------
curl localhost:10005/graphql \
  -F operations='{ "query": "mutation addArtwork($file: Upload!) { addArtwork(file: $file) }" , "variables": { "file": null } }' \
  -F map='{ "0": ["variables.file"] }' \
  -F 0=@1.png

```

- Output

> Please check `project uploaded-images` folder

```
{"data":{"upload":true}}
------
{"data":{"addArtwork":true}}
```

## f-auth

- Startup，visit http://localhost:10006/graphiql
- Input

```
{
  salary
}
------
{
  salary
}
# REQUEST HEADERS中Input{ "Authorization": "Basic aHI6aHI=" }，This is hr username and password
------
mutation {
  updateSalary(salaryInput: {employeeId: "1", newSalary: "100"}) {
    id
    employeeId
    newSalary
  }
}
------
mutation {
  updateSalary(salaryInput: {employeeId: "1", newSalary: "100"}) {
    id
    employeeId
    newSalary
  }
}
# REQUEST HEADERS中Input{ "Authorization": "Basic aHI6aHI=" }，This is hr username and password
```

## g-error

- Startup，visit http://localhost:10007/graphiql
- Input

```
{
  show(people: {name: "haha"}) {
    id
    name
  }
}
------
{
  show(people: {name: "zhangsan"}) {
    id
    name
  }
}
------
{
  getRating(id: "1") {
    avgStars
  }
}
```

## h-ut

> see `test` folder

- Run `DemoControllerTests` and `ShowDataFetcherTest` to check

## i-nplusone

- Startup，visit http://localhost:10009/graphiql
- Input

```
{
  shows {
    showId
    title
    reviews {
      starRating
    }
  }
}
------
{
  showsN {
    id
    title
    releaseYear
    artwork {
      url
    }
    reviewsN {
      username
      starScore
      submittedDate
    }
  }
}
```

## l-interfaceunion

- Startup then visit http://localhost:10012/graphiql
- interface input

```
{
  movies {
    __typename
    ... on ActionMovie {
      title
      nrOfExplosions
    }
    ... on ScaryMovie {
      title
      gory
      scareFactor
    }
  }
}
```

- union input

```
{
  search {
    __typename
    ... on Actor {
      name
    }
    ... on Series {
      title
    }
  }
}
```


## y-bff

- Startup，Startup module `z-domain`，visit http://localhost:20000/graphiql
- Startup, Startup module `z-domain`，visit http://localhost:20000/voyager
- Input

```
{
  shows {
    id
    title
    releaseYear
  }
}
------
mutation {
  addShow(input: {title: "title", releaseYear: 2022}) {
    id
    title
    releaseYear
  }
}
```

## z-domain

- Startup，visit http://localhost:20001/graphiql
- Startup，visit http://localhost:20001/voyager
- Input

```
{
  shows {
    id
    title
    releaseYear
  }
}
------
mutation {
  addShow(input: {title: "title", releaseYear: 2022}) {
    id
    title
    releaseYear
  }
}
```

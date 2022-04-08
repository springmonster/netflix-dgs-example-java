# DGS

- [DGS官网](https://netflix.github.io/dgs/)
- [DGS Github](https://github.com/Netflix/dgs-framework)

## module说明

| Module                 | 说明                                                             |
|------------------------|----------------------------------------------------------------|
| [✅a-start](./a-start)  | 简单的使用，使用多个`*.graphqls`                                         |
| [✅b-codegen](./b-codegen) | 使用codegen，Entity的代码自动生成，多module，type中带方法                       | 
| [✅c-scalar](./c-scalar) | 支持自定义类型                                                        |                                    
| [✅d-http](./d-http)    | 支持Query，Mutation，Subscription，参数校验                             |           
| [✅e-file](./e-file)    | 支持文件上传下载                                                       |                                     
| [✅f-auth](./f-auth)    | 支持认证和授权                                                        |                                      
| [✅g-error](./g-error)  | 支持错误类型                                                         | 
| [✅h-ut](./h-ut)        | 支持单元测试                                                         | 
| [✅i-nplusone](./i-nplusone) | 解决N+1的问题，支持Tracing                                             | 
| [✅y-bff](./y-bff)  | 支持Client和Server，支持https://github.com/APIs-guru/graphql-voyager | 
 | [✅z-domain](./z-domain) | 支持Client和Server，支持https://github.com/APIs-guru/graphql-voyager |

## Intellij Idea Plugin的安装

- GraphQL
- DGS

## 问题

1. 是否支持多个`*.graphqls`？

> 查看`a-start`

2. 减少代码编写量是否支持？例如只编写`*.graphqls`，Request和Response自动生成

> 查看`b-codegen`

3. 自定义类型如何支持？例如Long，BigDecimal，UUID

> 查看`c-scalar`，支持自定义类型，也搭配了`codegen`进行使用

4. 是否支持HTTP的所有方法？参数校验如何支持？

> 查看`d-http`

> 参数校验：列表，重命名，Optional，Validation

> 方法：Query，Mutation，Subscription（目前用不到）

5. 文件上传MultiPartFile如何支持？

> 查看`e-file`
>
> 查看https://github.com/jaydenseric/graphql-multipart-request-spec

6. 认证和授权如何支持？

> 查看`f-auth`

7. 错误类型如何支持？

> 查看`g-error`

8. 单元测试如何支持？

> 查看`h-ut`

9. N+1问题如何支持？

> 查看`i-nplusone`

10. GraphQL作为Client调用提供GraphQL的Server如何支持？

> 查看`y-bff`和`z-domain`

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

> 查看`i-nplusone`

## a-start

- 启动，访问http://localhost:10001/graphiql
- 输入

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

- 输出

```
{
  "data": {
    "shows": [
      {
        "title": "Stranger Things",
        "releaseYear": 2016
      },
      {
        "title": "Ozark",
        "releaseYear": 2017
      },
      {
        "title": "The Crown",
        "releaseYear": 2016
      },
      {
        "title": "Dead to Me",
        "releaseYear": 2019
      },
      {
        "title": "Orange is the New Black",
        "releaseYear": 2013
      }
    ]
  }
}
------
{
  "data": {
    "shows": [
      {
        "title": "Ozark",
        "releaseYear": 2017
      }
    ]
  }
}
------
{
  "data": {
    "showsWithDgsData": [
      {
        "id": "1",
        "title": "Stranger Things",
        "releaseYear": 2016,
        "actors": [
          {
            "name": "zhangsan"
          },
          {
            "name": "lisi"
          }
        ]
      },
      {
        "id": "2",
        "title": "Ozark",
        "releaseYear": 2017,
        "actors": null
      },
      {
        "id": "3",
        "title": "The Crown",
        "releaseYear": 2016,
        "actors": null
      },
      {
        "id": "4",
        "title": "Dead to Me",
        "releaseYear": 2019,
        "actors": null
      },
      {
        "id": "5",
        "title": "Orange is the New Black",
        "releaseYear": 2013,
        "actors": null
      }
    ]
  }
}
------
{
  "data": {
    "user": {
      "id": "id1",
      "name": "zhangsan"
    }
  }
}
```

## b-codegen

- root的build.gradle添加

```
plugins {
    id "com.netflix.dgs.codegen" version "5.1.17" apply false
}
```

- module的build.gradle添加

```
plugins {
    id "com.netflix.dgs.codegen"
}
```

- module的build.gradle添加并运行

```
generateJava{
    schemaPaths = ["${projectDir}/src/main/resources/schema"] // List of directories containing schema files
    packageName = 'com.codegen.graphqldgs' // The package name to use to generate sources
    generateClient = true // Enable generating the type safe query API
}
```

- 查看build文件夹下生成的产物
- 输入，访问http://127.0.0.1:10002/graphiql

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

- 输出

```
{
  "data": {
    "shows": [
      {
        "id": 1,
        "title": "Stranger Things",
        "releaseYear": 2016
      },
      {
        "id": 2,
        "title": "Ozark",
        "releaseYear": 2017
      },
      {
        "id": 3,
        "title": "The Crown",
        "releaseYear": 2016
      },
      {
        "id": 4,
        "title": "Dead to Me",
        "releaseYear": 2019
      },
      {
        "id": 5,
        "title": "Orange is the New Black",
        "releaseYear": 2013
      }
    ]
  }
}
------
{
  "data": {
    "shows": [
      {
        "id": 2,
        "title": "Ozark",
        "releaseYear": 2017
      }
    ]
  }
}
```

## c-scalar

- 输入，访问http://127.0.0.1:10003/graphiql

```
{
  shows {
    id
    dateTime
    price
    bigDecimal
    uuid
  }
}
```

- 输出

```
{
  "data": {
    "shows": [
      {
        "id": 1,
        "dateTime": "2022-04-03T22:30:23.149762+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "93ed1632-8da2-4adc-858d-1a2c003e1f79"
      },
      {
        "id": 2,
        "dateTime": "2022-04-03T22:30:23.149942+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "b966497e-6785-4220-bfcc-ef9a296a4072"
      },
      {
        "id": 3,
        "dateTime": "2022-04-03T22:30:23.150033+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "1e5c4216-7076-456f-90a8-2972da743a63"
      },
      {
        "id": 4,
        "dateTime": "2022-04-03T22:30:23.150059+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "20b2339a-e7fa-433d-add4-1dfed7516c23"
      },
      {
        "id": 5,
        "dateTime": "2022-04-03T22:30:23.150138+08:00",
        "price": 100,
        "bigDecimal": 100,
        "uuid": "ab90a2c2-797e-41c4-9bb8-2a429cbf0272"
      }
    ]
  }
}
```

## d-http

- 启动，访问http://127.0.0.1:10004/graphiql
- 输入

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

- 输出

```
{
  "data": {
    "show": {
      "id": "showId2",
      "name": "showName2"
    },
    "shows": {
      "id": "showId1",
      "name": "zhangsan"
    }
  }
}
------
{
  "data": {
    "showWithGood": {
      "id": "showId2",
      "name": "Good is Empty"
    }
  }
}
------
{
  "data": {
    "showWithGood": {
      "id": "showId2",
      "name": "Car"
    }
  }
}
------
{
  "errors": [
    {
      "message": "/addRatingWithInput/input/title size must be between 1 and 3",
      "locations": [
        {
          "line": 5,
          "column": 3
        }
      ],
      "path": [
        "addRatingWithInput"
      ],
      "extensions": {
        "classification": {
          "type": "ExtendedValidationError",
          "validatedPath": [
            "addRatingWithInput",
            "input",
            "title"
          ],
          "constraint": "@Size"
        }
      }
    }
  ],
  "data": {
    "addRating": {
      "avgStars": 100
    },
    "addRatingWithInput": null
  }
}
-----
{
  "data": {
    "addRating": {
      "avgStars": 100
    },
    "addRatingWithInput": {
      "avgStars": 200
    }
  }
}
```

## e-file

- 启动
- 通过`curl`输入

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

- 输出

> 第二个请求之后查看`project下面的uploaded-images`文件

```
{"data":{"upload":true}}
------
{"data":{"addArtwork":true}}
```

## f-auth

- 启动，访问http://localhost:10006/graphiql
- 输入

```
{
  salary
}
------
{
  salary
}
# REQUEST HEADERS中输入{ "Authorization": "Basic aHI6aHI=" }，这里对应hr的用户名和密码
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
# REQUEST HEADERS中输入{ "Authorization": "Basic aHI6aHI=" }，这里对应hr的用户名和密码
```

- 输出

```
{
  "errors": [
    {
      "message": "org.springframework.security.access.AccessDeniedException: 不允许访问",
      "locations": [],
      "path": [
        "salary"
      ],
      "extensions": {
        "errorType": "PERMISSION_DENIED"
      }
    }
  ],
  "data": {
    "salary": null
  }
}
------
{
  "data": {
    "salary": "Salary Test"
  }
}
------
{
  "errors": [
    {
      "message": "org.springframework.security.access.AccessDeniedException: 不允许访问",
      "locations": [],
      "path": [
        "updateSalary"
      ],
      "extensions": {
        "errorType": "PERMISSION_DENIED"
      }
    }
  ],
  "data": {
    "updateSalary": null
  }
}
------
{
  "data": {
    "updateSalary": {
      "id": "9f495563-edf9-4076-983e-890350744493",
      "employeeId": "1",
      "newSalary": "100"
    }
  }
}
```

## g-error

- 启动，访问http://localhost:10007/graphiql
- 输入

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

- 输出

```
{
  "errors": [
    {
      "message": "Show: 034bb19e-e7dc-44dc-90de-f96701ea56d5 was not found.",
      "locations": [],
      "path": [
        "show"
      ],
      "extensions": {
        "errorType": "NOT_FOUND",
        "debugInfo": {
          "somefield": "somevalue"
        }
      }
    }
  ],
  "data": {
    "show": null
  }
}
------
{
  "errors": [
    {
      "message": "java.lang.ArithmeticException: / by zero",
      "locations": [],
      "path": [
        "show"
      ],
      "extensions": {
        "errorType": "INTERNAL"
      }
    }
  ],
  "data": {
    "show": null
  }
}
------
{
  "errors": [
    {
      "message": "Rating: 1 was not found.",
      "locations": [],
      "path": [
        "getRating"
      ],
      "extensions": {
        "classification": "RATING_NOT_FOUND"
      }
    }
  ],
  "data": {
    "getRating": null
  }
}
```

## h-ut

> 查看`test`文件夹

- 启动，访问http://localhost:10008/graphiql
- 输入

```
{
  greeting
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

- 输出

```
{
  "data": {
    "greeting": "greeting!",
    "shows": [
      {
        "id": 1,
        "title": "Stranger Things",
        "releaseYear": 2016
      },
      {
        "id": 2,
        "title": "Ozark",
        "releaseYear": 2017
      },
      {
        "id": 3,
        "title": "The Crown",
        "releaseYear": 2016
      },
      {
        "id": 4,
        "title": "Dead to Me",
        "releaseYear": 2019
      },
      {
        "id": 5,
        "title": "Orange is the New Black",
        "releaseYear": 2013
      }
    ]
  }
}
------
{
  "data": {
    "addShow": {
      "id": -1754284133,
      "title": "title",
      "releaseYear": 2022
    }
  }
}
```

## i-nplusone

- 启动，访问http://localhost:10009/graphiql
- 输入

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

- 输出

```
{
  "data": {
    "shows": [
      {
        "showId": "1",
        "title": "Stranger Things",
        "reviews": [
          {
            "starRating": 1
          },
          {
            "starRating": 1
          },
          {
            "starRating": 1
          },
          {
            "starRating": 1
          }
        ]
      },
      {
        "showId": "2",
        "title": "Ozark",
        "reviews": [
          {
            "starRating": 2
          },
          {
            "starRating": 2
          },
          {
            "starRating": 2
          },
          {
            "starRating": 2
          }
        ]
      },
      {
        "showId": "3",
        "title": "The Crown",
        "reviews": [
          {
            "starRating": 3
          },
          {
            "starRating": 3
          },
          {
            "starRating": 3
          },
          {
            "starRating": 3
          }
        ]
      },
      {
        "showId": "4",
        "title": "Dead to Me",
        "reviews": [
          {
            "starRating": 4
          },
          {
            "starRating": 4
          },
          {
            "starRating": 4
          },
          {
            "starRating": 4
          }
        ]
      },
      {
        "showId": "5",
        "title": "Orange is the New Black",
        "reviews": [
          {
            "starRating": 5
          },
          {
            "starRating": 5
          },
          {
            "starRating": 5
          },
          {
            "starRating": 5
          }
        ]
      }
    ]
  }
}
------
{
  "data": {
    "showsN": [
      {
        "id": 1,
        "title": "Stranger Things",
        "releaseYear": 2016,
        "artwork": null,
        "reviewsN": [
          {
            "username": "giovanna.beatty",
            "starScore": 4,
            "submittedDate": "2022-01-19T14:37:47.056Z"
          },
          {
            "username": "kathryn.marquardt",
            "starScore": 1,
            "submittedDate": "2021-08-22T02:04:12.796Z"
          },
          {
            "username": "mayra.brakus",
            "starScore": 5,
            "submittedDate": "2021-10-19T05:55:37.287Z"
          },
          {
            "username": "charles.goldner",
            "starScore": 2,
            "submittedDate": "2021-10-08T07:23:19.009Z"
          },
          {
            "username": "kindra.mclaughlin",
            "starScore": 2,
            "submittedDate": "2022-01-14T10:25:11.624Z"
          },
          {
            "username": "maria.deckow",
            "starScore": 3,
            "submittedDate": "2021-08-20T07:25:47.71Z"
          },
          {
            "username": "roy.rice",
            "starScore": 3,
            "submittedDate": "2022-01-19T08:08:56.592Z"
          },
          {
            "username": "eve.ankunding",
            "starScore": 4,
            "submittedDate": "2021-07-01T08:33:22.273Z"
          },
          {
            "username": "lindy.cassin",
            "starScore": 4,
            "submittedDate": "2021-10-27T02:19:19.137Z"
          },
          {
            "username": "joan.borer",
            "starScore": 2,
            "submittedDate": "2021-12-10T00:45:32.832Z"
          },
          {
            "username": "deandra.gorczany",
            "starScore": 0,
            "submittedDate": "2021-10-12T01:50:43.559Z"
          },
          {
            "username": "mitchell.steuber",
            "starScore": 2,
            "submittedDate": "2021-09-13T14:17:25.74Z"
          },
          {
            "username": "laverne.waelchi",
            "starScore": 1,
            "submittedDate": "2021-12-26T20:55:09.722Z"
          },
          {
            "username": "carroll.mccullough",
            "starScore": 1,
            "submittedDate": "2021-11-08T20:37:40.085Z"
          },
          {
            "username": "tomoko.marquardt",
            "starScore": 3,
            "submittedDate": "2021-11-15T20:11:59.105Z"
          },
          {
            "username": "shante.torp",
            "starScore": 1,
            "submittedDate": "2021-11-29T13:07:41.191Z"
          },
          {
            "username": "trenton.weber",
            "starScore": 0,
            "submittedDate": "2022-01-11T10:53:05.894Z"
          },
          {
            "username": "lucrecia.batz",
            "starScore": 1,
            "submittedDate": "2021-10-30T21:05:51.955Z"
          }
        ]
      },
      {
        "id": 2,
        "title": "Ozark",
        "releaseYear": 2017,
        "artwork": null,
        "reviewsN": [
          {
            "username": "cristopher.nitzsche",
            "starScore": 4,
            "submittedDate": "2021-08-12T05:21:15.865Z"
          },
          {
            "username": "easter.heaney",
            "starScore": 2,
            "submittedDate": "2021-09-24T22:20:43.561Z"
          },
          {
            "username": "jerrell.von",
            "starScore": 4,
            "submittedDate": "2021-09-12T21:15:09.093Z"
          },
          {
            "username": "lavera.kunze",
            "starScore": 1,
            "submittedDate": "2021-06-15T04:15:41.084Z"
          }
        ]
      },
      {
        "id": 3,
        "title": "The Crown",
        "releaseYear": 2016,
        "artwork": null,
        "reviewsN": [
          {
            "username": "chance.price",
            "starScore": 2,
            "submittedDate": "2022-01-12T23:46:04.185Z"
          },
          {
            "username": "nanette.becker",
            "starScore": 4,
            "submittedDate": "2021-10-24T11:03:41.86Z"
          },
          {
            "username": "gabriel.mueller",
            "starScore": 1,
            "submittedDate": "2021-10-09T12:05:04.674Z"
          },
          {
            "username": "drew.langworth",
            "starScore": 3,
            "submittedDate": "2021-08-20T16:24:35.896Z"
          },
          {
            "username": "clement.kerluke",
            "starScore": 0,
            "submittedDate": "2021-07-12T22:21:28.7Z"
          },
          {
            "username": "rae.will",
            "starScore": 5,
            "submittedDate": "2022-02-05T19:23:33.349Z"
          },
          {
            "username": "geoffrey.hegmann",
            "starScore": 3,
            "submittedDate": "2021-11-15T22:34:51.577Z"
          },
          {
            "username": "sunni.conroy",
            "starScore": 4,
            "submittedDate": "2022-03-05T05:26:18.208Z"
          },
          {
            "username": "eldon.rodriguez",
            "starScore": 3,
            "submittedDate": "2021-06-12T19:56:17.178Z"
          },
          {
            "username": "isobel.kautzer",
            "starScore": 1,
            "submittedDate": "2022-03-10T01:17:00.589Z"
          },
          {
            "username": "garfield.lindgren",
            "starScore": 2,
            "submittedDate": "2022-02-22T05:07:37.257Z"
          },
          {
            "username": "jama.corwin",
            "starScore": 2,
            "submittedDate": "2021-11-06T15:01:41.229Z"
          },
          {
            "username": "randell.ratke",
            "starScore": 5,
            "submittedDate": "2022-03-21T06:51:09.457Z"
          },
          {
            "username": "vonda.prohaska",
            "starScore": 1,
            "submittedDate": "2021-09-09T20:45:41.42Z"
          },
          {
            "username": "jeanelle.lynch",
            "starScore": 5,
            "submittedDate": "2021-12-17T10:07:13.702Z"
          },
          {
            "username": "susie.windler",
            "starScore": 4,
            "submittedDate": "2021-07-19T06:13:01.88Z"
          },
          {
            "username": "treena.pouros",
            "starScore": 2,
            "submittedDate": "2021-07-30T04:00:50.572Z"
          },
          {
            "username": "wan.deckow",
            "starScore": 5,
            "submittedDate": "2021-07-11T07:39:35.242Z"
          }
        ]
      },
      {
        "id": 4,
        "title": "Dead to Me",
        "releaseYear": 2019,
        "artwork": null,
        "reviewsN": [
          {
            "username": "kareem.pagac",
            "starScore": 1,
            "submittedDate": "2022-01-13T02:40:08.084Z"
          },
          {
            "username": "greta.stroman",
            "starScore": 2,
            "submittedDate": "2021-07-12T12:03:40.602Z"
          },
          {
            "username": "rolande.johns",
            "starScore": 2,
            "submittedDate": "2022-01-31T10:02:57.262Z"
          },
          {
            "username": "nelia.larkin",
            "starScore": 4,
            "submittedDate": "2021-12-26T05:07:38.204Z"
          },
          {
            "username": "cristie.morar",
            "starScore": 5,
            "submittedDate": "2021-11-17T16:42:03.403Z"
          },
          {
            "username": "theron.becker",
            "starScore": 0,
            "submittedDate": "2022-02-08T19:31:22.167Z"
          },
          {
            "username": "timmy.ryan",
            "starScore": 4,
            "submittedDate": "2022-03-06T15:51:29.531Z"
          }
        ]
      },
      {
        "id": 5,
        "title": "Orange is the New Black",
        "releaseYear": 2013,
        "artwork": null,
        "reviewsN": [
          {
            "username": "ernie.bogan",
            "starScore": 2,
            "submittedDate": "2021-08-12T19:55:18.065Z"
          },
          {
            "username": "annalee.reichel",
            "starScore": 1,
            "submittedDate": "2021-08-31T19:19:59.344Z"
          },
          {
            "username": "celena.marvin",
            "starScore": 4,
            "submittedDate": "2021-10-29T17:09:39.2Z"
          },
          {
            "username": "dusty.ritchie",
            "starScore": 3,
            "submittedDate": "2021-09-10T21:15:26.526Z"
          },
          {
            "username": "jerald.ziemann",
            "starScore": 0,
            "submittedDate": "2021-08-25T20:15:35.954Z"
          },
          {
            "username": "ula.kirlin",
            "starScore": 2,
            "submittedDate": "2021-10-18T05:14:43.001Z"
          },
          {
            "username": "chad.conn",
            "starScore": 3,
            "submittedDate": "2021-07-23T19:40:05.251Z"
          },
          {
            "username": "lianne.becker",
            "starScore": 2,
            "submittedDate": "2021-10-02T14:18:18.36Z"
          },
          {
            "username": "wayne.considine",
            "starScore": 4,
            "submittedDate": "2022-01-13T23:28:31.209Z"
          }
        ]
      }
    ]
  }
}
```

## y-bff

- 启动，同时启动`domain`，访问http://localhost:20000/graphiql
- 输入

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

- 输出

```
{
  "data": {
    "shows": [
      {
        "id": "Stranger Things",
        "title": "1",
        "releaseYear": 2016
      },
      {
        "id": "Ozark",
        "title": "2",
        "releaseYear": 2017
      },
      {
        "id": "The Crown",
        "title": "3",
        "releaseYear": 2016
      },
      {
        "id": "Dead to Me",
        "title": "4",
        "releaseYear": 2019
      },
      {
        "id": "Orange is the New Black",
        "title": "5",
        "releaseYear": 2013
      }
    ]
  }
}
------
{
  "data": {
    "addShow": {
      "id": "3bb937c7-5583-4d87-b8ac-9df747b27e1f",
      "title": "title",
      "releaseYear": 2022
    }
  }
}
```

## z-domain

- 启动，访问http://localhost:20001/graphiql
- 输入

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

- 输出

```
{
  "data": {
    "shows": [
      {
        "id": "Stranger Things",
        "title": "1",
        "releaseYear": 2016
      },
      {
        "id": "Ozark",
        "title": "2",
        "releaseYear": 2017
      },
      {
        "id": "The Crown",
        "title": "3",
        "releaseYear": 2016
      },
      {
        "id": "Dead to Me",
        "title": "4",
        "releaseYear": 2019
      },
      {
        "id": "Orange is the New Black",
        "title": "5",
        "releaseYear": 2013
      }
    ]
  }
}
------
{
  "data": {
    "addShow": {
      "id": "7fdcde10-09f2-4df0-aaa0-d2b83c38d64a",
      "title": "title",
      "releaseYear": 2022
    }
  }
}
```

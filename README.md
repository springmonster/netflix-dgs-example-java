# DGS

- [DGS官网](https://netflix.github.io/dgs/)
- [DGS Github](https://github.com/Netflix/dgs-framework)

## module说明

- a-start：简单的使用，使用多个`*.graphqls`
- b-codegen：使用codegen，Entity的代码自动生成，多module
- c-scalar：支持自定义类型
- d-http：支持Query，Mutation，Subscription，参数校验
- e-file：支持文件上传下载
- f-auth：支持认证和授权
- g-error：支持错误类型
- y-bff和z-domain：支持Client和Server

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

> f-auth

7. 错误类型如何支持？

> g-error

8. GraphQL作为Client调用提供GraphQL的Server如何支持？

> y-bff,z-domain

## a-start

9. 单元测试如何支持？

> h-ut

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

## g-error
- 启动
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

## y-bff

## z-domain

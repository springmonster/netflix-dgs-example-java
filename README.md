# DGS
- 启动，访问http://localhost:10001/graphiql
- 输入
```
{
    shows {
        title
        releaseYear
    }
}

{
  shows(titleFilter: "Ozark") {
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
```
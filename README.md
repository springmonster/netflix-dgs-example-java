# DGS

- [DGS](https://netflix.github.io/dgs/)
- [DGS Github](https://github.com/Netflix/dgs-framework)

## module description

| Module                                                                                                                                                                                          | Description                                                                                                      |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|
| [✅a-start](./a-start)                                                                                                                                                                           | Example of multiple `*.graphqls`，@DgsData.List                                                                   |
| [✅b-codegen](./b-codegen)                                                                                                                                                                       | Example of codegen，multiple modules，methods in type，support constant in @DgsData，@RequestHeader                  | 
| [✅c-scalar](./c-scalar)                                                                                                                                                                         | Example of custom scalar                                                                                         |                                    
| [✅d-http](./d-http)                                                                                                                                                                             | Example of Query，Mutation，Subscription，params validation，Apollo Tracing                                          |           
| [✅e-file](./e-file)                                                                                                                                                                             | Example of file upload                                                                                           |                                     
| [✅f-auth](./f-auth)                                                                                                                                                                             | Example of authentication and authorization                                                                      |                                      
| [✅g-error](./g-error)                                                                                                                                                                           | Example of custom error type                                                                                     | 
| [✅h-ut](./h-ut)                                                                                                                                                                                 | Example of uni test, integration test, unit test of supporting custom scalar                                     | 
| [✅i-nplusone](./i-nplusone)                                                                                                                                                                     | Example of `N+1`, support custom tracing                                                                         | 
| [✅j-sample](./j-sample)                                                                                                                                                                         | Example of split Query and Mutation into different configruation files to avoid too many definitions in one file |
| [k-postg](./k-postg)                                                                                                                                                                            | Example of supporting PostGraphile（Experimental）（TODO）                                                           |
| [✅l-interfaceunion](./l-interfaceunion)                                                                                                                                                         | Example of interface and union                                                                                   |
| [✅m-dynamicschema](./m-dynamicschema)                                                                                                                                                           | Example of dynamic schema                                                                                        |
| [❎n-webflux](./n-webflux)                                                                                                                                                                       | Example of dynamic webflux, there are problems with `Spring Security`                                            |
| [✅o-metrics](./o-metrics)                                                                                                                                                                       | Example of metrics                                                                                               |
| [✅p-apollo-gateway](./p-apollo-gateway)<br/>[✅p-federation-customer](./p-federation-customer)<br/>[✅p-federation-name](./p-federation-name)<br/>[✅p-federation-profile](./p-federation-profile) | Apollo Federation Gateway<br/>                                                                                   |
| [✅o-metrics](./o-metrics)                                                                                                                                                                       | Example of metrics                                                                                               |
| [✅x-kotlin](./x-kotlin)                                                                                                                                                                         | Example of Kotlin                                                                                                | 
| [✅y-bff](./y-bff)                                                                                                                                                                               | Example of client and server，support voyager                                                                     | 
| [✅z-domain](./z-domain)                                                                                                                                                                         | Example of client and server，support voyager                                                                     |

## Intellij Idea Plugin

- [GraphQL](https://plugins.jetbrains.com/plugin/8097-graphql)
- [DGS](https://plugins.jetbrains.com/plugin/17852-dgs)

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

## m-dynamicschema

- Startup then visit http://localhost:10013/graphiql
- Input

```
query randomNumber {
  randomNumber(bound: 10)
}
------
mutation createUser {
  createUser(username: "hello", password: "world") {
    id
    username
    password
  }
}
```

## n-webflux

- Startup then visit http://localhost:10014/graphiql
- Input

```
query getUsers {
  getUsers {
    id
    username
    password
  }
  getUserById(id: 1) {
    id
    username
    password
  }
}

mutation createUser {
  createUser(username: "Trudy", password: "Trudy") {
    id
    username
    password
  }
}
```

## o-metrics

visit http://localhost:10015/actuator/metrics to check output

### Step 1

Use docker-compose to start Grafana and Prometheus servers.

- First generate jar in `/build/libs` folder
- In the root folder

```
docker-compose up -d
```

### Step 2

Check the Prometheus server.

- Open http://localhost:9090
- Access status -> Targets, endpoints must be "UP"

### Step 3

Configure the Grafana.

- Open http://localhost:3000, user name and password are all `admin`
- Configure integration with Prometheus
    - Access configuration
    - Add data source
    - Select Prometheus
    - Use url "http://host.docker.internal:9090" and access with value "Server(default)"
- Configure dashboard

## p-gateway

### Step 1

Start `customer`,`name`,`profile` services

### Step 2

Start `Apollo Gateway`

```
npm install

node index.js
```

### Step 3

Visit http://localhost:4000

Variables is

```
{
  "customerId": "1"
}
```

Query is

```
query Customer($customerId: String!) {
  customer(customerId: $customerId) {
    age
    id
    name {
      firstName
      fullName
      lastName
      middleName
      prefix
    }
    profile {
      email
      phone
    }
  }
}
```

## x-kotlin

- Input

```
{
	shows {
    title
    releaseYear
	  id
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

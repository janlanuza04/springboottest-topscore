# Basic Java Spring Boot Test : Top Score Ranking

This code base is a Test Project for building a RESTful API using Spring Boot.
The project feature is for keeping scores for certain players/s.
To test the API, curl or postman can be used to make HTTP request.

## Technologies, Libraries used:
* Java 8
* Gradle 7+
* Spring Boot 2.6+
* H2 Database
* Specification Argument Resolver by net.kaczmarzyk

## To build, test and run the project:
* Download and unzip the project in a directory
* To build a stand-alone jar, run below command in the project home directory
	`gradlew bootJar`
* To run the stand-alone jar  
			`java -jar {jarname}`
* To boot run project, run below command in the project home directory  
	`gradlew bootRun`
* To run test, run below command in the project home directory  
	`gradlew test`

## Notes:
* The API when running will use default port 8080
* Date format used in JSON output is in yyyy-MM-dd HH:mm:ss
* Date format used in URL input parameter is yyyy-MM-dd
* The API currently has no error handling, errors are by Spring Boot default error trace
* The project loads 10 initial data for testing as found in data.sql resource  
![H2SampleInitialData](https://user-images.githubusercontent.com/101033162/156952428-ad747253-0ec4-421a-a5dd-57079141cfd9.JPG)


## Functionalities:
This test project has below available functionalities
1. Getting all recorded scores for all players available. The API will return a JSON data.
* CURL Request  
```unix
curl --request GET http://localhost:8080/playerscores
```
* Postman Request
![PostManListAll](https://user-images.githubusercontent.com/101033162/156952907-d855df55-4193-436b-8ea0-3736b2691525.JPG)

* JSON Data Response  
![PostManListAllResponse](https://user-images.githubusercontent.com/101033162/156957023-5a0c0783-0291-403f-bcbb-02e95ce648b1.JPG)

2. To add new score record for a player. JSON payload are sent in request to record a score for a player. The API response returns HTTP OK

* CURL Request  
```unix
curl --request POST -d '{"name":"TEST4","score":78,"scoreDate":"2010-02-19 17:00:00"}' -H 'Content-Type: application/json' http://localhost:8080/playerscores
```
* Postman Request
![PostManSavePlayerScore](https://user-images.githubusercontent.com/101033162/156953415-33daf179-7e2e-47da-a21c-f3f7d9d61501.JPG)

* HTTP OK Response  
![PostManSavePlayerScoreResponse](https://user-images.githubusercontent.com/101033162/156953434-15fdb635-885d-4fbe-9a48-b8a99d03302d.JPG)

3. To get score record using score ID in request. The API will return a JSON data containing the score record based on the ID.
* URL Request Format  
`http://localhost:8080/playerscores/{id}`
* CURL Request
```unix
curl --request GET http://localhost:8080/playerscores/1
```
* Postman Request
![PostManGetById](https://user-images.githubusercontent.com/101033162/156953756-dc307420-ae5a-402d-af36-da6142684315.JPG)

* JSON Data Response
![PostManGetByIdResponse](https://user-images.githubusercontent.com/101033162/156957051-4853d941-7256-43e7-9d1a-d0d6426dc016.JPG)

4. To delete a score record using score ID. The API will return 200 OK http response if record is successfully deleted. URL Request is same with getting score record but HTTP request will be DELETE.
* CURL Request
```unix
curl --request DELETE http://localhost:8080/playerscores/1
```
* Postman Request
![PostManDeleteById](https://user-images.githubusercontent.com/101033162/156953969-156b058c-7d57-4fd4-b91a-79618e111382.JPG)

* HTTP OK Response
![PostManDeleteByIdResponse](https://user-images.githubusercontent.com/101033162/156953987-43a6ed14-d9b5-40aa-9f14-a5e12d4806fa.JPG)

5. To get all score record history of a player name. The API will return a JSON data containing: top score record, low score record, average score, and all score record of the player name.
* URL Request Format  
`http://localhost:8080/playerscores/history/{name}`
* CURL Request
```unix
curl --request GET http://localhost:8080/playerscores/history/test1
```
* Postman Request
![PostManPlayerScoreHistory](https://user-images.githubusercontent.com/101033162/156954135-0cd95baf-f4da-4b6f-ac30-ad8c025bef58.JPG)

* JSON Data Response
![PostManPlayerScoreHistoryResponse](https://user-images.githubusercontent.com/101033162/156957110-6a56d542-2e78-4200-aff8-f17e993e6a0a.JPG)

7. To search score record using certain search parameters. The API will return JSON data containing score record of the player based on the URL parameter.
Available search parameter  
  * Search by player name. The name parameter can contain multiple player name in comma separated format. Can be joined with other search parameter.
    * URL Request Format  
    `http://localhost:8080/playerscores/search?name={value}`
    * CURL Request
    ```unix
    curl --request GET http://localhost:8080/playerscores/search?name=test1
    ```
    * Postman Request
    ![PostManSearchBetweenDates](https://user-images.githubusercontent.com/101033162/156955384-4c16be52-1341-4973-8367-33e827711078.JPG)

    * Response
    ![PostManSearchNameResponse](https://user-images.githubusercontent.com/101033162/156957152-d5b00172-f848-4bb5-8889-1f6cd294203d.JPG)

* Search by after a certain date.
  * URL Request Format  
  `http://localhost:8080/playerscores/search?dateAfter={yyyy-MM-dd}`
  * CURL Request
  ```unix
  curl --request GET http://localhost:8080/playerscores/search?dateAfter=2012-01-12
  ```
  * Postman Request
  ![PostManSearchDateAfter](https://user-images.githubusercontent.com/101033162/156956184-bdf20f83-194a-47d3-8d33-0d3aa82c500d.JPG)

  * Response
  ![PostManSearchDateAfterResponse](https://user-images.githubusercontent.com/101033162/156957174-2de2af7e-88a0-4140-8d48-17c2ed303857.JPG)

* Search by before a certain date.
  * URL Request Format  
  `http://localhost:8080/playerscores/search?dateBefore={yyyy-MM-dd}`
  * CURL Request
  ```unix
  curl --request GET http://localhost:8080/playerscores/search?dateBefore=2021-03-12
  ```
  * Postman Request
  ![PostManSearchDateBefore](https://user-images.githubusercontent.com/101033162/156956212-30f12bd4-91f0-4387-b41a-552fd6c50a57.JPG)

  * Response
  ![PostManSearchDateBeforeResponse](https://user-images.githubusercontent.com/101033162/156957199-cd56d155-d2f9-45cf-a80d-3560f09d94df.JPG)

* Search by between dates by combining the two parameter for after and before date search.
  * URL Request Format  
  `http://localhost:8080/playerscores/search?dateAfter={yyyy-MM-dd}&dateBefore={yyyy-MM-dd}`
  * CURL Request
  ```unix
  curl --request GET http://localhost:8080/playerscores/search?dateAfter=2021-03-12&dateBefore=2021-05-12
  ```
  * Postman Request
  ![PostManSearchBetweenDates](https://user-images.githubusercontent.com/101033162/156956231-a06aa042-3e47-42fc-b6b8-596947727ce7.JPG)

  * Response
  ![PostManSearchBetweenDatesResponse](https://user-images.githubusercontent.com/101033162/156957218-9fcbf170-e1db-409f-92e2-27d5644fe50d.JPG)

* Pagination option in searching.By adding pageNo and pageSize other search parameter can be pageable. By default the pageNo is 0 and pageSize is 100.
  * URL Request Format  
  `http://localhost:8080/playerscores/search?{otherparam}&pageNo={value}&pageSize={value}`
  * CURL Request
  ```unix
  curl --request GET http://localhost:8080/playerscores/search?name=test1&pageNo=0&pageSize=1
  ```
  * Postman Request
  ![PostManSearchNamePageable](https://user-images.githubusercontent.com/101033162/156956261-5f3f62e9-1421-459c-8136-a84c58af1168.JPG)

  * Response
  ![PostManSearchNamePageableResponse](https://user-images.githubusercontent.com/101033162/156957238-fe3ce86c-eee7-4f17-a3e7-20692af8970d.JPG)

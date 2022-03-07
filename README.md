# Basic Java Spring Boot : Top Score Ranking

This code base is a Test Project for building a RESTful API using Spring Boot.
The project feature is for keeping scores for certain players/s.

## Technologies, Libraries used:
* Java 8
* Gradle 7+
* Spring Boot 2.6+
* H2 Database
* Specification Argument Resolver by net.kaczmarzyk

## To build, test and run the project:
* Download and unzip the project in a directory
* To build a stand-alone jar, run below command in the project home directory
	* gradlew bootJar
	* To run the stand-alone jar
		java -jar <jarname>
* To boot run project, run below command in the project home directory
	gradlew bootRun
* To run test, run below command in the project home directory
	gradlew test

## Notes:
* The API when running will use default port 8080
* The project loads 10 initial data for testing as found in data.sql resource

## Functionalities:
This test project has below available functionalities
1. Getting all recorded scores for all players available.
* URL Request
http://localhost:8080/playerscores
* URL Response
2. To add new score record for a player. JSON payload are sent in request to record a score for a player. The API response returns a JSON data
* URL Request

* URL Response

3. To get score record using score ID in request. The API will return a JSON data containing the score record based on the ID
* URL Request

* URL Response

4. To delete a score record using score ID. The API will return 200 OK http response if record is successfully deleted.

* URL Request

* URL Response
5. To get all score record history of a player name. The API will return a JSON data containing: top score record, low score record, average score, and all score record of the player name.

* URL Request

* URL Response

6. To search score record using certain search parameters. The API will return JSON data containing score record of the player based on the search parameter.
Available search parameter
	a. Search by player name
  1. The name parameter can contain multiple player name in comma separated format, currently this API Request is case sensitive
	b. Search by after a certain date
	c. Search by before a certain date
	d. Search by between dates
	e. Pagination option in searching

VOTING WEB APP:

this application is a voting web application.

features:

register and log in a voter (using JWT)
(id name password region)

view upcoming elections 
(election id, election date)

find the appropriate ballot (with polls you are eligible to participate in)
(a ballot has an election id, a ballot id, regionId, and the poll question ids that it contains);

submit poll answers
(a poll question contains a poll id, a question string, an answer string)

options table:
(int optionId, string optionValue, fk pollId)

region table
(regionId, region name);

votes table
(voter Id, pollId, string optionValue)

view poll results for old, active and future polls. 

Types of Poll questions:

ranked list: give candidates a ranking, can rank 0 to all of the candidates

yes or no: 0 or 1

multiple choice: choose one of many options, with possibility to write in. A write-in must create a row in the options table if it is unique. 

HOW TO RUN:

git clone https://github.com/williapa/voting.git

in root of project, run:

	mvn clean install 

then, check the console output for the location of the .jar file. To start the server, run:

	java -jar "C:\path\to\voting\target\votingApp-0.0.1-SNAPSHOT.jar"

In eclipse, you can also right click the Application.java class and select "Run spring boot app" or "Run java application".

All data is transferred via json through the /api/ routes. localhost:8080/ serves the single html page for the application. Using ng-route, we will make it so that different routes serve different views, but the only transferred HTML file is the index.html.

Database: currenty the application uses an in-memory H2 database. This allows for rapid development. When it is time to switch to a more permanent solution, you can easily change the jdbc driver url and connect to a standalone database of any kind. 

To run SQL for the in memory database (Eventually there will be an init.sql to populate with test data), you can go to localhost:8080/h2-console/ and click "connect" with the default options. This is a good way to monitor the database during development.

/login - > /elections - > /ballots - > /questions -> /results (admin page if admin)

/login 

login form (this is the header on every page, if you are logged in, you have a jwt in local storage and it will populate the header)

/election

landing page. links to every election page

/ballots

every ballot for that election also listing region and ballotId

/questions

every question for that ballot with the question body, candidate options, question type. Everyone can still view this, it only requires your /login JWT
to be able to cast a ballot (meaning: post your list of votes to the votes table)

/results

shows the number of votes for each poll question, regardless of region. also has a link for every table's "get" and "create", use the current index.html 
as reference for /results but requiring admin jwt. 

todo: enforce vote limits with ng-change or and/or on server

todo: protect API routes

todo: create results api that returns # of votes per candidate for each question in that electino.

todo: figure out how to do runoff question. 

todo: show logged in name, links for results

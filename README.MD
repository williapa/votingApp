# VOTING WEB APP:

this application is a simple voting web application.

## HIGH LEVEL FEATURES:

-Register voters
-Sign in a voter
-View upcoming elections
-Automatically assigned ballot with questions for your regional location
-Submit a ballot, with the option to resubmit multiple times (does not duplicate vote)
-Ballots can contain 3 types of question: yes or no, pick two, and instant runoff.
-For pick two and instant runoff, voters may submit a write in candidate.
-View results for all votes cast
-View winner for a question.

## DATA MODEL

VOTER
long id (pk)
string name (unique)
string password 
long regionId (fk)

VOTE
long id (pk)
long candidateId(fk)
long questionId(fk)
long ballotId(fk)
long voterId (fk)
long electionId (fk)
long rank (default 0, only used for instant runoff votes)
String writein (optional, only used for writen candidates)

DELETEDVOTE
(same as vote, but for votes that were overwritten)

REGION
long id (pk)
String region

BALLOTQUESTION
long id (pk)
long ballotId (fk)
long questionId (fk)

QUESTION
long id (pk)
String body 
String type 

ELECTION
long id (pk)
date startDate 
date endDate

CANDIDATE
long id (pk)
String body
long questionId (fk)

BALLOT
long id (pk)
long regionId (fk)
long electionId (fk)


## SUMMARY OF TECHNOLOGIES USED: 

This is a spring boot application, serving a single jsp that is the basis for our angular application. The spring backend will use the spring data jpa "CrudRepository" interface to connect to an in memory H2 SQL database (sql implementation in java). This will enable us to annotate our domain objects as entities with ids, and our database is automatically created. We can use the h2 console in development to query our tables using sql with a friendly interface to debug or view raw data. Later, through simple application properties, we can configure a jdbc database connection to a persistent option.

Our Service classes will use JERSEY REST to expose our crud repository data as json, enforcing some business logic (you can use the annotation to enforce different types of key relationships, such as many to one, one to many, etc. for this example, I used a simpler approach, making sure each table had a unique ID.) The angular SPA will request this data via ajax and then suitably present any data.

Each Jersey service class has an annoted method @postconstruct which runs following the creation of the database tables. In this class, I generate the initial data for the application, which includes some users, 5 different regions and 5 different ballots, each with 3 shared nation questions and a unique regional question. Questions are associated to multiple ballots via a lookup table BallotQuestion. Elections specify start and end dates. The application will only allow votes posted between these dates. Note that while the application does not currently allow for an admin role to post new questions, candidates, ballot questions, etc, most of the api routes are implemented.

Security: votes cannot be submitted without a saved Voter Web Token. the voter web token is saved in local storage upon successful signin or registration. This is a custom implementation, more mature approaches use a 2-token system, one which is used to enforce a timeout of the main token.  Our angular application will look for this token and attach the unique encrypted identifier in the token, generated from a value that is not exposed to users, to each request header. When votes are posted to the server, the server decrypts the request header and verifies that it corresponds with the voter Id for the submitted votes. All other routes are not protected. A user who is not logged in will be shown the results page instead of a ballot. 

Votes are always anonymous. in the database, an id is connected with the vote to ensure a limit of votes per question per voter. However, at no point will the application show a cast vote associated with any voter identification. 

## ROUTES: 

/ (home)
login and register. Successful submission links to 

/elections
view upcoming elections. if logged in, clicking a row links to your ballot based on your region. Else, skips to results

/ballot/{id}
redirected to results if no voter web token. Ballot displayed based on provided ballot id, in order 
to show regional questions. After voting, the user is directed to 

/results/{electionid}
results page contains every vote cast, listed by vote id (arbitrary index), question name, candidate name, and rank. A ledger of votes, we can extend this page to tally results.
clicking a question name will link to 

/winner/{questionid}
the winner page will show the tallied number of votes for each candidate for the question, listed from greatest to least. 

On any page, a user can log out and return to / (Home). 


###TODO:

todo: protect API routes (yes) 

todo: create results API that returns # of votes per candidate for each question in that election (yes) 

todo: enforce data for posted votes

todo: add date to vote

todo: winner 

todo: junit & nightwatch tests

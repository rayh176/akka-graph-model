Overview
The project cover a REST API service, that provides a risk simulation API endpoint that receives brand value,
and calls an internal API service to perform statistical analysis of that data and return the results.
1- Complex event processing used with AKKA -
2- Results are stored in the cache
3- Data returned is an xml format via a web call

Remarque:
--------
1- Complex prediction turn off due to machine resources
2- No use of Cookies was added.
3- Convert Json to Html page not finished
4- I didn't add Cucumber or more tests due to lack of time
5- Disruptor was considered but not used

Technologies
------------
Java 1.8
Spring 4.x.x
Spring boot
EhCache
Akka
Rest
Maven 3.x.x

Program
------
The characteristics of Akka allow me to solve the difficult concurrency and scalability challenges.
Program adhere to strong isolation principles.
Spring helped to reduce classes dependencies.

Inputs
------
Brand_Factor expected values ranging from (0.1 -> 2.9)

The Model
------------
For 15 years, in 1 year increments.

Age = Age + 1
If (Auto_Renew) {
    // Do nothing, maintain Breed<
} else {
    double rand = Math.random() * 3;
    Affinity = Payment_at_Purchase/Attribute_Price + (rand * Attribute_Promotions * Inertia_for_Switch)
    If (Breed == Breed_C && Affinity < (Social_Grade * Attribute_Brand))
        Switch Breed to Breed_NC
    Else if (Breed == Breed_NC && Affinity < (Social_Grade * Attribute_Brand * Brand_Factor))
        Switch Breed to Breed_C
}

Outputs
-------
For every year, and final:

Breed_C Agents
Breed_NC Agents
Breed_C Lost (Switched to Breed_NC)
Breed_C Gained (Switch from Breed_NC)<
Breed_C Regained (Switched to NC, then back to C)

Edit
----
Project with any IDE but preferably IntelliJ

Build project
-------------
mvn clean install

Start the server
----------------
[please have Java on PATH] - run the below
./startUp.sh

Stop Server
Please just use CTRL + C - Shutdown hook already added to program to shut gracefully

Run the client
--------------

 Command Line
 ------------
    curl -v -H "Content-Type: application/json" http://localhost:3000/risk-measures/0.2

 Web
 ---
 http://localhost:3000/risk-measures/0.2




If require more discussion - please don't hesitate to contact me on
rayh176@gmail.com
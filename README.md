# JSF_LSD
First iteration of hackernews (hand-in for 2.11.2017)

Valid username: "Henning" and password: "secret" for the system.

Link for hackernews-application:
http://52.88.77.103/hackernews/

Links for REsT services on hackernews:
http://52.88.77.103/hackernews/resources/post
http://52.88.77.103/hackernews/resources/latest
http://52.88.77.103/hackernews/resources/alive

It should be mentioned that some of the navigation in the system is not working optimally at present. 
When a user presses the "Logout" link, no redirect to the landing-page is happening (as intended) and no logout-message is displayed, 
although the user is actually logged out (by refreshing the browser, this should become apparent).
This issue is seemingly caused by mis-configuration of the Glassfish server hosting the system, and will be addressed ASAP.

I wasn't able to successfully run the Python-script (student_tester.py) against the system. 
The error message "Hov it seems I cannot connect to your system" was displayed. This exception will also be further pursued.
I was however able to successfully make an HTTP-POST against "http://52.88.77.103/hackernews/resources/post" and persist the jSon-objects 
provided in the file, "03-Minimum_Requirements_and_API_Description.md" at github. 

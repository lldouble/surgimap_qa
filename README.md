#####
1. Abstract:
Package to execute the Surgimap QA / Test Automation exercises.
Implented Exercises: 1, 2 and 4.

2. Prerequirements:
a) Java 1.8.x
b) Maven (configured properly with Java)
c) Internet connection
d) Google Chrome browser
e) Selenium Chrome Driver (download here: http://chromedriver.chromium.org/downloads and unzip and store somewhere safe the executable)
d) *IMPORTANT:* please provide the selenium chrome driver full path (from step 7) into the file "locators.properties", at the property "selenium.chrome.driver.executable.path" (see the current property value for an example)

3. To run the test cases:
a) Open a Command Line
b) Go where this project was checked out;
c) Run "mvn clean verify" > The screen command line output will provide the test logs and information

4. Some information:
The test cases for the exercises are in the package: /src/test/java/test/
The resources folder contains the dictionary for the Exercise 1 and a property file with the locators for Exercise 2.
The package /src/main/java/config contains a test configuration file to read the property file
The package /src/main/java/pages contains the POM of the page(s) needed in test case 2
The package /src/main/java/utilities contains some utility methods (e.g. access a gmail account and read email)

5. Simplifications
Test Logs running on System.out for a matter of time, instead than proper logging.
Test Automation only tested to run in desktop mode (just for a matter of time, I could have chosen mobile with the same effort)
Driver lifecycle handling simplified (open, close, ...).

6. Important note:
Make sure to use latest Chrome Driver (at least 2.43.0) and Chrome (at least 70.0).
The test suite is using MailSlurp as an external (Rest) API to generate mailboxes and check received emails into them.
Since this step is using an external service that could be unavailable at any time and it is not in our control, this
step is executed in a "best effort way", so that if it fails due to the service not avaiable at the moment of the test
execution, it will not make the test case fail but just provide a WARNING in the logs to invite the tester to complete
this step (email verification) manually.
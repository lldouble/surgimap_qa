#####
1. Abstract:
Package to execute the Surgimap QA / Test Automation exercises.
Implented Exercises: 1, 2 and 4.

2. Prerequirements:
a) Java 1.8.x
b) Maven (configured properly with Java)
c) Internet connection
d) Google Chrome browser
e) Selenium chrome driver up and running (download here: http://chromedriver.chromium.org/downloads)

3. To run the test cases:
a) Open a Command Line
b) Go where this project was checked out;
c) Run "maven clean verify" > The screen command line output will provide the test logs and information

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
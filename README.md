# Spring4Samples
==============
## Description:
Some code samples showing some of the Spring 3.*/4 features
==============
## How to build:

### Prerequisites:
You should have Oracle jdbc driver library installed in your local Maven repo:
'
<dependency>
    <groupId>oracle</groupId>
    <artifactId>jdbc</artifactId>
    <version>11.2.0</version>
    <scope>runtime</scope>
</dependency>
'
Check repositories section in build.gradle file to point correctly to your local Maven repo.

### Maven build with running tests:
'mvn clean install'

### Gradle build with running tests:
'mvn clean build'
==============
## How to import in your IDE:

### IntellijIDEA:
1) Import as Maven project
or
2) 'mvn idea:idea' and import as IDEA project

### Eclipse:
1) Import as Maven project using m2eclipse plugin
2) 'mvn eclipse:eclipse' and import as Eclipse project





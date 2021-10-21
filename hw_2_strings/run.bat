call cd ..\reversestring
call mvn clean install
call cd ..\hw_2_strings
call mvn clean install
call java -jar .\target\hw_2_strings.jar

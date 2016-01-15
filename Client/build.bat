@echo off
cd src
javac -d ..\bin -classpath .;..\jar\jsonAnalyzer.jar client\*.java jsonAnalyzer\*.java data\*.java ui\*.java
pause
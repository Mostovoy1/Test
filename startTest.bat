set in=%~dp0in.txt
echo "%cd%">"%in%"
mvn exec:java
set MAVEN_OPTS=-XX:+TieredCompilation -XX:TieredStopAtLevel=1 -DdependencyLocationsEnabled=false 
mvn -T 4 clean install
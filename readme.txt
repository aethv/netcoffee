To build project, execute command at parent pom.xml:
1. build artifacts		: mvn clean install
2. start server 		: mvn -pl netcoffee-server exec:java -Dexec.mainClass="com.clinet.Main" -e
3. start client 		: mvn -pl netcoffee-client exec:java -Dexec.mainClass="com.clinet.Main" -e
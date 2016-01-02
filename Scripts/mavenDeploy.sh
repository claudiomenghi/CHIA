cd CHIALogging
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAAction
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAAutomata
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAAutomataIO
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAChecker
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAConstraint
mvn clean install sonar:sonar 
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAConstraintIO
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAConstraintComputation
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIALTLIO
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAReplacementChecker
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
cd ..
cd CHIAFramework
mvn clean install sonar:sonar
mvn clean deploy
mvn javadoc:javadoc
mvn package
cd ..
mvn site

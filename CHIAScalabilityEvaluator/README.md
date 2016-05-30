# Running the scalability test

To run the scalbility test donwload the ```EX1.java```, ```EX2.java```, ```EX3.java``` or ```EX3b.java```

Then, run the command.

```java -jar -Xss1024M -Xmx10g Ex3.jar ./config.txt ./Claims```

where 

* ```config.txt``` is the configuration file which contains the information for the random generation of the model
* ```./Claims``` is the folder containing the claims of interest

The configuration files and the claims used in our experiments are contained in the folder ```src/main/resources/```

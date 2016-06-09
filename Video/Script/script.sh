#!/bin/sh
clear
start=$SECONS
alias echon="echo "

echo ">\c"
say "Let's see how CHIA works in practice"
say "CHIA is a prototype tool realized as a Java 7 stand-alone application."
say "After executing the command java minus jar CHIA dor jar, the command line application is started"
echo "java -jar CHIA.jar"
sleep 0.5

sh ./chiaPART1.sh  | java -jar CHIA.jar
echo ""
echo ">\c"
echo "java -jar CHIA.jar"
sh ./chiaPART2.sh  | java -jar CHIA.jar

duration=$(( SECONDS - start ))
printf '%dh:%dm:%ds\n' $(($duration/3600)) $(($duration%3600/60)) $(($duration%60))

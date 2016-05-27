clear
% the folder where the results of the tests are stored
testFolder='Ex3b';
% the number of the tests
testNumber=20;
% the number of the claims condidered in the tests
claimNumber=3;


replacementDensityColum=8;
initialReplacementDensityTransitions=0.1;
finalReplacementDensity=0.5;
incrementReplacementDensity=0.2;

plugColum=10;
initialStatesNumber=100;
incrementStatesNumber=100;
finalStatesNumber=1000;
statesSteps=10;

task3SatisfactionColumn=11;
task4SatisfactionColumn=12;

task3TimeColumn=15;
task4TimeColumn=16;

%------------------------------------------------------------------------------
% PERCENTAGE RESULTS
%------------------------------------------------------------------------------
% Test1 = Y, Test2=Y
%case1Number=0;
% Test1 = N, Test2=N
%case2Number=0;

cases=zeros(2, claimNumber);
total=zeros(1, claimNumber);
for currentTest=1:testNumber
    for currentClaim=1:claimNumber
        currentFile=strcat(testFolder, filesep, 'Test', num2str(currentTest), filesep, 'Claim', num2str((currentClaim-1)), filesep, 'results.txt');
        fid = fopen(currentFile);
        if(fid==-1)
            disp(strcat('error in opening the file: ', currentFile));
        else
            tline = fgetl(fid);
            
            while ischar(tline) 
                
                record=strsplit(tline,'\t');
                    if strcmp(record(task3SatisfactionColumn),'Y') && strcmp(record(task3SatisfactionColumn),'Y')
                     cases(1, currentClaim)=cases(1, currentClaim)+1;
                    end
                    if strcmp(record(task4SatisfactionColumn),'N') && strcmp(record(task4SatisfactionColumn), 'N')
                     cases(2, currentClaim)=cases(2, currentClaim)+1;
                    end
               
                    total(1,currentClaim)=total(1,currentClaim)+1;
                      tline = fgetl(fid);
                
            end
            fclose(fid);
        end
    end
end
percentages=[cases(1,:)./(cases(1,:)+cases(2,:)); cases(2,:)./(cases(1,:)+cases(2,:))];
disp(percentages);


%------------------------------------------------------------------------------
% TIME RESULTS
%------------------------------------------------------------------------------

initialNumberPluggingTransitions=2;
finalNumberPluggingTransitions=5;
incrementNumberPluggingTransitions=1;

plugSteps=(finalNumberPluggingTransitions-initialNumberPluggingTransitions+incrementNumberPluggingTransitions)/incrementNumberPluggingTransitions;


plugResults=zeros(plugSteps, statesSteps);
plugNumber=zeros(plugSteps, statesSteps);

numReplacementDensitySteps=int8((finalReplacementDensity+incrementReplacementDensity-initialReplacementDensityTransitions)/incrementReplacementDensity);
replacementDensityResults=zeros(numReplacementDensitySteps, statesSteps);
replacementDensityNumber=zeros(numReplacementDensitySteps, statesSteps);

replacementDensityResultsFalse=zeros(numReplacementDensitySteps, statesSteps);
replacementDensityNumberFalse=zeros(numReplacementDensitySteps, statesSteps);

replacementDensityResultsTrue=zeros(numReplacementDensitySteps, statesSteps);
replacementDensityNumberTrue=zeros(numReplacementDensitySteps, statesSteps);

casetime=zeros(4, statesSteps);
number=zeros(4, statesSteps);
case1number=0;
case2number=0;
for currentTest=1:testNumber
    for currentClaim=1:claimNumber
        currentFile=strcat(testFolder, filesep, 'Test', num2str(currentTest), filesep, 'Claim', num2str((currentClaim-1)), filesep, 'results.txt');
        fid = fopen(currentFile);
        if(fid==-1)
            disp(strcat('error in opening the file: ', currentFile));
        else
            tline = fgetl(fid);

            while ischar(tline) 
                record=strsplit(tline,'\t');
                
                    currStateNum=cell2mat(record(4));

                    % +1 nessary since the index starts from 1
                    col=(str2num(currStateNum)-initialStatesNumber)./incrementStatesNumber+1;

                    task3Time=str2num(cell2mat(record(task3TimeColumn)));
                    task4Time=str2num(cell2mat(record(task4TimeColumn)));
                    casetime(1, col)=casetime(1, col)+task3Time/task4Time;
                    number(1, col)=number(1, col)+1;

                    plugTransitionNumber=str2num(cell2mat(record(plugColum)));
                    plugTableIndex=(plugTransitionNumber-initialNumberPluggingTransitions+incrementNumberPluggingTransitions)./incrementNumberPluggingTransitions;
                    plugResults(plugTableIndex, col)=plugResults(plugTableIndex, col)+task3Time/task4Time;
                    plugNumber(plugTableIndex,col)=plugNumber(plugTableIndex,col)+1;

                    currentReplacementDensityNumber=str2num(cell2mat(record(replacementDensityColum)));
                    replacementDensityTableIndex=int8((currentReplacementDensityNumber-initialReplacementDensityTransitions+incrementReplacementDensity)/incrementReplacementDensity);
                    replacementDensityResults(replacementDensityTableIndex, col)=replacementDensityResults(replacementDensityTableIndex, col)+task3Time/task4Time;
                    replacementDensityNumber(replacementDensityTableIndex,col)=replacementDensityNumber(replacementDensityTableIndex,col)+1;


                    if strcmp(record(task3SatisfactionColumn),'Y') && strcmp(record(task3SatisfactionColumn),'Y')
                        replacementDensityResultsTrue(replacementDensityTableIndex, col)=replacementDensityResultsTrue(replacementDensityTableIndex, col)+task3Time/task4Time;
                        replacementDensityNumberTrue(replacementDensityTableIndex,col)=replacementDensityNumberTrue(replacementDensityTableIndex,col)+1;

                    end
                    if strcmp(record(task4SatisfactionColumn),'N') && strcmp(record(task4SatisfactionColumn), 'N')
                        replacementDensityResultsFalse(replacementDensityTableIndex, col)=replacementDensityResultsFalse(replacementDensityTableIndex, col)+task3Time/task4Time;
                        replacementDensityNumberFalse(replacementDensityTableIndex,col)=replacementDensityNumberFalse(replacementDensityTableIndex,col)+1;

                    end
                     tline = fgetl(fid);
               
            end
            fclose(fid);
        end
    end
end


xValues=initialStatesNumber:incrementStatesNumber:finalStatesNumber;

replacementDensityAverage=replacementDensityResults(:,:)./replacementDensityNumber(:,:);

replacementDensityAverageTrue=replacementDensityResultsTrue(:,:)./replacementDensityNumberTrue(:,:);

replacementDensityAverageFalse=replacementDensityResultsFalse(:,:)./replacementDensityNumberFalse(:,:);

PlotEx3b

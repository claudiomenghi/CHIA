clear
% the folder where the results of the tests are stored
testFolder='Ex3';
% the number of the tests
testNumber=20;
% the number of the claims condidered in the tests
claimNumber=3;

initialStatesNumber=100;
incrementStatesNumber=100;
finalStatesNumber=1000;
statesSteps=(finalStatesNumber-initialStatesNumber+incrementStatesNumber)/incrementStatesNumber;

task1SatisfactionColumn=10;
task3SatisfactionColumn=11;

task1TimeColum=14;
task2TimeColum=15;
%------------------------------------------------------------------------------
% PERCENTAGE RESULTS
%------------------------------------------------------------------------------
% Test1 = Y, Test2=Y
%case1Number=0;
% Test1 = Y, Test2=M
%case2Number=0;
% Test1 = N, Test2=N
%case3Number=0;
% Test1 = N, Test2=M
%case4Number=0;
cases=zeros(4, claimNumber);
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
               
                if strcmp(record(task1SatisfactionColumn),'Y') && strcmp(record(task3SatisfactionColumn),'Y')
                    cases(1, currentClaim)=cases(1, currentClaim)+1;
                end
                if strcmp(record(task1SatisfactionColumn), 'M') && strcmp(record(task3SatisfactionColumn), 'M')
                    cases(2, currentClaim)=cases(2, currentClaim)+1;
                end
                if strcmp(record(task1SatisfactionColumn),'N') && strcmp(record(task3SatisfactionColumn), 'N')
                    cases(3, currentClaim)=cases(3, currentClaim)+1;
                end
               
                total(1,currentClaim)=total(1,currentClaim)+1;
                tline = fgetl(fid);
            end
            fclose(fid);
        end
    end
end
percentages=[cases(1,:)./(cases(1,:)+cases(2,:)+cases(3,:)); cases(2,:)./(cases(1,:)+cases(2,:)+cases(3,:)); cases(3,:)./(cases(1,:)+cases(2,:)+cases(3,:)) ];
disp(percentages);


%------------------------------------------------------------------------------
% TIME RESULTS
%------------------------------------------------------------------------------

casetime=zeros(4, statesSteps);
number=zeros(4, statesSteps);
case1number=0;
case2number=0;
case3number=0;
case4number=0;
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
                task1Time=str2num(cell2mat(record(task1TimeColum)));
                task2Time=str2num(cell2mat(record(task2TimeColum)));
                
                if strcmp(record(task1SatisfactionColumn), 'Y') && strcmp(record(task3SatisfactionColumn), 'Y')
                    casetime(1, col)=casetime(1, col)+task1Time/task2Time;
                    number(1, col)=number(1, col)+1;
                end
                if strcmp(record(task1SatisfactionColumn), 'M') && strcmp(record(task3SatisfactionColumn), 'M')
                    casetime(2, col)=casetime(2, col)+task1Time/task2Time;
                    number(2, col)=number(2, col)+1;
                end
                if strcmp(record(task1SatisfactionColumn), 'N') && strcmp(record(task3SatisfactionColumn), 'N')
                    casetime(3, col)=casetime(3, col)+task1Time/task2Time;
                    number(3, col)=number(3, col)+1;
                end
                total(1,currentClaim)=total(1,currentClaim)+1;
                tline = fgetl(fid);
            end
            fclose(fid);
        end
    end
end

average=[casetime(1, :)./number(1,:); casetime(2, :)./number(2,:); casetime(3, :)./number(3,:)];

xValues=initialStatesNumber:incrementStatesNumber:finalStatesNumber;
PlotEx3
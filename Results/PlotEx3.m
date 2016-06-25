xValues=initialStatesNumber:incrementStatesNumber:finalStatesNumber;
ex3plot=figure('Name','Ex3');

fontSize=22;
set(ex3plot,'Units','Inches');
set(ex3plot, 'Position', [0 0 6.5 3.5]);

pos = get(ex3plot,'Position');
set(ex3plot,'PaperPositionMode','Auto','PaperUnits','Inches','PaperSize',[pos(3), pos(4)])

plot(xValues, average(1,:), 'k-o', xValues, average(2,:), 'k--o', xValues, average(3,:),  'k-+', 'LineWidth',2);
legend('C1', 'C2', 'C3', 'Location','northoutside','Orientation','horizontal');

yPos = 1;
hold on
plot(get(gca,'xlim'), [yPos yPos])


grid on;
xlabel('Number of states')
ylabel('Tr')
set(gca,'fontsize', fontSize);

set(gca,'ytick',[0:50:250]);
set(gca,'xtick',[100:200:1000]);
axis([100 1000 0 250])

print(ex3plot, 'EX3','-dpdf', '-r0');














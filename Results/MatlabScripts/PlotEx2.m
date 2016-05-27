xValues=initialStatesNumber:incrementStatesNumber:finalStatesNumber;
ex2plot=figure('Name','Ex2');

fontSize=22;
set(ex2plot,'Units','Inches');
set(ex2plot, 'Position', [0 0 6.5 3.5]);

pos = get(ex2plot,'Position');
set(ex2plot,'PaperPositionMode','Auto','PaperUnits','Inches','PaperSize',[pos(3), pos(4)])


plot(xValues, average(1,:), 'k-o', xValues, average(2,:), 'k--o', xValues, average(3,:), 'k-+', xValues, average(4,:), 'k--+', 'LineWidth',2);
legend('C1', 'C2', 'C3', 'C4', 'Location','northoutside','Orientation','horizontal');

grid on;
xlabel('Number of states')
ylabel('Tr')
set(gca,'fontsize', fontSize);

yPos = 1;
hold on
plot(get(gca,'xlim'), [yPos yPos])

set(gca,'ytick',[0:2:7]);
set(gca,'xtick',[100:200:1000]);
axis([100 1000 0 7])

print(ex2plot, 'EX2','-dpdf', '-r0');














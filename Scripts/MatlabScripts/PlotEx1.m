xValues=initialStatesNumber:incrementStatesNumber:finalStatesNumber;
ex1plot=figure('Name', 'Ex1');

fontSize=22;
set(ex1plot,'Units','Inches');
set(ex1plot, 'Position', [0 0 6.5 3.5]);

pos = get(ex1plot,'Position');

set(ex1plot,'PaperPositionMode','Auto','PaperUnits','Inches','PaperSize',[pos(3), pos(4)])

plot(xValues, average(1,:), 'k-o', xValues, average(2,:), 'k--o', xValues, average(3,:), 'k-+', xValues, average(4,:), 'k--+', 'LineWidth',2);
legend('C1', 'C2', 'C3', 'C4', 'Location','northoutside','Orientation','horizontal');
grid on;
xlabel('Number of states')
ylabel('Tr')
%set(gca,'fontsize', 18);
set(gca,'fontsize', fontSize);

yPos = 1;
hold on
plot(get(gca,'xlim'), [yPos yPos])

set(gca,'ytick',[0:1:3]);
set(gca,'xtick',[100:200:1000]);
axis([100 1000 0 3])
print(ex1plot, 'EX1','-dpdf', '-r0');









ex3bplot=figure('Name','Ex3b');

fontSize=22;


set(ex3bplot,'Units','Inches');
set(ex3bplot, 'Position', [0 0 8.5 3.5]);
pos = get(ex3bplot,'Position');
set(ex3bplot,'PaperPositionMode','Auto','PaperUnits','Inches','PaperSize',[pos(3), pos(4)])


hold on
hdlY=plot(xValues, replacementDensityAverageTrue(1,:), 'k-o', xValues, replacementDensityAverageTrue(2,:), 'k--o', xValues, replacementDensityAverageTrue(3,:),'k:o',xValues, replacementDensityAverageFalse(1,:), 'k-+', xValues, replacementDensityAverageFalse(2,:), 'k--+', xValues, replacementDensityAverageFalse(3,:),'k:+','LineWidth',2);

%gridLegend(hdlY, 3,'C1-0.1', 'C1-0.3', 'C1-0.5', 'C2-0.1', 'C2-0.3', 'C2-0.5','Location','northoutside','Orientation','horizontal', 'Fontsize', 22);

legend('C1,r=0.1', 'C1,r=0.3', 'C1,r=0.5', 'C3,r=0.1', 'C3,r=0.3', 'C3,r=0.5', 'Location','eastoutside','Orientation','vertical');

grid on;
xlabel('Number of states')
ylabel('Tr')

set(ex3bplot,'Units','Inches');

yPos = 1;
hold on
plot(get(gca,'xlim'), [yPos yPos])

set(gca,'fontsize', fontSize);
box on
set(gca,'ytick',[0:2:6]);
set(gca,'xtick',[100: 200: 1000]);
axis([100 1000 0 7.5])
print(ex3bplot, 'EX3b','-dpdf', '-r0');
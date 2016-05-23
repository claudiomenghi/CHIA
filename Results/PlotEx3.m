
average(2,6)=425

average(3,6)=42
average(2,7)=505
average(2,8)=580
average(2,9)=650
average(2,10)=720
average(3,7)=44.9800
average(3,8)=46.5432
average(3,9)=49.0123
average(3,10)=51.3723
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

set(gca,'ytick',[0:200:800]);
set(gca,'xtick',[100:200:1000]);
axis([100 1000 0 800])

print(ex3plot, 'EX3','-dpdf', '-r0');














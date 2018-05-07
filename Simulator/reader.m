load priceA.txt
pricesA = priceA(:,1);
figure;
plot(pricesA);
ylim([0,2.5]);
ylabel("Price ($)");
xlabel("Tick Number");
title("Price of Stock");

load salesA.txt
pricesA = salesA(:,1);
figure;
plot(pricesA);
ylabel("Number of Sales");
xlabel("Tick Number");
title("Sales of Stock");

load vendorA.txt
doritos = vendorA(:,1);
donuts = vendorA(:,2);
banana = vendorA(:,3);
sandwich = vendorA(:,4);
coke = vendorA(:,5);
snickers = vendorA(:,6);
juice  = vendorA(:,7);
carrots = vendorA(:,8);
figure;
plot(doritos);
hold on;
plot(donuts);
hold on;
plot(banana);
hold on;
plot(sandwich);
hold on;
plot(coke);
hold on;
plot(snickers);
hold on;
plot(carrots);
hold on;
plot(juice);
ylim([15,35]);
ylabel("Number of Items");
xlabel("Tick Number");
hold off;

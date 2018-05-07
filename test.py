# import numpy as np
# import matplotlib.pyplot as plt

# N = 50
# x = np.random.rand(N)
# y = np.random.rand(N)
# z = int(np.random.rand(N)*3);

# plt.scatter(x, y)
# plt.show()
from random import random
import numpy as np
import json
import matplotlib.pyplot as plt
from kmeans import Kmeans


COLORS = ['#FF0000', '#FF6600', '#FFFF00', '#00FF00', '#0000FF', '#8000FF', '#663300', '#FFFFFF', "#00FFFF"]


#Note: We use kmeans instead of EM because we want to hard assign a machine to a person. A person 
#   spends money only at one machine at a given time
def main():
    #TEST CODE
    #points = np.random.rand(5000,2)
    
    # points = []
    # for i in xrange(5000):
    #     points.append([40*random(), 20*random(), 1])

    # for i in xrange(5000):
    #   points.append([43*random() + 40, 25*random() + 20, 1])

    # k = 2

    # Kmeans(k, points)


    customerValue = [0]*1000

    pointsFile = open('/Users/Sean/Desktop/Classes/18651/Data/points.json')
    breakfastMovementFile = open('/Users/Sean/Desktop/Classes/18651/Data/breakfast.json')
    breakfastPurchaseFile = open('/Users/Sean/Desktop/Classes/18651/Data/breakfastbuy.json')
    lunchMovementFile = open('/Users/Sean/Desktop/Classes/18651/Data/lunch.json')
    lunchPurchaseFile = open('/Users/Sean/Desktop/Classes/18651/Data/lunchbuy.json')
    dinnerMovementFile = open('/Users/Sean/Desktop/Classes/18651/Data/dinner.json')
    dinnerPurchaseFile = open('/Users/Sean/Desktop/Classes/18651/Data/dinnerbuy.json')

    breakfastPurchaseData = json.load(breakfastPurchaseFile)["data"]
    for purchase in breakfastPurchaseData:
        customerValue[int(purchase["Client ID"])] += int(float(purchase["Price"]))

    lunchPurchaseData = json.load(lunchPurchaseFile)["data"]
    for purchase in lunchPurchaseData:
        customerValue[int(purchase["Client ID"])] += int(float(purchase["Price"]))

    dinnerPurchaseData = json.load(dinnerPurchaseFile)["data"]
    for purchase in dinnerPurchaseData:
        customerValue[int(purchase["Client ID"])] += int(float(purchase["Price"]))


    dataset = []

    print("Loading dataset...")
    lunchMovementsData = json.load(lunchMovementFile)["data"]
    for customer in lunchMovementsData:
        #Data is in x,y. We want row,col
        dataset.append([int(customer["X"]), - int(customer["Y"]) + 55, customerValue[int(customer["ID"])]])
    print("Done!")


#    Kmeans(2, dataframe[dataframe.columns[2:4]])
    Kmeans(4, dataset)




main()
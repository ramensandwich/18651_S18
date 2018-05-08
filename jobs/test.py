from random import random
import json
from kmeans import Kmeans


#Note: We use kmeans instead of EM because we want to hard assign a machine to a person. A person 
#   spends money only at one machine at a given time
def main():
    #In our dataset, our customers are id'd 0 to 999. Adjust this if dataset changes number of clients
    customerValue = [0]*1000

    pointsFile = open('../data/points.json')
    breakfastMovementFile = open('../data/breakfast.json')
    breakfastPurchaseFile = open('../data/breakfastbuy.json')
    lunchMovementFile = open('../data/lunch.json')
    lunchPurchaseFile = open('../data/lunchbuy.json')
    dinnerMovementFile = open('../data/dinner.json')
    dinnerPurchaseFile = open('../data/dinnerbuy.json')

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
    #Change the line below to use a different dataset
    lunchMovementsData = json.load(lunchMovementFile)["data"]
    for customer in lunchMovementsData:
        #Data is in x,y. We want row,col
        dataset.append([int(customer["X"]), - int(customer["Y"]) + 55, customerValue[int(customer["ID"])]])
    print("Done!")

    Kmeans(4, dataset)




main()
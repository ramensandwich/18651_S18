import json
import matplotlib.pyplot as plt
import pandas as pd

COLORS = ['#FF0000', '#FF6600', '#FFFF00', '#00FF00', '#0000FF', '#8000FF', '#663300', '#FFFFFF', "#00FFFF"]


def main():

    pointsFile = open('/Users/Sean/Desktop/Classes/18651/Data/points.json')
    breakfastMovementFile = open('/Users/Sean/Desktop/Classes/18651/Data/breakfast.json')

    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    ax1.axis([0, 83, 0, 55])
    pointsData = json.load(pointsFile)["Points"]
    movementData = json.load(breakfastMovementFile)

    #Draw all the points of interest first
    for chunk in pointsData:
        ax1.scatter(int(chunk["X"]), int(chunk["Y"]), c='#000000')

    dataframe = pd.DataFrame(movementData["data"]).astype(int)
    # x = dataframe["X"]
    # y = dataframe["Y"]

#c=df['color'].apply(lambda x: colors[x])

    for i in xrange(1000):
        chunk = dataframe[dataframe.Tick == (5400 + i)]
        x = chunk["X"]
        y = chunk["Y"]
        colors = chunk["ID"].apply(lambda x : COLORS[x % 9])
        ax1.scatter(x,y, alpha = 0.2, c=colors)
        plt.pause(0.1)
        plt.clf()
        print(i)
        ax1 = fig.add_subplot(111)
        ax1.axis([0, 83, 0, 55])

    # for i in xrange(1000):
    #     customers = movementData[0 + 1000*i : 999 + 1000*i]
    #     for customer in customers:
    #         print(customer.values())

    # for i in xrange(1000):
    #     asdf = [x for x in movementData if x["Tick"] == 5400 + i]
    #     for customer in asdf:
    #         ax1.scatter(int(customer["X"]), int(customer["Y"]), c=COLORS[int(customer["ID"]) % 9], alpha = 0.2)

    # for customer in movementData:
    #     print("X: " + customer["X"] + " Y: " + customer["Y"] + " Tick: " + customer["Tick"] + " ID: " + customer["ID"])
    #     ax1.scatter(int(customer["X"]), int(customer["Y"]), c=COLORS[int(customer["ID"]) % 9], alpha = 0.2)

    #     if int(customer["ID"]) == 999:
    #         #Reset the window 
    #         plt.pause(1)
    #         plt.clf()
    #         ax1 = fig.add_subplot(111)
    #         ax1.axis([0, 83, 0, 55])
    #         for chunk in pointsData:
    #             ax1.scatter(int(chunk["X"]), int(chunk["Y"]), c='#000000')

    plt.show()


main()
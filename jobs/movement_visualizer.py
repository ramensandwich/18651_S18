import json
import matplotlib.pyplot as plt
import pandas as pd

COLORS = ['#FF0000', '#FF6600', '#FFFF00', '#00FF00', '#0000FF', '#8000FF', '#663300', '#FFFFFF', "#00FFFF"]


def main():

    pointsFile = open('../data/points.json')
    #Change the below line to use a different dataset
    breakfastMovementFile = open('../data/breakfast.json')
    campus_map = plt.imread('../media/campus_map.png')

    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    ax1.imshow(campus_map, extent=[0, 83, 0, 55])
    pointsData = json.load(pointsFile)
    movementData = json.load(breakfastMovementFile)

    dataframe = pd.DataFrame(movementData["data"]).astype(int)
    pointsOfInterest = pd.DataFrame(pointsData["Points"]).astype(int)

    poiX = pointsOfInterest["X"]
    poiY = pointsOfInterest["Y"].apply(lambda y : -y + 55)
    ax1.scatter(poiX, poiY, c="#000000", marker="*")

    #We currently only run for 1000 timesteps, or about 1000*5seconds = about 1.5 hours in simulation time
    for i in xrange(1000):
        chunk = dataframe[dataframe.Tick == (5400 + i)]
        x = chunk["X"]
        #y to row:
        #row = -y + 55
        y = chunk["Y"].apply(lambda y : -y + 55)
        colors = chunk["ID"].apply(lambda x : COLORS[x % 9])
        ax1.scatter(x,y, alpha = 0.3, c=colors)
        plt.pause(0.1)
        plt.clf()
        print(i)
        ax1 = fig.add_subplot(111)
        ax1.imshow(campus_map, extent=[0, 83, 0, 55])
        ax1.scatter(poiX, poiY, c="#000000", marker="*")


    plt.show()


main()
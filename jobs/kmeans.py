"""
kmeans.py

Performs kmeans clustering using Lloyd's algorithm.


"""

from random import random
import matplotlib.pyplot as plt
from scipy.spatial import Voronoi, voronoi_plot_2d
import numpy as np


            #red,       blue,       yellow,    green,   orange,      purple,     brown,      black,      aqua
COLORS = ['#FF0000', '#0000FF',  '#FFFF00', '#00FF00', '#FF6600', '#8000FF', '#663300', '#FFFFFF', "#00FFFF"]

"""
Function: GetDistance
Returns the distance as a function of Euclidean distance and expected spending of customer.
Note: Expected spending of customer is an exponential decay function based on distance.

"""
def GetDistance(pointA, pointB):
    return ((pointA[0] - pointB[0])**2 + (pointA[1] - pointB[1])**2)**0.5


def GetExpectedSpending(centroid, customer):
    #Expected spending = Average customer spending per day * e ** (-lamda * distance)
    #0.0150 was calculated using exponential decay. Users reported willing to go 200ft to go to a vending machine
    return customer[2] * np.exp( - 0.0150 * GetDistance(centroid, customer))


#Return the expected profit of the given assignment
def AssignPoints(k, centroids, customers):
    expectedProfit = 0

    for customer in customers:
        closest = 0
        closestDistance = 99999
        for i in xrange(k):
            distance = GetDistance(centroids[i], customer)
            if (distance < closestDistance):
                closestDistance = distance
                closest = i
        #Customers are [x, y, value, k], where x and y are coordinates, value is daily spending, and k is grouping
        customer[3] = closest
        expectedProfit += GetExpectedSpending(centroids[closest], customer)

    return expectedProfit


# Function: UpdateCentroid
# Performs the update step in Lloyd's algorithm. Moves centroids
def UpdateCentroid(k, centroids, customers):
    #initialize sum [sum of x, sum of y, number of customers]
    centroidSum = []
    netDelta = 0

    for i in xrange(k):
        centroidSum.append([0,0,0])

    for customer in customers:
        centroidSum[customer[3]][0] += customer[0]
        centroidSum[customer[3]][1] += customer[1]
        centroidSum[customer[3]][2] += 1

    for i in xrange(k):
        #If no points were assigned to this centroid, it was placed in a bad spot. Replace it.
        if (centroidSum[i][2] == 0):
            centroids[i][0] = 83 * random()
            centroids[i][1] = 55 * random()
            continue
        newX = centroidSum[i][0]/centroidSum[i][2]
        newY = centroidSum[i][1]/centroidSum[i][2]
        netDelta += abs(centroids[i][0] - newX)
        netDelta += abs(centroids[i][1] - newY)
        centroids[i][0] = newX
        centroids[i][1] = newY
    return netDelta


# Function: Kmeans
# Perform Lloyd's algorithm to efficiently identify and label clusters
#
# Args:
# k: Number of desired clusters
# customers: List of [x, y, value], where x and y are coordinates and value is the customer's daily spending
#
# Returns:
# A list of [x,y] coordinate pairs for the centroids
def Kmeans(k, customers):

    #Append the fourth value to the customer tuple to track which grouping the customer belongs to
    for i in xrange(len(customers)):
        customers[i] = [customers[i][0], customers[i][1], customers[i][2], 0]

    #Randomly initialize our k centroids
    centroids = []
    for i in xrange(k):
        centroids.append([83 * random(), 55 * random()])

    fig = plt.figure()
    ax1 = fig.add_subplot(111)

    campus_map = plt.imread('../media/campus_map.png')
    ax1.imshow(campus_map, extent=[0, 83, 0, 55])

    expectedProfit = AssignPoints(k, centroids, customers)
    print("Original expected profit: " + str(expectedProfit))
    distance = 100

    while (distance > 0.05):
        cx, cy = zip(*centroids)
        for i in xrange(k):
            filteredPoints = [x for x in customers if x[3] == i]
            if len(filteredPoints) == 0: continue
            px, py, pv, pk = zip(*filteredPoints)
            ax1.scatter(list(px), list(py), c=COLORS[i], alpha=0.1)
        ax1.scatter(list(cx), list(cy), c=COLORS[0], marker='*')

        #Slowdown the redraw rate to make the visualizer easier to follow
        plt.pause(1)

        #Reset the window 
        plt.clf()
        ax1 = fig.add_subplot(111)
        ax1.imshow(campus_map, extent=[0, 83, 0, 55])
        ax1.set_xlabel("Grid X coordinate")
        ax1.set_ylabel("Grid Y coordinate")

        expectedProfit = AssignPoints(k, centroids, customers)
        distance = UpdateCentroid(k, centroids, customers)
        print("Distance: " + str(distance))
        print("Expected profit: " + str(expectedProfit))
        #vor = Voronoi(np.array(centroids))
        #fig = voronoi_plot_2d(vor)


    plt.gca().invert_yaxis()
    plt.show()

    return centroids


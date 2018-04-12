"""
kmeans.py

Performs kmeans clustering using Lloyd's algorithm.



"""

from random import random
import matplotlib.pyplot as plt
from scipy.spatial import Voronoi, voronoi_plot_2d
import numpy as np


            #red,      orange,    yellow,    green,     blue,   purple,     brown,      black,      aqua
COLORS = ['#FF0000', '#FF6600', '#FFFF00', '#00FF00', '#0000FF', '#8000FF', '#663300', '#FFFFFF', "#00FFFF"]


"""
Function: GetDistance
Returns the Euclidean distance between the two points

"""
def GetDistance(pointA, pointB):
    return ((pointA[0] - pointB[0])**2 + (pointA[1] - pointB[1])**2)**0.5



def AssignPoints(k, centroids, points):
    for point in points:
        closest = 0
        closestDistance = 99999
        for i in xrange(k):
            distance = GetDistance(centroids[i], point)
            if (distance < closestDistance):
                closestDistance = distance
                closest = i
        #Points are [x, y, k], where x and y are coordinates and k is grouping
        point[2] = closest


# Function: UpdateCentroid
# Performs the update step in Lloyd's algorithm. Moves centroids
def UpdateCentroid(k, centroids, points):
    #initialize sum
    centroidSum = []
    netDelta = 0

    for i in xrange(k):
        centroidSum.append([0,0,0])

    for point in points:
        centroidSum[point[2]][0] += point[0]
        centroidSum[point[2]][1] += point[1]
        centroidSum[point[2]][2] += 1

    for i in xrange(k):
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
# points: List of [x,y] coordinate pairs
def Kmeans(k, points):
    #Randomly initialize our k centroids

    for i in xrange(len(points)):
        points[i] = [points[i][0], points[i][1], 0]

    centroids = []
    for i in xrange(k):
        centroids.append([random(), random()])

    x = range(1)
    y = range(1)
    fig = plt.figure()
    ax1 = fig.add_subplot(111)
    ax1.axis([0, 1, 0, 1])


    #print("Centroids: ")
    #print(centroids)
    AssignPoints(k, centroids, points)
    distance = 100

    px, py, z = zip(*points)
    #ax1.scatter(list(px), list(py), c=COLORS[4], alpha = 0.5)

    while (distance > 0.05):
        #unzippedCentroids = zip(*centroids)
        #unzippedPoints = zip(*points)
        #plt.scatter(*zip(*centroids))
        cx, cy = zip(*centroids)
        ax1.scatter(list(cx), list(cy), c=COLORS[0], marker='*')
        for i in xrange(k):
            filteredPoints = [x for x in points if x[2] == i]
            px, py, pk = zip(*filteredPoints)
            ax1.scatter(list(px), list(py), c=COLORS[i], alpha=0.5)

        #plt.scatter(unzippedCentroids, COLORS[0], unzippedPoints, COLORS[4])
        plt.pause(1)
        plt.clf()
        ax1 = fig.add_subplot(111)
        ax1.axis([0, 1, 0, 1])
        AssignPoints(k, centroids, points)
        distance = UpdateCentroid(k, centroids, points)
        print("Distance: " + str(distance))
        #vor = Voronoi(np.array(centroids))
        #fig = voronoi_plot_2d(vor)


    plt.show()




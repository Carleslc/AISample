from src.layers.Dense import Dense as Layer_Dense
from src.activation.functions import ReLU
from src.utils.data import spiral

import nnfs
import numpy as np
import matplotlib.pyplot as plt

nnfs.init()

# 100 points for every class (3 classes)
# X = 300 sample points of 2 features (x,y)
# y = class of each points
X, y = spiral(100, 3)

plt.scatter(X[:,0], X[:,1])
plt.show()

plt.scatter(X[:,0], X[:,1], c=y, cmap="brg")
plt.show()

layer1 = Layer_Dense(2, 3) # layer with 2 inputs (x, y) and 3 neurons (3 outputs)
layer1.forward(X)

activation1 = ReLU(layer1.output)

print('Output')
print(activation1)

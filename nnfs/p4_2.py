# 2 layers of 3 neurons each with 4 inputs (and 3 batches of sample inputs) (using classes)

from src.layers.Dense import Dense as Layer_Dense

import numpy as np

np.random.seed(0)

# 3 batches of 4 inputs each
X = [
  [1, 2, 3, 2.5],
  [2.0, 5.0, -1.0, 2.0],
  [-1.5, 2.7, 3.3, -0.8]
]

layer1 = Layer_Dense(4, 5) # layer 1 with 4 inputs and 5 neurons (5 outputs for each input batch)
layer1.forward(X)
print('Layer 1 (Hidden)', layer1.output, sep='\n')

layer2 = Layer_Dense(5, 2) # layer 2 with 5 inputs from the previous layer output and 2 output neurons
layer2.forward(layer1.output)
print('Layer 2 (Output)', layer2.output, sep='\n')

from src.layers.Dense import Dense as Layer_Dense
from src.activation.functions import ReLU
from src.utils.data import spiral

import numpy as np

np.random.seed(0)

X = np.array([
  [0, 2, -1, 3.3, -2.7, 1.1, 2.2, -100]
])

layer1 = Layer_Dense(8, 8)
layer1.forward(X)

print('Layer 1 (Hidden)')
print(layer1.output)

layer2 = Layer_Dense(8, 1)
layer2.forward(layer1.output)

print('Layer 2 (Output)')
print(layer2.output)

print('Result (ReLU)')
print(ReLU(layer2.output))

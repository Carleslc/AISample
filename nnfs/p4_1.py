# Layer of 3 neurons with 4 inputs (and 3 batches of sample inputs)

import numpy as np

inputs = [
  [1, 2, 3, 2.5],
  [2.0, 5.0, -1.0, 2.0],
  [-1.5, 2.7, 3.3, -0.8]
] # (3,4)

weights = [
  [0.2, 0.8, -0.5, 1.0],
  [0.5, -0.91, 0.26, -0.5],
  [-0.26, -0.27, 0.17, 0.87]
] # (3, 4)

biases = [2, 3, 0.5]

# (3, 4) x (4, 3) + (1, 3) = (3, 3)
output = np.dot(inputs, np.array(weights).T) + biases

print(output)
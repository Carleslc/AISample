import numpy as np

def Identity(inputs):
  return inputs

def Unit(inputs): # H
  '''
  0 if x <= 0
  1 if x > 0
  '''
  return np.where(inputs > 0, 1, 0)

def Sigmoid(inputs):
  '''
  1/(1+e^(-x))
  '''
  return 1/(1 + np.exp(-inputs))

def ReLU(inputs):
  '''
  0 if x <= 0
  x if x > 0
  '''
  return np.maximum(0, inputs)

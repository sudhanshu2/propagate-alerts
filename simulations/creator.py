import secrets
import networkx as nx
import sys

EDGE_WEIGHT = 10

def centralized(number_sensors):
  G_centralized = nx.Graph()
  for i in range(number_sensors):
    G_centralized.add_node(i, visited="false")

  for i in range(1, number_sensors):
    G_centralized.add_edge(0, i, weight=(i / 10))
  return G_centralized

def decentralized(number_sensors, connected_sensors, random_connections=False):
  random_generator = secrets.SystemRandom()
  max_connected_sensors = connected_sensors

  G_decentralized = nx.Graph()

  for i in range(1, number_sensors + 1):
    G_decentralized.add_node(i, visited="false", depth=-1)

  if random_connections is True:
    connected_sensors = random_generator.randrange(1, max_connected_sensors)

  current_node = 1
  current_difference = connected_sensors

  while current_node + current_difference <= number_sensors:
    if random_connections is True:
      connected_sensors = random_generator.randrange(1, max_connected_sensors)
    for i in range(connected_sensors):
      to_join = current_node + current_difference + i
      if current_node < number_sensors and to_join <= number_sensors:
        G_decentralized.add_edge(current_node, to_join, weight=EDGE_WEIGHT)
    current_node += 1
    current_difference += connected_sensors - 2

  return G_decentralized

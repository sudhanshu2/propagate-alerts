from network_failure import event_failure
from collections import defaultdict

MULTIPLIER = 1
LATENCY_PER_MILE = 0.000005 * MULTIPLIER
COMPUTE_TIME = 3 * MULTIPLIER
ROUTING_PER_NODE = 0.1 * MULTIPLIER

def simulation(G, alert_origin, failure=False, failure_rate=0.2):
  for node in G.nodes:
    G.nodes[node]["visited"] = "false"

  not_visited_neighbors = list()
  G_visited = defaultdict(list)

  not_visited_neighbors.append(alert_origin)

  G.nodes[alert_origin]["visited"] = "true"

  G_visited[0].append(alert_origin)
  count = 1
  max_weight = 0
  for edge in G.edges:
      if max_weight < G.get_edge_data(edge[0], edge[1])['weight']:
          max_weight = G.get_edge_data(edge[0], edge[1])['weight']

  while len(not_visited_neighbors) != 0:
    current = not_visited_neighbors.pop()
    neighbors = G.edges(current)
    to_remove = list()
    if failure == True:
      for edge in neighbors:
        probability = failure_rate * (int(G.get_edge_data(edge[0], edge[1])['weight'] / max_weight))
        if event_failure(probability) == True:
          to_remove.append(edge)

    for edge in to_remove:
      G.remove_edge(edge[1], edge[0])

    count += 1

    for edge in G.edges(current):
      if G.nodes[edge[1]]["visited"] == "false":
        not_visited_neighbors.append(edge[1])
        G.nodes[edge[1]]["visited"] = "true"
        latency = COMPUTE_TIME + LATENCY_PER_MILE * G.get_edge_data(edge[0], edge[1])['weight'] + (ROUTING_PER_NODE * len(G.edges(current)))
        G_visited[latency].append(edge[1])

  sorted_time_stamps = [key for key in G_visited.keys() if key != 'default']
  sorted_time_stamps.sort()

  nodes_alerted = list()
  current_nodes_alerted = 0

  if len(sorted_time_stamps) == 0:
    return list(), list(), 0

  for j in range(len(sorted_time_stamps)):
    current_nodes_alerted += len(G_visited[sorted_time_stamps[j]])
    nodes_alerted.append(current_nodes_alerted)

  return sorted_time_stamps, nodes_alerted, current_nodes_alerted

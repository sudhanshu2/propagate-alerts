from network_failure import event_failure
from collections import defaultdict

MULTIPLIER = 1
LATENCY_PER_MILE = 0.0000119 * MULTIPLIER
COMPUTE_TIME = 4 * MULTIPLIER
ROUTING_PER_NODE = 0.1 * MULTIPLIER

def simulation(G, alert_origin, failure=False, failure_rate=0.2):

  for node in G.nodes:
    G.nodes[node]["visited"] = "false"
    G.nodes[node]["depth"] = -1

  not_visited_neighbors = list()
  G_visited = defaultdict(list)

  not_visited_neighbors.append(alert_origin)

  G.nodes[alert_origin]["visited"] = "true"
  G.nodes[alert_origin]["depth"] = 0

  G_visited[0].append(alert_origin)

  while len(not_visited_neighbors) != 0:
    current = not_visited_neighbors.pop()
    neighbors = G.edges(current)
    to_remove = list()
    if failure == True:
      for edge in neighbors:
        probability = pow(failure_rate, G.nodes[alert_origin]["depth"] + 2)
        if event_failure(probability) == True:
          to_remove.append(edge)

    for edge in to_remove:
      G.remove_edge(edge[1], edge[0])

    for edge in G.edges(current):
      if G.nodes[edge[1]]["visited"] == "false":
        not_visited_neighbors.append(edge[1])
        G.nodes[edge[1]]["visited"] = "true"
        G.nodes[edge[1]]["depth"] = G.nodes[edge[0]]["depth"] + 1
        latency = COMPUTE_TIME + (LATENCY_PER_MILE * int(G.get_edge_data(edge[0], edge[1])['weight']) + ROUTING_PER_NODE) * G.nodes[edge[0]]["depth"]
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

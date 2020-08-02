import secrets
import creator

import decentralized_simulation as decentralized
import centralized_simulation as centralized
import matplotlib.pyplot as plt

RUNS = 4
NODE_CONNECTIONS = 5

xs = list()
ys = list()

xs_random = list()
ys_random = list()

xs_curr = list()
ys_curr = list()

for i in range(1, RUNS + 1):
  node_count = pow(10, i)

  G_alert = creator.decentralized(node_count, NODE_CONNECTIONS)
  G_alert_random = creator.decentralized(node_count, NODE_CONNECTIONS, True)
  G_curr = creator.centralized(node_count)

  random_generator = secrets.SystemRandom()
  alert_origin = random_generator.randrange(1, node_count - 1)

  time_stamp, nodes_alerted, _ = decentralized.simulation(G_alert, alert_origin)
  time_stamp_random, nodes_alerted_random, _ = decentralized.simulation(G_alert_random, alert_origin)
  time_stamp_curr, nodes_alerted_curr, _ = centralized.simulation(G_curr, alert_origin)

  xs.append(nodes_alerted)
  ys.append(time_stamp)

  xs_random.append(nodes_alerted_random)
  ys_random.append(time_stamp_random)

  xs_curr.append(nodes_alerted_curr)
  ys_curr.append(time_stamp_curr)

for i in range(len(xs)):
    plt.figure(i + 1)
    plt.plot(xs[i], ys[i])
    plt.plot(xs_random[i], ys_random[i])
    plt.plot(xs_curr[i], ys_curr[i])

plt.show()

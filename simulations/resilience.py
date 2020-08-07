import secrets
import sys
import creator

import decentralized_simulation as decentralized
import centralized_simulation as centralized
import matplotlib.pyplot as plt

NUMBER_NODES = 1000
MIN_FAILURE = 0.0001
MAX_FAILURE = 1
STEP = 0.0001
NUMBER_RUNS = 1
NODE_CONNECTIONS = 20

current_failure_rate = MIN_FAILURE

alert_origin = list()
G_alert = list()
G_alert_random = list()
G_curr = creator.centralized(NUMBER_NODES)

random_generator = secrets.SystemRandom()

for i in range(NUMBER_RUNS):
  G_alert.append(creator.decentralized(NUMBER_NODES, NODE_CONNECTIONS))
  G_alert_random.append(creator.decentralized(NUMBER_NODES, NODE_CONNECTIONS, True))
  alert_origin.append(random_generator.randrange(1, NUMBER_NODES))

total_iterations = 1
all_zero = False

xs = [list()] * NUMBER_RUNS
ys = [None] * NUMBER_RUNS
ys_random = [None] * NUMBER_RUNS
ys_random = [None] * NUMBER_RUNS
ys_curr = [None] * NUMBER_RUNS

for i in range(NUMBER_RUNS):
  ys[i] = list()
  ys_random[i] = list()
  ys_curr[i] = list()

while current_failure_rate <= MAX_FAILURE and all_zero == False:
  sys.stdout.write("\rcurrent failure rate: " + str(current_failure_rate) + ", total iterations: " + str(total_iterations))
  sys.stdout.flush()
  count = 0

  for i in range(NUMBER_RUNS):
    _, _, total_nodes_alerted = decentralized.simulation(G_alert[i], alert_origin[i], failure=True, failure_rate=current_failure_rate)
    _, _, total_nodes_alerted_random = decentralized.simulation(G_alert_random[i], alert_origin[i], failure=True, failure_rate=current_failure_rate)
    _, _, total_nodes_alerted_curr = centralized.simulation(G_curr, alert_origin[i], failure=True, failure_rate=current_failure_rate)

    ys[i].append(total_nodes_alerted)
    ys_random[i].append(total_nodes_alerted_random)
    ys_curr[i].append(total_nodes_alerted_curr)

    # print(str(total_nodes_alerted) + " " + str(total_nodes_alerted_random) + " " + str(total_nodes_alerted_curr))

    if total_nodes_alerted == 1 and total_nodes_alerted_random == 1 and total_nodes_alerted_curr == 1:
      count += 1

    total_iterations += 1

  if count == NUMBER_RUNS:
    all_zero = True

  xs[0].append(current_failure_rate)
  current_failure_rate += STEP

sys.stdout.write("\r")
sys.stdout.flush()

if all_zero == True:
    print("current failure rate: " + str(current_failure_rate))

plt.figure(1)
for i in range(len(xs)):
    plt.plot(xs[i], ys[i])

plt.figure(2)
for i in range(len(xs)):
    plt.plot(xs[i], ys_random[i])

plt.figure(3)
for i in range(len(xs)):
    plt.plot(xs[i], ys_curr[i])

plt.show()

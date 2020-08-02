import secrets
import sys
import creator

import decentralized_simulation as decentralized
import centralized_simulation as centralized
import matplotlib.pyplot as plt

NUMBER_NODES = 1000
MIN_FAILURE = 0.01
MAX_FAILURE = 0.03
STEP = 0.01
NUMBER_RUNS = 20
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
    _, _, total_nodes_alerted_curr = centralized.simulation(G_curr, alert_origin[i], multiplier=FIBER_MULTIPLIER, failure=True, failure_rate=current_failure_rate)

    ys[i].append(total_nodes_alerted)
    ys_random[i].append(total_nodes_alerted_random)
    ys_curr[i].append(total_nodes_alerted_curr)

    if total_nodes_alerted == 0 and total_nodes_alerted_random == 0 and total_nodes_alerted_curr == 0:
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

print(xs)
print(ys)

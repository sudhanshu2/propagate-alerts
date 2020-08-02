import secrets
import decimal

ctx = decimal.Context()
ctx.prec = 20

def float_to_str(f):
    d1 = ctx.create_decimal(repr(f))
    return format(d1, 'f')

def event_failure(probability):
  if probability >= 1:
    return True
  probability_string = float_to_str(probability)
  decimals = probability_string[probability_string.index(".") + 1:]
  chance = int(decimals)
  precision = len(decimals)
  upper_range = pow(10, precision)

  random_generator = secrets.SystemRandom()
  generated = random_generator.randrange(1, upper_range)

  if generated <= chance:
    return True
  return False

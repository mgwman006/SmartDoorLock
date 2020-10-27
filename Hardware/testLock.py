from gpiozero import *
from time import sleep

switch = OutputDevice(18)

while True:
    switch.on()
    sleep(5)
    switch.off()
    sleep(5)
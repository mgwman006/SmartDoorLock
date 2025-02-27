#importiong packages
from gpiozero import *
import paho.mqtt.client as mqtt
import Camera
from time import sleep


#Variables
switch = OutputDevice(18)
button = Button (4)

originalKey = ["maneno.mgwami@gmail.com"]
doorStatus = "closed"



def openDoor():
    #Statement to open door
    switch.on()
    doorStatus = "open"
    print ("Door is: " + doorStatus)
    
    
def closeDoor():
    #Statement to close Door
    switch.off()
    doorStatus = "closed"
    print ("Door is: " + doorStatus)


def scanner():
    
    key=Camera.takePicture()
    print(key)
    if (key == originalKey):
        openDoor()
    
    else:
        closeDoor()
        


# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("Lock/control")

# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):

    message = str(msg.payload)
    if (message == "open"):
        openDoor()
    
    else:
        closeDoor()




def startMQTTClient():

    client = mqtt.Client()
    
    client.on_connect = on_connect
    client.on_message = on_message

    client.connect("mqtt.eclipse.org", 1883, 60)
    client.loop_forever()
        
        
while True:
    
    button.when_pressed = scanner
    startMQTTClient()
    #button.when_pressed = scanner
    sleep(2)
    

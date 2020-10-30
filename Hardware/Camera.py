from picamera import PiCamera, Color
from time import sleep
from signal import pause
from PIL import Image
import zbarlight



camera=PiCamera()
camera.resolution = (2592, 1944)
camera.framerate =15
camera.awb_mode= 'sunlight'
camera.brightness =43
camera.contrast =49
camera.color_effects = (128,128)


def takePicture():
    
    camera.start_preview()
    sleep(5)
    fileName = "/home/pi/r/SmartDoorLock/Hardware/testImage/finaltestedImage.jpg"
    camera.capture(fileName) 
    camera.stop_preview()
    decodedKey = qrDecode()
    
    return decodedKey

    

def qrDecode():

    fileSource = "/home/pi/r/SmartDoorLock/Hardware/testImage/finaltestedImage.jpg"
    with open(fileSource, 'rb') as imageFile:
        image=Image.open(fileSource)
        image.load()
    
    #newImage=zbarlight.copy_image_on_background(image, color=zbarlight.WHITE)    
    code=zbarlight.scan_codes(['qrcode'],image)
    return code


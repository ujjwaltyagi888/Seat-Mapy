import serial
import time

ser = serial.Serial("COM3", 115200)

while True:
    line = ser.readline()
    data = str(line).replace("b", "").replace("'", "").replace("x0c", "").replace("\\", "").replace("rn", "")
    f = open("serial.txt", "w")
    f.write(data)
    f.close()
    time.sleep(1)
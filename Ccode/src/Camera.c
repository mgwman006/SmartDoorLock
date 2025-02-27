#include "Camera.h"
volatile unsigned char pixles[176][144];

void camera_config()
{
	delay();
	// Enable scaling (COM3)
	i2c_com(0x0c, 0x00, 0b00001000); 

	// Selecting QCIF format (COM7)
	i2c_com(0x12, 0x00, 0b00001000);

        //Selecting QCIF format (COM14)
	i2c_com(0x3e, 0x0e, 0b00001000);

	// //Test pattern
	/**I2C_Start();
	I2C_Write(0x42);
	I2C_Write(0x70);
	I2C_Write(0x4a | 0b10000000);
	I2C_Stop();
	delay();
	I2C_Start();
        I2C_Write(0x42);
        I2C_Write(0x71);
        I2C_Write(0x35 | 0b10000000);
        I2C_Stop();
        delay();
        */
}

//Reading image

void read_image()
{
    for (int row==0, row < 176, row++)
    {
        for (int column==0, column < 144, column++)
        {
	   delay();
	   StartI2C();
	   WriteI2C(0x43);
           pixels[i][j]=ReadI2C();
           NotAckI2C();
           StopI2C();
           delay(); 
        }

    }
}


//I2C communication
void i2c_com(int reg_address, int default_value, int change_value)
{
	delay();
	I2C_Start();
	I2C_Write(0x42);
	I2C_Write(reg_address); 
	I2C_Write(default_value | change_value);
	StopI2C();
	delay();
}


// This is about a ms delay, used for I2C communication
void delay(void) 
{
        int j;
        for (j=0; j<1000; j++) {}
}

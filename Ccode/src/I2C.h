#ifndef I2C_H__
#define I2C_H__
// Header file for i2c_master_noint.c
// helps implement use I2C1 as a master without using interrupts


//Vriables Declaration

//WriteI2C( slave_address | 0x01 ); 

 

//Methods
void I2C_Init(unsigned long int clock_freq);              // set up I2C 1 as a master, at 100 kHz
void I2C_Idle(void);
void MSSP_Wait(void);
void I2C_Start(void);              // send a START signal
void I2C1_Repeated_Start(void);            // send a RESTART signal
int I2C_Write(unsigned char data); // send a byte (either an address or data)
unsigned char I2C_Read(void);      // receive a byte of data
void I2C_Ack(void);             // send an ACK (0) or NACK (1)
void I2C_Nack(void)
void I2C_Stop(void);               // send a stop

#endif

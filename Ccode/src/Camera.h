#ifndef Camera_H__
#define Camera_H__


#define _XTAL_FREQ 20000000                   //Frequency of external oscillator in Hz
#define clock_frequency 100000



#define VGA_WIDTH	640
#define VGA_HEIGHT	480
#define QVGA_WIDTH	320
#define QVGA_HEIGHT	240
#define CIF_WIDTH	352
#define CIF_HEIGHT	288
#define QCIF_WIDTH	176
#define	QCIF_HEIGHT	144



/*
 * Write and Read Addresses
 */
#define OV7670_I2C_ADDR_R 0x43
#define OV7670_I2C_ADDR_W 0x42


void read_image(void);
void i2c_com(int reg_address, int default_value, int change_value);
void delay(void);


#endif

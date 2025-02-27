#include "USART.h"

void USART_Init(unsigned long int baud_rate, unsigned long int clock_frequency)
{
    
    TRISC6=0;		/* Make Tx pin as output*/
    TRISC7=1;  		/* Make Rx pin as input*/

    /* Baud rate=9600, SPBRG = (F_CPU /(64*9600))-1*/
    SPBRG= ((clock_frequcny) / (64*baud_rate)) - 1;     //Low speed 	

    TXSTA = 0x20;  	/* Enable Transmit(TX) */ 
    RCSTA = 0x90;  	/* Enable Receive(RX) & Serial */
    
    //INTCONbits.GIE = 1;	/* Enable Global Interrupt */
    //INTCONbits.PEIE = 1;/* Enable Peripheral Interrupt */
    //PIE1bits.RCIE = 1;	/* Enable Receive Interrupt*/
    //PIE1bits.TXIE = 1;	/* Enable Transmit Interrupt*/
}

Char USART_TransmitChar(char out)
{
   while(TXIF == 0) //Wait interupt flag
   {
   }	
   TXREG = out;  	/* Write char data to transmit register */    
}


Char USART_ReceiveChar(void)
{
    while(RCIF==0)      /*wait for receive interrupt flag*/
    {
    }
    
    return RCREG;       /*received in RCREG register and return to main program */
}


void USART_SendString(const char *str)
{

   while(*str!='\0')                /* Transmit data until null */
   {            
        USART_TxChar(*str);
        str++;
        
   }

}
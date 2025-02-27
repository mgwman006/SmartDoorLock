
#ifndef USART_H
#define	USART__H


void USART_Init(unsigned long int baud_rate, unsigned long int clock_frequency)              /* USART Initialization function */
void USART_TxChar(char);            /* USART character transmit function */
char USART_RxChar(void);                /* USART character receive function */
void USART_SendString(const char *);/* USART String transmit function */

#endif	/* USART_HEADER_FILE_H */
#include <string.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <mosquitto.h>
#include "USART_.h"


void initSim(void)
{
    AT+CIPMUX=0;      //Single Connection Mode
    AT+CIMODE=1;      //Transparent Mode
    AT+CSTT="CMNET"    //Start Task and set APN
    AT+CIICR;         //Bring up Wireless Connection
    AT+CIPSERVER=1,"1671";   //Start a server and opening a listening port
    AT+CIFSR; //Getlocal IP address 
    
    AT+CIPSTART="TCP","116.228.221.51","8500" ; //Start Up TCP connection to remote server
    ATO; //Return to Data Mode
    
}


bool SendATandExpectResponse(const char* ATCommand, const char* ExpectedResponse)
{
	USART_SendString(ATCommand);	/* Send AT command to SIM900 */
	USART_TxChar('\r');
	return WaitForExpectedResponse(ExpectedResponse);
}


bool TCPServer_TransParentMode(void)
{
	char _buffer[20];
	sprintf(_buffer, "AT+CIPMODE=%d\r", 1);
	_buffer[19] = 0;
	USART_SendString(_buffer);
	return WaitForExpectedResponse("OK");
}

bool TCPServer_SingleConnectionMode(void)
{
	char _buffer[20];
	sprintf(_buffer, "AT+CIPMUX=%d\r", 0);
	_buffer[19] = 0;
	USART_SendString(_buffer);
	return WaitForExpectedResponse("OK");
}




bool SIM800_StartTask()
{
	for (uint8_t i=0;i<5;i++)
	{
		if(SendATandExpectResponse("AT+CSTT=\"CMNET\"","OK") && SendATandExpectResponse("AT+CIICR","OK"))
		{
		    return true;
                }
	}
	return false;
}



bool TCPClient_Shut()
{
	USART_SendString("AT+CIPSHUT\r");
	return WaitForExpectedResponse("OK");
}

bool TCPClient_Close()
{
	USART_SendString("AT+CIPCLOSE=1\r");
	return WaitForExpectedResponse("OK");
}

bool TCPServer_Connect(void)
{

	//USART_SendString("AT+CREG?\r");
	//if(!WaitForExpectedResponse("+CREG: 0,1"))
	//return false;

	USART_SendString("AT+CGATT?\r");
	if(!WaitForExpectedResponse("+CGATT: 1"))
	{
	    return false;
        }

	USART_SendString("AT+CIPSERVER=1,1671");
	if(!WaitForExpectedResponse("SERVER OK"))
	{
	    return false;
        }
        
        //USART_SendString("AT+CIFSR\r");    //Get local IP Address
        //if(!WaitForExpectedResponse("."))
        //{
        //    return false;
      //  }


	USART_SendString("AT+CIPSTATUS\r");
	return WaitForExpectedResponse("hello server");

}




bool TCPClient_connected() {
	USART_SendString("AT+CIPSTATUS\r");
	CRLF_COUNT = 2;
	return WaitForExpectedResponse("CONNECT OK");
}

uint8_t TCPClient_Start(const char* Domain, const char* Port)
{
	USART_SendString("AT+CIPMUX?\r");
	if(WaitForExpectedResponse("+CIPMUX: 0"))
	USART_SendString("AT+CIPSTART=\"TCP\",\"");
	else
	{
		USART_SendString("AT+CIPSTART=\"");
		USART_SendString(CONNECTION_NUMBER);
		USART_SendString("\",\"TCP\",\"");
	}
	
	USART_SendString(Domain);
	USART_SendString("\",\"");
	USART_SendString(Port);
	USART_SendString("\"\r");

	CRLF_COUNT = 2;
	if(!WaitForExpectedResponse("CONNECT OK"))
	{
		if(Response_Status == SIM900_RESPONSE_TIMEOUT)
		return SIM900_RESPONSE_TIMEOUT;
		return SIM900_RESPONSE_ERROR;
	}
	return SIM900_RESPONSE_FINISHED;
}

uint8_t TCPClient_Send(char* Data)
{
	USART_SendString("AT+CIPSEND\r");
	CRLF_COUNT = -1;
	WaitForExpectedResponse(">");
	USART_SendString(Data);
	USART_SendString("\r\n");
	USART_TxChar(0x1A);

	if(!WaitForExpectedResponse("SEND OK"))
	{
		if(Response_Status == SIM900_RESPONSE_TIMEOUT)
		return SIM900_RESPONSE_TIMEOUT;
		return SIM900_RESPONSE_ERROR;
	}
	return SIM900_RESPONSE_FINISHED;
}

void interrupt ISR()			 /* Receive ISR routine */
{
    uint8_t oldstatus = STATUS;
    INTCONbits.GIE = 0;
    char received_char;
    if(RCIF==1){
        RESPONSE_BUFFER[Counter] = RCREG;/* Copy data to buffer & increment counter */
        Counter++;
        if(RCSTAbits.OERR)       /* check if any overrun occur due to continuous reception */
        {           
            CREN = 0;
            NOP();
            CREN = 1;
        }
    }
    INTCONbits.GIE = 1;
    STATUS = oldstatus;
}

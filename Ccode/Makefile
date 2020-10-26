SRC     = src
DST     = bin/

CC      = sdcc
FAMILY  = pic16
PROC    = 18f4550

all: $(SRC)
	$(CC) --use-non-free -m$(FAMILY) -p$(PROC) $(SRC)/blink_led.c -o $(DST)   

clean:
	rm -f ./bin/*.asm ./bin/*.cod ./bin/*.hex ./bin/*.lst ./bin/*.o

.PHONY: all clean




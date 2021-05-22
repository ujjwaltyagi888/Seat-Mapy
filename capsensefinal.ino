#include <CapacitiveSensor.h>

CapacitiveSensor cs_2_3 = CapacitiveSensor(2,3);// 10 megohm resistor between pins 2 & 3, pin 3 is sensor pin, add wire, foil
CapacitiveSensor cs_4_5 = CapacitiveSensor(4,5);
CapacitiveSensor cs_6_7 = CapacitiveSensor(6,7);
CapacitiveSensor cs_A0_A1 = CapacitiveSensor(A0,A1);
CapacitiveSensor cs_A2_A3 = CapacitiveSensor(A2,A3);
CapacitiveSensor cs_A4_A5 = CapacitiveSensor(A4,A5);
int n=250;
int latchPin = 11; // connect to the ST_CP of 74HC595 (pin 3,latch pin)
int clockPin = 9;  // connect to the SH_CP of 74HC595 (pin 4, clock pin)
int dataPin = 12;  // connect to the DS of 74HC595 (pin 2)

byte sevenSegDigits[10] = { B01111011   ,  // = 0
                            B00001001   ,  // = 1
                            B10110011   ,  // = 2
                            B10011011   ,  // = 3
                            B11001001   ,  // = 4
                            B11011010   ,  // = 5
                            B11111000   ,  // = 6
                            B00001011   ,  // = 7
                            B11111011   ,  // = 8
                            B11001011      // = 9,  
                           };
 byte sevenSegAlpha[] = {  B11101011   , // = A
                          B11111000   , // = b
                          B01110010   , // = C
                          B10111001   , // = d
                          B11110010   , // = E
                          B11100010   , // = F
                          B11011011   , // = g
                          B11101000   , // = h
                          B01100000     // = I   
};
byte sevenSegDP = B00000100;  // = DP

bool bAddDecimalPoint = true;


int state =LOW;
boolean yes;
boolean previous = false;
long int time =0;
int debounce = 200;
void setup(){

cs_2_3.set_CS_AutocaL_Millis(0xFFFFFFFF); 
cs_4_5.set_CS_AutocaL_Millis(0xFFFFFFFF); 
cs_6_7.set_CS_AutocaL_Millis(0xFFFFFFFF); 
cs_A0_A1.set_CS_AutocaL_Millis(0xFFFFFFFF); 
cs_A2_A3.set_CS_AutocaL_Millis(0xFFFFFFFF); 
cs_A4_A5.set_CS_AutocaL_Millis(0xFFFFFFFF); 
Serial.begin(115200);// turn off autocalibrate on channel 1 - just as an example Serial.begin(115200);

// Set latchPin, clockPin, dataPin as output
    pinMode(latchPin, OUTPUT);
    pinMode(clockPin, OUTPUT);
    pinMode(dataPin, OUTPUT);


int debounce = 200;
}
void sevenSegWrite(byte digit, bool bDP = false, char switchValue='D') {
    /*       digit = array pointer or binary value, as a byte 
     *         bDP = true-include decimal point, as boolean
     * switchValue = 'A' alpha
     *               'B' binary
     *               'D' digits <default>, as char           */
    
    // set the latchPin to low potential, before sending data
    digitalWrite(latchPin, LOW);
     
    // the data (bit pattern)
    if (switchValue=='A'){
        // alpha
        shiftOut(dataPin, clockPin, MSBFIRST, sevenSegAlpha[digit]+(sevenSegDP*bDP)); 
    } else if (switchValue=='B'){
        // binary
        shiftOut(dataPin, clockPin, MSBFIRST, digit+(sevenSegDP*bDP));
    } else {
        // digits
        shiftOut(dataPin, clockPin, MSBFIRST, sevenSegDigits[digit]+(sevenSegDP*bDP));   
    }
 
    // set the latchPin to high potential, after sending data
    digitalWrite(latchPin, HIGH);
}
void sevenSegBlank(){
    // set the latchPin to low potential, before sending data
    digitalWrite(latchPin, LOW);
    shiftOut(dataPin, clockPin, MSBFIRST, B00000000);  
    // set the latchPin to high potential, after sending data
    digitalWrite(latchPin, HIGH);
}

void loop()                    
{
    bAddDecimalPoint = !bAddDecimalPoint;   
    int i=0;
    long total1 =  cs_2_3.capacitiveSensor(30);
    
    if (total1 > n){{yes = true;}
    
    Serial.print("OCCUPIED;");
   
    }
    else {{yes = false;}
   
    Serial.print("UNOCCUPIED;");
    i=i+1;
    }
 //////////////////////////////////////////   
long total2 =  cs_4_5.capacitiveSensor(30);
    
    if (total2 > n){{yes = true;}
    
    Serial.print("OCCUPIED;");

    }
    else {{yes = false;}
   
    Serial.print("UNOCCUPIED;");
    i=i+1;
  }
 ///////////////////////////////////////////   
long total3 =  cs_6_7.capacitiveSensor(30);
    
    if (total3 > n){{yes = true;}
    
    Serial.print("OCCUPIED;");
  
    }
    else {{yes = false;}
   
    Serial.print("UNOCCUPIED;");
    i=i+1;
    }
///////////////////////////////////////////

long total4 =  cs_A0_A1.capacitiveSensor(30);
    
    if (total4 > n){{yes = true;}
    
    Serial.print("OCCUPIED;");
 
    }
    else {{yes = false;}
   
    Serial.print("UNOCCUPIED;");
    i=i+1;
    }
//////////////////////////////////////////
long total5 =  cs_A2_A3.capacitiveSensor(30);
    
    if (total5 > 400){{yes = true;}
    
    Serial.print("OCCUPIED;");
    
    }
    else {{yes = false;}
   
    Serial.print("UNOCCUPIED;");
    i=i+1;
     
    }
 ///////////////////////////////////////
 long total6 =  cs_A4_A5.capacitiveSensor(30);
    
    if (total6 > n){{yes = true;}
    
    Serial.println("OCCUPIED");
    delay(100);
    }
    else {{yes = false;}
   
    Serial.println("UNOCCUPIED");
    i=i+1;
    delay(500);}
  bAddDecimalPoint = !bAddDecimalPoint;   
  sevenSegWrite(i, bAddDecimalPoint);
  delay(500);
   }    


  
  

    

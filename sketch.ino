// include the library code:
#include <LiquidCrystal.h>

// initialize the library with the numbers of the interface pins
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

const int sensorPin = A1;
float maximum;
float values[20];
void setup() {
  // set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  Serial.begin(9600);
  
  lcd.print("Waiting for input Waiting for input ");
}

void loop() {
  int sensorVal = analogRead(sensorPin);
  float voltage = (sensorVal / 1024.0) * 5.0; //convert arduino sensor value to volts
  int i = 0;
  int j = 0;

  Serial.print("Voltage:");
  Serial.println(voltage,2);

  lcd.scrollDisplayLeft();

  //0.3V - 3V, when the blood is in the test strip
  while(voltage > 0.3 && voltage < 3){
    sensorVal = analogRead(sensorPin);
    voltage = (sensorVal / 1024.0) * 5.0;
          
    Serial.print("Voltage:");
    Serial.println(voltage,2);

    values[i]= voltage;
    i++;

    //get the maximum value from the strip
    if (i==20){
      maximum = values[0];
      for(j=1;j<20;j++){  
        if(values[j] > maximum ){
          maximum = values[j];
        }
      }
        
      lcd.clear();
      maximum = maximum*53+14; //convert to mg/dl
      lcd.print(maximum,2);
      
      //final print
      while(1){
        //Serial.print("mg/dl:");
        Serial.println(round(maximum));
        delay(1000);
      }
    }
    delay(500);
  }
  delay(500); 
}

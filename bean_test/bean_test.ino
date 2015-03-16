/* Arduino code for Bean to run the bean_test
 *
 * Please note this code is proof of concept, lacks documentation
 *
 * (c) 2015 D. Cuartielles for Arduino
 *     d.cuartielles@arduino.cc
 */

int pin_num = 0; //Choose the pin where the led is connected to
int val=0;

void setup()
{
    pinMode(pin_num, OUTPUT);
    Serial.begin(9600); //Initialize the Serial Communication or BLE
    //Serial.flush();
}

void loop()
{
        byte input_serial;
       
    while (Serial.available() > 0)
    {
          input_serial = Serial.read(); //Get first byte from Serial
          delay(5);
          if (input_serial == 'L') //Check for L = Led
          {
            pin_num = Serial.parseInt(); //Get the Pin number
            val = Serial.parseInt(); //Get the value to write to this Pin
           
            if (Serial.read() == '\n')//Check for end of the message
            {
             if(pin_num == 0)   {         
                 analogWrite(pin_num, val); //Execute action
                 Serial.println("L/" + String(pin_num) + '/' + String(val));
             }
             else Serial.println("Wrong Pin");
            }
          }
          else
          {
            Serial.println("Wrong Command");
          }
        }
}

/*пин №13 связан со встроенным светодиодом на платах Uno,
   Mega, Nano, Leonardo, Mini и др.
*/
#define LED_PIN  13
#define FAN_PIN  4
#define TIMEOUT_MSEC_TO_OFF 30000
bool isSleep = true;
long timer = 0;
void setup() {
  //открытие Serial-порта со скоростью 9600 бод/c
  Serial.begin(9600);

  //настройка пина со светодиодом в режим выхода
  pinMode(LED_PIN, OUTPUT);
    pinMode(FAN_PIN, OUTPUT);
}

void loop() {

  //если в буфере Serial-порта пришли байты (символы) и ожидают считывания
  if (Serial.available() != 0) {

    //то считываем один полученный байт (символ)
    byte b = Serial.read();

    //если получен символ '1', то светодиод включается
    if (b == 49) {
      isSleep = false;
      timer = millis();
      Serial.print("fan watch dog not sleep");
    }
  }

  if (millis() - timer > TIMEOUT_MSEC_TO_OFF) {
    isSleep = true;
  }

  digitalWrite(LED_PIN, !isSleep);
  digitalWrite(FAN_PIN, !isSleep);
}

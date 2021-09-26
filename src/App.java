import arduino.Arduino;

public class App {
    static final String SUCCESS_RESP = "fan watch dog not sleep";
    static Arduino arduino = null;

    public static void main(String[] args) throws InterruptedException {
        boolean connected = false;
        for (int i = 1; i < 10; i++) {
            arduino = new Arduino("COM" + i, 9600);
            connected = arduino.openConnection();
            if (connected) {
                Thread.sleep(2000);
                if (arduinoIsAvailable()) {
                    System.out.println("Connection COM port success : " + i);
                    break;
                }else{
                    arduino.closeConnection();
                }
            }
        }
        if (!connected) {
            System.out.println("Arduino not found");
            return;
        }

        while (true) {
            if (!arduinoIsAvailable()) {
                break;
            }
            Thread.sleep(5000);
        }
        arduino.closeConnection();
    }

    static private boolean arduinoIsAvailable() {
        arduino.serialWrite('1');
        String resp = arduino.serialRead().trim().replace("\n", " ");
        System.out.println("Arduino resp:" + resp);
        if (!resp.contains(SUCCESS_RESP)) {
            System.out.println("Arduino resp != " + SUCCESS_RESP + ", watch dog SLEEP");
            return false;
        }
        return true;
    }
}
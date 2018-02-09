package arduino;

import java.util.Map;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortListener implements SerialPortEventListener {

  SerialPort port;
  String residue = "";
  Map<String, Object> values;


  public PortListener(SerialPort sport, Map<String, Object> values) {
    this.port = sport;
    this.values = values;
  }


  @Override
  public void serialEvent(SerialPortEvent event) {
    if (event.isRXCHAR() ) {
      try {
        String readValue = port.readString();
        if (readValue != null) {
          residue += readValue;
          int spoint = residue.indexOf(';');

          //get rid of any leading semicolons
          while (spoint == 0 ) {
            residue = residue.substring(1);
            spoint = residue.indexOf(';');
          }
          //harvest all complete messages (ending in a semicolon).
          while (spoint > 0 && spoint < residue.length() ) {
            String head = residue.substring(0, spoint);
            parse(head);
            if ( spoint == residue.length() - 1) {
              residue = "";
            } else {
              residue = residue.substring(spoint+1);
            }
            spoint = residue.indexOf(';');
          }
        }
      } catch (SerialPortException e) {
        e.printStackTrace();
      }
    }
  }


  public void parse(String entry) {
    String[] pair = entry.split(",");
    if (pair.length == 3) {
      String key = pair[0].toLowerCase();
      String type = pair[1];
      char typeChar = type.toLowerCase().charAt(0);
      Object value = null;
      try {
        switch (typeChar) {
          case 'd': value = Double.parseDouble(pair[2]);
                    break;
          case 's': value = pair[2];
                    break;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      if (value != null) {
        values.put(key, value);
      }
    }
  }
}

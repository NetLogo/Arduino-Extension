package arduino;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class PortListener implements SerialPortEventListener {
	
	SerialPort port;
	String residue = "";
	

	public PortListener( SerialPort sport ) {
		this.port = sport;
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


	public void parse( String entry  ) {
		String[] pair = entry.split(",");
		if ( pair.length == 2 ) {
			String key = pair[0];
			String lcKey = key.toLowerCase();
			try {
				double val = Double.parseDouble(pair[1]);
				ArduinoExtension.values.put(lcKey,val);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

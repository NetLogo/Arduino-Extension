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
				residue += port.readString();
				int spoint = residue.indexOf(';');
				
				while (spoint == 0 ) { 
					residue = residue.substring(1); 
					spoint = residue.indexOf(';'); 
				}
				
				while (spoint > 0 && spoint < residue.length() - 1) {
					String head = residue.substring(0, spoint);
					parse(head);
					residue = residue.substring(spoint+1);
					spoint = residue.indexOf(';');
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
			try {
				double val = Double.parseDouble(pair[1]);
				ArduinoExtension.values.put(key,val);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

}

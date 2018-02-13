package arduino

import java.util.{ Map => JMap }

import jssc.SerialPort
import jssc.SerialPortEvent
import jssc.SerialPortEventListener
import jssc.SerialPortException

class PortListener(port: SerialPort, values: JMap[String, Object])
  extends SerialPortEventListener {

  private var residue: String = ""

  override def serialEvent(event: SerialPortEvent): Unit = {
    if (event.isRXCHAR) {
      try {
        val readValue = port.readString()
        if (readValue != null) {
          residue += readValue
          val (newResidue, results) = MessageParser.parseStream(residue)
          residue = newResidue
          results.foreach {
            case Right(ValuePair(key, value)) => values.put(key, value)
            case Left(e: ErrorRecord) =>
          }
        }
      } catch {
        case s: SerialPortException => s.printStackTrace()
      }
    }
  }
}

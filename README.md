arduino-extension
=================

Arduino Extension for NetLogo 5.0.x

This extension provides 'no-frills' communication between NetLogo and a connected Arduino.  

It uses version 2.6.0 of the Java-Simple-Serial-Connector (JSSC) https://code.google.com/p/java-simple-serial-connector/w/list usb-to-serial library.  The extension offers simple write functionality from NetLogo to the board, as well as the ablity to read data that has been sent from the board to the computer and stored in a lookup table of name-value pairs.

CONTENTS:
In addition to the source, which expects to be compiled against a 5.0.x version of NetLogo and the JSSC library, this repo contains a ZIP file that can be unzipped and used without compilation.

The zip file also contains a sample NetLogo model and an Arduino Sketch that corresponds to it.  

TO USE:
For a first use without compiling code, do the following:
1) Acquire the NetLogo software (5.0.4 is the latest version as of this writing).
2) Place the ZIP file from this repo into the extensions subfolder of your NetLogo installation
3) unzip, resulting in a folder called "arduino" under extensions.
4) Acquire an arduino board and install the arduino IDE
5) Use the arduino IDE to edit the Sketch (if desired) and send to the board.  (See elaborate comments in the sketch for recommendations about what to comment out/leave in depending on your setup & circuit on the board.
6) Once the arduino has the sketch loaded on it, it will run that sketch whenever it is powered on.
7) Open the test NetLogo model in this repo.
8) Connect the arduino to a USB port on the computer if it is not still connected from step 5
9) Press OPEN to choose the port to communicate with and establish the connection
10) Use the buttons to send byte commands; use the interface to inspect variable value(s) that your sketch is sending
11) note that by typing arduino:primitives you can get a list of the available commands in the extension.


NOTES:
A NetLogo model using this extension must work in conjunction with an Arduino Sketch.  These two endpoints communicate by way of an application protocol that they define.  For example, if the NetLogo model sends a byte '1' over the wire this may mean something to the Arduino Sketch, which will respond accordingly.  The Arduino Sketch for its own part may send name-value pairs over the serial port, which then can be looked up asynchronously by the NetLogo model.

The modeler is free to build as simple or as complex an application protocol on top of this raw communication mechanism.

The asynchronous nature of the board-to-computer communications has one notable limitation.  If you choose to try to simulate a synchronous, BLOCKING READ communications pattern, (e.g., by sending a byte-based signal to the board, which triggers a response in a known name-value pair), then you are likely to be 'off by one' respone.  That is, if you do the following in NetLogo code:


arduino:write-byte b

show arduino:read "varname"


You are likely to get the value of "varname" from the PRIOR command represented by writing the byte b.  This is because the second line of NetLogo code will execute while the Arduino is off generating a new value for "varname".

There are ways of getting around this (simulating a blocking interface by polling on a value to indicate fresh "news" on "varname"). But this extension works best in settings where the Arduino Sketch is "chatty" and the NetLogo model samples this stream when it needs data.


FUTURE IMPROVEMENTS:
Because I anticipate that most situations will use chatty Arduino sketches, there is an opportunity to support data smoothing.  This is not in place at the moment, but it should be an easy addition.

Also, the primitive arduino:is-open?  can be misleading.  Its return indicates whether the port is open (from the computer's perspective) and whether it has not been closed explicitly.  If you pull the arduino, this will not be detected (at least not immediately).  So, a future improvement would be to do a more elaborate PING that confirmed that communications were in fact occurring.  However, this would impose constraints & requirements on the arduino sketch, which I did not want to do.  Suggestions are welcome....



QUESTIONS:
If you run into problems or have questions about the extension, please email me: cbrady@inquirelearning.com
I have tested early versions of this code on Windows, all versions on Mac.  And I WANT it to work across Mac, Win, and Linux.  So if you have troubles, please let me know.





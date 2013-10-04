arduino-extension
=================

Arduino Extension for NetLogo 5.0.x

This extension provides 'no-frills' communication between NetLogo and a connected Arduino.  

It uses the JSSC usb-to-serial library, offering simple write functionality to the board, as well as the ablity to read 
data that has been sent from the board to the computer and stored in a lookup table of name-value pairs.

CONTENTS:
In addition to the source, which expects to be compiled against a 5.0.x version of NetLogo and the JSSC library, 
this repo contains a ZIP file that can be unzipped and used without compilation.

The zip file also contains a sample NetLogo model and an Arduino Sketch that corresponds to it.  



NOTES:
A NetLogo model using this extension must work in conjunction with an Arduino Sketch.  
These two endpoints communicate by way of an 
application protocol that they define.  For example, if the NetLogo model sends a byte '1' over the wire 
this may mean something to the Arduino Sketch, which will respond accordingly.  The Arduino Sketch for its own part
may send name-value pairs over the serial port, which then can be looked up asynchronously by the NetLogo model.

The modeler is free to build as simple or as complex an application protocol on top of this raw communication mechanism.

The asynchronous nature of the board-to-computer communications has one notable limitation.  
If you choose to try to simulate a synchronous, BLOCKING READ communications pattern,
(by sending a byte-based signal to the board, which triggers a response in a known name-value pair), then
you are likely to be 'off by one' respone.  That is, if you do the following in NetLogo code:

arduino:write-byte b

show arduino:read "varname"

You are likely to get the value of "varname" from the PRIOR command represented by writing the byte b.  
This is because the second line of NetLogo code will execute while the Arduino is off generating a new value for "varname".

There are ways of getting around this (simulating a blocking interface by polling on a value to indicate fresh "news" on "varname")
But this extension works best in settings where the Arduino Sketch is "chatty" and the NetLogo model samples this stream when it needs data.


FUTURE IMPROVEMENTS:
Because I anticipate that most situations will use chatty Arduino sketches, there is an opportunity to support
data smoothing.  This is not in place at the moment, but it should be an easy addition.


QUESTIONS:
If you run into problems or have questions about the extension, please email me: cbrady@inquirelearning.com



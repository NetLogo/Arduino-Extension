ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

ifeq ($(origin NETLOGO), undefined)
  NETLOGO=../..
endif

ifneq (,$(findstring CYGWIN,$(shell uname -s)))
  COLON=\;
  JAVA_HOME := `cygpath -up "$(JAVA_HOME)"`
else
  COLON=:
endif

JAVAC:=$(JAVA_HOME)/bin/javac
SRCS=$(wildcard src/**/*.java)

arduino.jar arduino.jar.pack.gz: $(SRCS) manifest.txt Makefile
	mkdir -p classes
	$(JAVAC) -g -deprecation -Xlint:all -Xlint:-serial -Xlint:-path -encoding us-ascii -source 1.5 -target 1.5 -classpath $(NETLOGO)/NetLogoLite.jar:jssc-2.6.0.jar -d classes $(SRCS)
	jar cmf manifest.txt arduino.jar -C classes .
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip arduino.jar.pack.gz arduino.jar

arduino.zip: arduino.jar
	rm -rf arduino
	mkdir arduino
	cp -rp arduino.jar arduino.jar.pack.gz README.md Makefile src manifest.txt arduino
	zip -rv arduino.zip arduino
	rm -rf arduino

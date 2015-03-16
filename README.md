# Android-to-Bean
Application example on how to connect a PunchThrough Bean board to an Android phone

##What
This is a *proof of concept* of how to connect a PunchThrough Bean to an Android phone. There are two folders in this repository:

* beanlib: this is the library that can be found here:

https://bitbucket.org/littlerobots/beanlib

and that creates an API to get a Bean (which is a BLE device) to talk to an Android device.

_Note: beanlib is licensed under MIT License and therefore that code will remained licensed like that and it has to be mentioned here._

* bean_test: this is my small contribution, it is a simple app that connects to the first Bean it finds and allows you sending three commands from the interface: LED ON, LED OFF and lalala. You should connect an LED to pin 0 on the Bean. There is a bean_test.ino application you should upload to the Bean if you want things to work

_Note: this last chunck of code should be GPLv3 licensed, just because. The Arduino example included._

##Why
Mainly for three reasons:

1) we needed it for a project at Arduino Verkstad, we are running the http://nordiciothackathon.com and we need to display cases where BLE devices can be used in various configurations

2) I hadn't been coding anything fun for a while

3) I happened to have a couple of hours off this Monday night (20150316)

##Who
So far the main contribution to this proof of concept comes from: D. Cuartielles, d.cuartielles@arduino.cc.

##How to contribute
For questions, issues and contributions, use github.


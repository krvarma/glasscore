Google Glass and Spark Core
---------------------------

A fun application to control Spark Core using a Google Glass. "***OK Glass, light on***" turns the Spark Core on-board LED on and "***OK Glass, light off***" turns off the LED. This uses the Tinker API to turn the on-board LED On/Off.

To use this application, first you should setup the Google Glass GDK by following [this guide][1]. Next download the source code and open it in Eclipse editor. Open the *BaseActivity.java* file and replace tokens ***deviceid*** and ***accesstoken*** with Spark Core Device ID and Access Token. Connect the Google Glass and load the application into the Glass (you should turn on USB Debugging on the Glass Settings -> Device Info -> Turn on debug).

**Screenshots**

![enter image description here][2]

![enter image description here][3]


  [1]: https://developers.google.com/glass/develop/gdk/quick-start
  [2]: https://raw.githubusercontent.com/krvarma/glasscore/master/turningon.png
  [3]: https://raw.githubusercontent.com/krvarma/glasscore/master/turningoff.png
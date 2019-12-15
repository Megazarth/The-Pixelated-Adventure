# The-Pixelated-Adventure
A simple RPG Game utilizing the LWJGL Framework.

This project was tested using Netbeans.

If you are interested in seeing the result, after downloading make sure the project folder is on your main drive and not usb flash drive or external drive.

Then after adding the project in Netbeans, do the following:
1. Right-click the project folder in Netbeans -> Properties
2. Click on "Run"
3. In "VM Options" add:
    -Djava.library.path="{path to the project folder}\The-Pixelated-Adventure\lib\native-win"  -Xms512m -XX:+UseConcMarkSweepGC
    
    Example:
    -Djava.library.path="C:\Users\ysuta\Desktop\UC\Coding\The-Pixelated-Adventure\lib\native-win"  -Xms512m -XX:+UseConcMarkSweepGC

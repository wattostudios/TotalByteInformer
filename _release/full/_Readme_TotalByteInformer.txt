////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                    TOTAL BYTE INFORMER                                     //
//                                 Raw File Data Interpreter                                  //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                            Copyright (C) 2002-2022 wattostudios                            //
//                                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                  About Total Byte Informer                                 //
//                                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////

Total Byte Informer is a program that works similar to a hex viewer, but is designed primarily 
to help identify and reverse-engineer unknown file formats. It allows side-by-side viewing of
multiple files for comparison purposes, or open the same file multiple times to view different
parts of the file. Values can be displayed as hex, byte, or char, and null bytes are displayed
in a different color for easy identification. Common data representations are displayed on a
single screen in both little-endian and big-endian orders, and you can perform simple bit-shift
and bit-swap operations on the data of a file.

For further information, downloads, and help, visit the website at https://www.watto.org

////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                 Installation Instructions                                  //
//                                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////

Total Byte Informer requires you to have Java Runtime Environment 8.0 (or 1.8) or later 
installed on your computer. This is a free download from http://www.java.com 

When you install Total Byte Informer, several shortcuts are created in the Start Menu or the main
Windows tile screen. If one of the shortcuts doesn't work for you, try one of the other ones.
More detailed instructions can be found below, or on the Total Byte Informer website.

                                 Preferred Windows Method
1. Install Java Runtime Environment 8.0 (or 1.8), or later, from http://www.java.com
2. Download the EXE installer from https://github.com/wattostudios/TotalByteInformer
3. Double-click on TotalByteInformer.exe to run the installation program
4. Double-click on any one of the following programs to run Total Byte Informer...
   a. TotalByteInformer.exe
   b. TotalByteInformer.bat
   c. TotalByteInformer.jar
   d. TotalByteInformer.ps1

                                Alternative Windows Method
1. Install Java Runtime Environment 8.0 (or 1.8), or later, from http://www.java.com
2. Download the ZIP archive from https://github.com/wattostudios/TotalByteInformer
3. Right-click on the Total Byte Informer ZIP and choose Extract
4. Follow the screens, and tell it to extract the file to c:\tbi
5. Open a Command Prompt window (you can search for it in the Start Menu or Windows Tile screen)
6. Change to the Total Byte Informer directory by typing "cd c:\tbi"
7a. Run Total Byte Informer by typing "java -jar TotalByteInformer.jar"
7b. If that doesn't work, try typing "c:\java\bin\java -jar TotalByteInformer.jar"
8. If none of that works, run Windows Explorer and double-click any of the following files
   in the c:\tbi directory...
      a. TotalByteInformer.exe
      b. TotalByteInformer.bat
      c. TotalByteInformer.jar
      d. TotalByteInformer.ps1

                                 Other Operating Systems
1. Install Java Runtime Environment 8.0 (or 1.8), or later, from http://www.java.com
2. Download TotalByteInformer.zip from https://github.com/wattostudios/TotalByteInformer
3. Using any zip program, unzip TotalByteInformer.zip to /usr/local/bin/tbi
4. Open a Command Prompt window
5. Change to the Total Byte Informer directory by typing "cd /usr/local/bin/tbi"
6a. Run Total Byte Informer by typing "java -jar TotalByteInformer.jar"
6b. If that doesn't work, try typing "/usr/java/latest/bin/java -jar TotalByteInformer.jar"
7. If you are using a Linux-based operating system, you could also try one of the following 
   script files in the /usr/local/bin/tbi directory...
      a. TotalByteInformer.sh
      b. TotalByteInformer.csh
      c. TotalByteInformer.ksh
  
* Please note: While Java can theoretically run on many different operating systems, this
  doesn't necessarily mean that Total Byte Informer works correctly on those operating systems. 
  Total Byte Informer is only officially supported on Windows.

////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      Version History                                       //
//                                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////

Version 3.0
[I] New major release with the following significant changes...
    [I] Built on Java 8.0, implementation of Java Packages and other code enhancements
    [I] Development using Eclipse, build and deployment using Apache Ant
    [I] WSProgram 4.0 now used as the program base
    [I] New theme ButterflyLookAndFeel implemented by default
    [I] Using launch4J for building the Windows executable, NSIS 3.0 for the installer
    [I] Implementation of usability improvements and simplifications over the previous release
[+] XOR can now perform bitswapping on a multi-value key, with values separated by space or comma

Version 2.0
[I] Complete rebuild to use WSProgram 3.0, and to improve on the poor performance of version 1.0
[+] Added in a notes panel again, for writing things that you need to remember
[-] Removed features that are not implemented, such as block shading.
[A] The table columns can now be set to any width - there is no longer a minimum width restriction
[B] Fixed a bug where the BitShading was not drawing correctly

Version 1.0
[I] Complete rebuild to utilise the new Program Template 2.0, and improve ease of use
[+] Created SidePanels to display data and program options
[T] Created and tested the loading and display of file data
[+] Implemented cell shading, and changing offsets/numColumns in the table

Version 0.2
[I] Restructuring and reorganisation of the program to include settings, languages, menus, etc.
[+] Creation of the basic structure and interface
[+] Re-implimented loading files and the showing of the byte table
[+] Re-implemented changing between byte representation format
[+] Re-implemented the display of format information when a byte is clicked
[+] Implemented shading based on the value of each byte, in an aqua color
[+] Implemented the changing of file endian format
[+] Added in the options, about, help, statusbar, and menubar
[A] Bytes in the table that are past the end of the file are now shaded green
[B] Fixed a bug where both byte tables would use the same format settings

Version 0.16
[+] Added a filename field to the interface to show the currently opened file name
[+] Added a current offset field to the interface to show the clicked position
[+] Added a field to allow the user to change the number of shown bytes
[A] Null byte values are now shaded with a light yellow to stand out
[A] Selected cells now have a border around them
[B] Fixed a bug where the Byte(B) field would show the wrong value

Version 0.15
[+] Added checkboxes to allow users to toggle the display of Strings, Hex, and Number representations of bytes
[A] Removed the String, Float, and Double fields from the display, as they aren't used much, and took up too much space

Version 0.1
[I] Initial program

Legend
[+] Added something
[-] Removed something
[B] Bug fix
[A] Alteration
[T] Testing
[I] General Information
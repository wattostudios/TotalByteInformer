# TOTAL BYTE INFORMER
Raw File Data Interpreter
http://www.watto.org

## About Total Byte Informer
There are literally thousands of different file formats that exist in the computing world,
covering a wide range of different purposes and design structures. For people who wish to
examine these formats more closely, they commonly use a hex editor to display the contents of
these files. Hex editing tools can be very powerful, but they can also be very difficult to use,
particularly if you cannot quickly and easily read hex.

Total Byte Informer is a program that was originally written to assist in the examination of
game archives, but can be used for any type of file. Customization of the user interface is
paramount to provide for many different people and purposes: for example, file data can be
displayed as hex, byte values, or characters. A range of different shadings can be applied to
visualise the structure of a file format in colors rather than numbers. You can even perform
bit swapping or shifting on the data before it is displayed.

Total Byte Informer is not an editor, so if you need to make changes to file data then you will
still need to use a hex editor; Total Byte Informer is a simple data viewing program to make
examination easier for all types of people.

For further information, downloads, and help, visit the website at http://www.watto.org/

## GitHub Information

### What this is...
* This is the source, and complimentary files, for Total Byte Informer. 
* It is intended that this be used to assist people to analyse unknown data files

### What this is *not*...
* This is not a Hex Editor - it doesn't *edit* anything
* This is a tool written purely for my benefit and use. If you want to use it, that's great, but
  it isn't up to the standard of a proper product. There will be bugs, it doesn't look particularly
  pretty, but it does the job it's intended to be used for.
* This tool is completely unsupported by wattostudios.
  
## Installation/Build Prerequisites

Total Byte Informer requires you to have Java Runtime Environment 6.0 (or 1.6) or later installed on
your computer. This is a free download from http://www.java.com 

## Version History

* Version 2.0
  * [I] Complete rebuild to use WSProgram 3.0, and to improve on the poor performance of version 1.0
  * [+] Added in a notes panel again, for writing things that you need to remember
  * [-] Removed features that are not implemented, such as block shading.
  * [A] The table columns can now be set to any width - there is no longer a minimum width restriction
  * [B] Fixed a bug where the BitShading was not drawing correctly

* Version 1.0
  * [I] Complete rebuild to utilise the new Program Template 2.0, and improve ease of use
  * [+] Created SidePanels to display data and program options
  * [T] Created and tested the loading and display of file data
  * [+] Implemented cell shading, and changing offsets/numColumns in the table

* Version 0.2
  * [I] Restructuring and reorganisation of the program to include settings, languages, menus, etc.
  * [+] Creation of the basic structure and interface
  * [+] Re-implimented loading files and the showing of the byte table
  * [+] Re-implemented changing between byte representation format
  * [+] Re-implemented the display of format information when a byte is clicked
  * [+] Implemented shading based on the value of each byte, in an aqua color
  * [+] Implemented the changing of file endian format
  * [+] Added in the options, about, help, statusbar, and menubar
  * [A] Bytes in the table that are past the end of the file are now shaded green
  * [B] Fixed a bug where both byte tables would use the same format settings

* Version 0.16
  * [+] Added a filename field to the interface to show the currently opened file name
  * [+] Added a current offset field to the interface to show the clicked position
  * [+] Added a field to allow the user to change the number of shown bytes
  * [A] Null byte values are now shaded with a light yellow to stand out
  * [A] Selected cells now have a border around them
  * [B] Fixed a bug where the Byte(B) field would show the wrong value

* Version 0.15
  * [+] Added checkboxes to allow users to toggle the display of Strings, Hex, and Number representations of bytes
  * [A] Removed the String, Float, and Double fields from the display, as they aren't used much, and took up too much space

* Version 0.1
  * [I] Initial program

* Legend
  * [+] Added something
  * [-] Removed something
  * [B] Bug fix
  * [A] Alteration
  * [T] Testing
  * [I] General Information
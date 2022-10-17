# TOTAL BYTE INFORMER
Raw File Data Interpreter
http://www.watto.org

## About Total Byte Informer
Total Byte Informer is a program that works similar to a hex viewer, but is designed primarily 
to help identify and reverse-engineer unknown file formats. It allows side-by-side viewing of
multiple files for comparison purposes, or open the same file multiple times to view different
parts of the file. Values can be displayed as hex, byte, or char, and null bytes are displayed
in a different color for easy identification. Common data representations are displayed on a
single screen in both little-endian and big-endian orders, and you can perform simple bit-shift
and bit-swap operations on the data of a file.

For further information, downloads, and help, visit the website at https://www.watto.org

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
  
## Installation and Build Prerequisites

Total Byte Informer requires you to have Java Runtime Environment 8.0 (or 1.8) or later installed on
your computer. This is a free download from http://www.java.com 

We maintain the project using Eclipse. You should be able to download the repository, and import
it into Eclipse via the menu path File -- Open Projects from File System. The main entry point
is under src/org/watto/tbi/TotalByteInformer.java

## Operating System Support

Total Byte Informer is only officially supported on Windows. While Java can theoretically run on many
different operating systems, this doesn't necessarily mean that Total Byte Informer works correctly
or completely on those operating systems.

We have performed some very basic tests on Linux-based operating systems (does the program open,
and does it read an archive) - that's about it. We have provided some extremely basic scripts to
assist running in ksh/csh/bash shells, however you should evaluate the scripts and adjust them
appropriately for your system.

We would expect that most functions would operate correctly in non-Windows environments, but
offer no guarantees of this, and in some situations we may not be able to make it work in those
environments at all.

## Version History

* Version 3.0
  * [I] New major release with the following significant changes...
    * [I] Built on Java 8.0, implementation of Java Packages and other code enhancements
    * [I] Development using Eclipse, build and deployment using Apache Ant
    * [I] WSProgram 4.0 now used as the program base
    * [I] New theme ButterflyLookAndFeel implemented by default
    * [I] Using launch4J for building the Windows executable, NSIS 3.0 for the installer
    * [I] Implementation of usability improvements and simplifications over the previous release
  * [+] XOR can now perform bitswapping on a multi-value key, with values separated by space or comma

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
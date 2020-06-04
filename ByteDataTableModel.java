////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                    TOTAL BYTE INFORMER                                     //
//                                 Raw File Data Interpreter                                  //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2002-2007  WATTO Studios                           //
//                                                                                            //
// This program is free software; you can redistribute it and/or modify it under the terms of //
// the GNU General Public License published by the Free Software Foundation; either version 2 //
// of the License, or (at your option) any later versions. This program is distributed in the //
// hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranties //
// of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License //
// at http://www.gnu.org for more details. For updates and information about this program, go //
// to the WATTO Studios website at http://www.watto.org or email watto@watto.org . Thanks! :) //
//                                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////

import org.watto.*;
import org.watto.manipulator.*;

import javax.swing.table.*;
import javax.swing.event.*;
import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class ByteDataTableModel implements TableModel{

  public static final int SHADING_NONE = 0;
  public static final int SHADING_GRAYSCALE = 1;
  public static final int SHADING_RANGE = 2;
  public static final int SHADING_HALF = 3;
  public static final int SHADING_BIT = 4;

  public static final int SWAP_NONE = 0;
  public static final int SWAP_XOR = 1;
  public static final int SWAP_AND = 2;
  public static final int SWAP_NAND = 3;
  public static final int SWAP_OR = 4;
  public static final int SWAP_NOR = 5;
  public static final int SWAP_NOT = 6;

  public static final int SHIFT_LEFT = 7;
  public static final int SHIFT_LEFT_WRAP = 8;
  public static final int SHIFT_RIGHT = 9;
  public static final int SHIFT_RIGHT_WRAP = 10;

  //ByteData[] byteData = new ByteData[0];
  byte[] byteData = new byte[0];
  File file;
  long offset = 0;
  String name;

  /** actual number of bytes read into the array (incase offset+length > arcSize) **/
  int numRead = 0;


  int shadingType      = 0;
  int swapType         = 0;
  int swapValue        = 0;
  boolean showByte     = false;
  boolean showHex      = false;
  boolean showChar     = true;
  boolean showUnicode  = false;
  boolean littleEndian = true;
  boolean useNegatives = true;
  int numColumns       = 32;


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public ByteDataTableModel(String name){
    this.name = name;
    this.shadingType  = Settings.getInt(name + "_ShadingType");
    this.showByte     = Settings.getBoolean(name + "_ShowByteValue");
    this.showHex      = Settings.getBoolean(name + "_ShowHexValue");
    this.showChar     = Settings.getBoolean(name + "_ShowCharValue");
    this.showUnicode  = Settings.getBoolean(name + "_ShowUnicodeValue");
    this.littleEndian = Settings.getBoolean(name + "_LittleEndian");
    this.useNegatives = Settings.getBoolean(name + "_UseNegatives");
    this.numColumns   = Settings.getInt(name + "_NumberOfColumns");
    //this.swapType     = Settings.getInt(name + "_SwapType"); // always reset to 0
    }


/**
**********************************************************************************************
Used by clone();
**********************************************************************************************
**/
  //public ByteDataTableModel(ByteData[] byteData, File file, long offset, String name){
  public ByteDataTableModel(byte[] byteData, File file, long offset, String name){
    this(name);
    //this.byteData = byteData;
    this.file = file;
    this.offset = offset;
    setOffset(offset);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void addTableModelListener(TableModelListener listener){
    }


/**
**********************************************************************************************
gets the bytes, without XOR conversions
**********************************************************************************************
**/
  public byte[] getBytes(int numBytes, int cellNumber){
    byte[] bytes = new byte[numBytes];
    for (int i=0;i<numBytes;i++){
      if (cellNumber >= byteData.length || cellNumber < 0){
        bytes[i] = 0;
        }
      else {
        //bytes[i] = byteData[cellNumber].getValue();
        bytes[i] = byteData[cellNumber];
        }
      cellNumber++;
      }
    return bytes;
    }


/**
**********************************************************************************************
gets the bytes, including XOR conversions
**********************************************************************************************
**/
  public byte[] getDisplayedBytes(int numBytes, int cellNumber){
    byte[] bytes = new byte[numBytes];
    for (int i=0;i<numBytes;i++){
      if (cellNumber >= byteData.length || cellNumber < 0){
        bytes[i] = 0;
        }
      else {
        //bytes[i] = byteData[cellNumber].getValue();
        bytes[i] = ((ByteData)getValueAt(cellNumber)).getValue();
        }
      cellNumber++;
      }
    return bytes;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Class getColumnClass(int column){
    return ByteData.class;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getColumnCount(){
    return numColumns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnCount(int numColumns){
    if (numColumns <= 0){
      return;
      }
    this.numColumns = numColumns;
    Settings.set(name + "_NumberOfColumns",numColumns);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getColumnName(int column){
    return "";
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getFilename(){
    if (file == null){
      return "";
      }
    return file.getAbsolutePath();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getReadLength(){
    return Settings.getInt(name + "_ReadLength");
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getRowCount(){
    int numColumns = getColumnCount();
    int numRows = byteData.length / numColumns;

    if (byteData.length%numColumns != 0){
      numRows++;
      }

    return numRows;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getShadingType(){
    return shadingType;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getSwapType(){
    return swapType;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getSwapValue(){
    return swapValue;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getValueAt(int row, int column){
    int cellNumber = getColumnCount()*row + column;
    return getValueAt(cellNumber);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getValueAt(int cellNumber){
    if (cellNumber >= numRead){
      return new ByteData(-999);
      }

    if (swapType == SWAP_NONE){
      // get the value normally
      //return new ByteData(byteData[cellNumber],shadingType,showByte,showHex,showChar,showUnicode,littleEndian,useNegatives);
      return ByteData.getInstance(byteData[cellNumber],shadingType,showByte,showHex,showChar,showUnicode,littleEndian,useNegatives);
      }
    else {
      // do a swap of some type
      byte changedValue = byteData[cellNumber];
      if (swapType == SWAP_XOR){
        changedValue ^= swapValue;
        }
      else if (swapType == SWAP_OR){
        changedValue |= swapValue;
        }
      else if (swapType == SWAP_NOR){
        changedValue |= swapValue;
        changedValue ^= 255;
        }
      else if (swapType == SWAP_AND){
        changedValue &= swapValue;
        }
      else if (swapType == SWAP_NAND){
        changedValue &= swapValue;
        changedValue ^= 255;
        }
      else if (swapType == SWAP_NOT){
        changedValue ^= 255;
        }
      else if (swapType == SHIFT_LEFT){
        changedValue <<= swapValue;
        }
      //else if (swapType == SHIFT_LEFT_WRAP){
      //  changedValue <<<= swapValue;
      //  }
      else if (swapType == SHIFT_RIGHT){
        changedValue >>= swapValue;
        }
      else if (swapType == SHIFT_RIGHT_WRAP){
        changedValue >>>= swapValue;
        }
      else {
        // return the normal value if it is an invalid swap type
        return ByteData.getInstance(byteData[cellNumber],shadingType,showByte,showHex,showChar,showUnicode,littleEndian,useNegatives);
        }
      return ByteData.getInstance(changedValue,shadingType,showByte,showHex,showChar,showUnicode,littleEndian,useNegatives);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isCellEditable(int row, int column){
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isLittleEndian(){
    return littleEndian;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isShowingByte(){
    return showByte;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isShowingChar(){
    return showChar;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isShowingHex(){
    return showHex;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isShowingUnicode(){
    return showUnicode;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadFile(File file){
    if (file == null){
      return;
      }

    this.file = file;

    setOffset(0);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setOffset(long offset){
    if (file == null || !file.exists()){
      return;
      }

    try {
      this.offset = offset;
      int readLength = getReadLength();

      FileManipulator fm = new FileManipulator(file,"r");
      fm.seek(offset);

      if (offset >= file.length()){
        numRead = 0;
        }
      else if (offset+readLength >= file.length()){
        numRead =(int)(file.length()-offset);
        }
      else {
        numRead = readLength;
        }

      byteData = fm.read(readLength);

      fm.close();

      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setReadLength(int readLength){
    Settings.set(name + "_ReadLength",readLength);
    setOffset(offset);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setShowByte(boolean showByte){
    this.showByte = showByte;
    Settings.set(name + "_ShowByteValue",showByte);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setShowChar(boolean showChar){
    this.showChar = showChar;
    Settings.set(name + "_ShowCharValue",showChar);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setShowHex(boolean showHex){
    this.showHex = showHex;
    Settings.set(name + "_ShowHexValue",showHex);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setShowUnicode(boolean showUnicode){
    this.showUnicode = showUnicode;
    Settings.set(name + "_ShowUnicodeValue",showUnicode);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setBigEndian(){
    this.littleEndian = false;
    Settings.set(name + "_LittleEndian",false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setLittleEndian(){
    this.littleEndian = true;
    Settings.set(name + "_LittleEndian",true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setUseNegatives(boolean useNegatives){
    this.useNegatives = useNegatives;
    Settings.set(name + "_UseNegatives",useNegatives);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setShadingType(int shadingType){
    this.shadingType = shadingType;
    Settings.set(name + "_ShadingType",shadingType);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSwapType(int swapType){
    this.swapType = swapType;
    Settings.set(name + "_SwapType",swapType);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSwapValue(int swapValue){
    this.swapValue = swapValue;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void removeTableModelListener(TableModelListener listener){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setValueAt(Object value, int row, int column){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public long getOffset(){
    return offset;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public File getFile(){
    return file;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getName(){
    return name;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean getUseNegatives(){
    return useNegatives;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public ByteDataTableModel clone(){
    return new ByteDataTableModel(byteData,file,offset,name);
    }



}
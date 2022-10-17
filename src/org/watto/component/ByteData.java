/*
 * Application:  Total Byte Informer
 * Author:       wattostudios
 * Website:      http://www.watto.org
 * Copyright:    Copyright (c) 2002-2022 wattostudios
 *
 * License Information:
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later versions. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranties
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License at http://www.gnu.org for more
 * details. For further information on this application, refer to the authors' website.
 */

package org.watto.component;

import java.awt.Color;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class ByteData {

  public static final int SHADING_NONE = 0;
  public static final int SHADING_GRAYSCALE = 1;
  public static final int SHADING_HALF = 3;
  
  public static final int DISPLAY_CHAR = 1;
  public static final int DISPLAY_BYTE = 2;
  public static final int DISPLAY_HEX = 3;

  
  static int value = 0;

  static int shadingType;
  static int displayType;

  static ByteData instance = new ByteData(0);

  /**
  **********************************************************************************************
  Constructor
  **********************************************************************************************
  **/
  public ByteData(int valueIn) {
    value = valueIn;
  }

  /**
  **********************************************************************************************
  Constructor
  **********************************************************************************************
  **/
  public static ByteData getInstance(int valueIn, int shadingTypeIn, int displayTypeIn) {
    value = valueIn;
    shadingType = shadingTypeIn;
    displayType = displayTypeIn;

    return instance;
  }
  
  
  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/  
  public boolean isNull() {
    return value == 0;
  }
  
  
  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/  
  public boolean isEOF() {
    return value == -999;
  }


  
  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/
  public Color getShadingColor() {
    if (shadingType == SHADING_GRAYSCALE) {
      return getGrayscaleColor();
    }
    else if (shadingType == SHADING_HALF) {
      return getHalfColor();
    }
    else {
      return null;
    }
  }


  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/
  public Color getGrayscaleColor() {
    int colValue = getPositiveValue();
    int color = 255 - (colValue % 255);
    return new Color(color, color, color);
  }

  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/
  public Color getHalfColor() {
    int colValue = getPositiveValue() / 2;
    int color = 255 - (colValue % 255) - 50;

    if (value < 0) {
      return new Color(color, color, 255);
    }
    else {
      return new Color(255, color, color);
    }
  }



  
  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/
  public String getDisplayValue() {
    if (value == -999) {
      return ""; // EOF
    }
    
    if (displayType == DISPLAY_CHAR) {
      String text = "";
      if (value <= 32) {
        text = " ";
      }
      else {
        text = "" + (char) value;
      }
      
      return text;
    }
    else if (displayType == DISPLAY_HEX) {
      int byteVal = value;
      if (value < 0) {
        byteVal = 256 + byteVal;
      }
      String hex = Integer.toHexString(byteVal).toUpperCase();
      if (hex.length() == 1) {
        hex = "0" + hex;
      }

      return hex;
    }
    else if (displayType == DISPLAY_BYTE) {
      return "" + value;
    }
    
    return "";
  }
  

  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/
  public byte getValue() {
    return (byte) value;
  }

  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/
  public int getPositiveValue() {
    int colValue = value;
    if (value < 0) {
      return (256 + colValue);
    }
    return colValue;
  }

}
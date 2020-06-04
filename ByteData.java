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

import org.watto.plaf.AquanauticTheme;
import org.watto.manipulator.DataConverter;

import java.awt.*;
import javax.swing.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class ByteData extends JLabel{

  static int value = 0;

  static int shadingType;
  static boolean showByte     = false;
  static boolean showHex      = false;
  static boolean showChar     = true;
  static boolean showUnicode  = false;
  static boolean littleEndian = true;
  static boolean useNegatives = true;


  boolean selected = false; // for rendering

  public final static Color COLOR_GREEN    = new Color(200,255,200);
  public final static Color COLOR_BLUE     = Color.BLUE;
  public final static Color COLOR_RED      = Color.RED;
  public final static Color COLOR_YELLOW   = new Color(255,255,200);
  public final static Color COLOR_WHITE    = Color.WHITE;
  public final static Color COLOR_BLACK    = Color.BLACK;
  public final static Color COLOR_LIGHT    = AquanauticTheme.COLOR_LIGHT;
  public final static Color COLOR_DARK     = AquanauticTheme.COLOR_DARK;
  public final static Color COLOR_BITSHADE = new Color(40,40,0);

  static ByteData instance = new ByteData(0);




/**
**********************************************************************************************
  Constructor
**********************************************************************************************
**/
  public ByteData(int value){
    this.value = value;
    }


/**
**********************************************************************************************
  Constructor
**********************************************************************************************
**/
  public static ByteData getInstance(int in_value, int in_shadingType, boolean in_showByte, boolean in_showHex, boolean in_showChar, boolean in_showUnicode, boolean in_littleEndian, boolean in_useNegatives){
    value = in_value;
    shadingType = in_shadingType;
    showByte = in_showByte;
    showHex = in_showHex;
    showChar = in_showChar;
    showUnicode = in_showUnicode;
    littleEndian = in_littleEndian;
    useNegatives = in_useNegatives;

    //if (!littleEndian){
    //  value = DataConverter.changeFormat((byte)in_value);
    //  }

    return instance;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSelected(boolean selected){
    this.selected = selected;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getMinimumSize(){
    return new Dimension(0,0);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g){
    paint(g,getWidth(),getHeight());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, int width, int height){
    Color bgColor = COLOR_WHITE;

    if (value == -999){
      bgColor = COLOR_GREEN;
      }
    else if (value == 0){
      if (selected){
        bgColor = COLOR_LIGHT;
        }
      else {
        bgColor = COLOR_YELLOW;
        }
      }
    else {

      if (shadingType == ByteDataTableModel.SHADING_NONE){
        }
      else if (shadingType == ByteDataTableModel.SHADING_GRAYSCALE){
        bgColor = getGrayscaleColor(1);
        }
      else if (shadingType == ByteDataTableModel.SHADING_HALF){
        bgColor = getHalfColor(1);
        }
      else if (shadingType == ByteDataTableModel.SHADING_BIT){
        if (value == 0){
          bgColor = COLOR_BITSHADE;
          }
        }

      if (selected){
        bgColor = COLOR_LIGHT;
        }

      }


    g.setColor(bgColor);
    g.fillRect(0,0,width,height);

    if (selected){
      g.setColor(COLOR_DARK);
      }
    else {
      g.setColor(COLOR_LIGHT);
      }
    g.drawRect(0,0,width-1,height-1);


    if (shadingType == ByteDataTableModel.SHADING_BIT){
      paintBitShading(g,width,height);
      }

    paintValue(g,width,height);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Color getGrayscaleColor(int blockNum){
    int colValue = getPositiveValue();
    int color = 255 - (colValue%255);
    return new Color(color,color,color);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Color getHalfColor(int blockNum){
    int colValue = getPositiveValue() / 2;
    int color = 255 - (colValue%255) - 50;

    if (value < 0){
      return new Color(color,color,255);
      }
    else {
      return new Color(255,color,color);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintBitShading(Graphics g, int width, int height){
    boolean[] bits = DataConverter.toBooleanArray((byte)value);
    int blockSize = width/8;

    g.setColor(COLOR_BLACK);

    for (int i=0,x=0;i<8;i++,x+=blockSize){
      if (! bits[i]){
        continue;
        }

      g.fillRect(x,0,x+blockSize,height);
      }

    // should fill in the remainder pixels!?

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintValue(Graphics g, int width, int height){
    int fromLeft = 2;

    if (showChar){
      String text = "";
      if (value <= 32){
        text = " ";
        }
      else {
        text = "" + (char)value;
        }

      g.setColor(COLOR_RED);
      g.drawString(text,fromLeft,height-3);
      fromLeft += 10;
      }


    if (showHex){
      int byteVal = value;
      if (value < 0){
        byteVal = 256 + byteVal;
        }
      String hex = Integer.toHexString(byteVal).toUpperCase();
      if (hex.length() == 1){
        hex = "0" + hex;
        }

      g.setColor(COLOR_BLUE);
      g.drawString(hex,fromLeft,height-3);
      fromLeft += 16;
      }

    if (showByte){
      String number;
      if (useNegatives){
        number = "" + value;
        }
      else {
        number = "" + getPositiveValue();
        }

      if (number.length() == 1){
        fromLeft += 7;
        }
      if (number.length() == 2){
        fromLeft += 3;
        }

      g.setColor(COLOR_BLACK);
      g.drawString(number,fromLeft,height-3);
      fromLeft += 25;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public byte getValue(){
    return (byte)value;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getPositiveValue(){
    int colValue = value;
    if (value < 0){
      return (256 + colValue);
      }
    return colValue;
    }


}
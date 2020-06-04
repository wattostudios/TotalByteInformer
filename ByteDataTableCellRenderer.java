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

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class ByteDataTableCellRenderer implements TableCellRenderer{

  ByteData value;

/**
**********************************************************************************************
  Constructor
**********************************************************************************************
**/
  public ByteDataTableCellRenderer(){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    ByteData data = (ByteData)value;
    data.setSelected(isSelected);
    return data;
    }


}
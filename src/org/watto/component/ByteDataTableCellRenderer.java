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

import javax.swing.table.*;
import org.watto.plaf.LookAndFeelManager;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class ByteDataTableCellRenderer /*implements TableCellRenderer*/ extends DefaultTableCellRenderer{

  private static final long serialVersionUID = 1L;
  //ByteData value;

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
/*
  public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    ByteData data = (ByteData)value;
    data.setSelected(isSelected);
    return data;
    }
*/
  
  /***********************************************************************************************
  Gets the renderer for the <code>table</code> cell <code>value</code>
  @param table the <code>JTable</code> being painted
  @param value the value of the cell being painted
  @param isSelected whether the cell is selected or not
  @param hasFocus whether the cell has focus or not
  @param row the row in the <code>table</code> where this cell appears
  @param column the column in the <code>table</code> where this cell appears
  @return the renderer for this cell
  ***********************************************************************************************/
  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    // detect nulls so that they can be colored specially
    boolean isNull = false;
    boolean isEOF = false;
    
    String displayValue = "";
    if (value instanceof ByteData) {
      ByteData dataValue = (ByteData)value;
      displayValue = dataValue.getDisplayValue();
      isNull = dataValue.isNull();
      isEOF = dataValue.isEOF();
    }
    
    
    


    JComponent rend = (JComponent) super.getTableCellRendererComponent(table, displayValue, isSelected, hasFocus, row, column);

    rend.setForeground(LookAndFeelManager.getTextColor());

    // determine whether this is the last column or row of the table.
    // this is so we don't paint double-thickness borders in the grid.
    boolean firstRow = (row == 0);
    boolean firstColumn = (column == 0);

    Color borderColor;
    Color backgroundColor;

    if (isSelected/* || hasFocus*/) {
      borderColor = LookAndFeelManager.getDarkColor();

      backgroundColor = LookAndFeelManager.getLightColor();

      if (row % 2 == 0) {
        // leave color as is
      }
      else {
        // darker row
        int r = backgroundColor.getRed() - 25;
        if (r < 0) {
          r = 0;
        }
        int g = backgroundColor.getGreen() - 25;
        if (g < 0) {
          g = 0;
        }
        int b = backgroundColor.getBlue() - 25;
        if (b < 0) {
          b = 0;
        }
        backgroundColor = new Color(r, g, b);
      }

      if (table.getSelectionModel().getMinSelectionIndex() == row) {
        firstRow = true;
      }
    }
    else {
      borderColor = LookAndFeelManager.getLightColor();
      
      // see if the cell has a special shading applied to it or not
      Color shadingColor = null;
      if (value instanceof ByteData) {
        if (!isNull && !isEOF) {
          shadingColor = ((ByteData)value).getShadingColor();
        }
      }
      
      if (shadingColor != null) {
        // cell already has a special color for the shading
        backgroundColor = shadingColor; 
      }
      else {
        // display the cell in normal colors
      
      if (row % 2 == 0) {
        // leave color as is
        backgroundColor = Color.WHITE;

        if (isNull) {
          backgroundColor = new Color(255, 255, 230); // light yellow for nulls
        }
        else if (isEOF) {
          backgroundColor = new Color(215, 215, 255); // light blue for EOF
        }

      }
      else {
        // darker row
        backgroundColor = new Color(230, 230, 230);

        if (isNull) {
          backgroundColor = new Color(230, 230, 205); // light yellow for nulls
        }
        else if (isEOF) {
          backgroundColor = new Color(190, 190, 215); // light blue for EOF
        }

      }
      
      }

    }

    rend.setBackground(backgroundColor);

    Border coloredBorder;
    Border emptyBorder;

    // First column has a bolder border on the right
    if (isSelected) {
      coloredBorder = new MatteBorder(1, 1, 1, 1, borderColor);
      emptyBorder = null;
    }
    else if (firstColumn && firstRow) {
      coloredBorder = new MatteBorder(1, 1, 1, 1, borderColor);
      emptyBorder = null;
    }
    else if (firstColumn) {
      coloredBorder = new MatteBorder(0, 1, 1, 1, borderColor);
      emptyBorder = new EmptyBorder(1, 0, 0, 0);
    }
    else if (firstRow) {
      coloredBorder = new MatteBorder(1, 0, 1, 1, borderColor);
      emptyBorder = new EmptyBorder(0, 1, 0, 0);
    }
    else {
      coloredBorder = new MatteBorder(0, 0, 1, 1, borderColor);
      emptyBorder = new EmptyBorder(1, 1, 0, 0);
    }

    rend.setBorder(new CompoundBorder(coloredBorder, emptyBorder));

    if (rend instanceof JLabel) {
      JLabel label = (JLabel) rend;

      label.setHorizontalAlignment(CENTER);

      //rend.setFont(monoFont);

      label.setIcon(null);
    }

    //rend.setSize(new Dimension((int)rend.getMinimumSize().getWidth(),table.getRowHeight()));

    return rend;
  }
  

}
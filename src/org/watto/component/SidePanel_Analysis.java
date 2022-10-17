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

import org.watto.*;

import org.watto.event.*;
import org.watto.io.converter.BooleanArrayConverter;
import org.watto.io.converter.DoubleConverter;
import org.watto.io.converter.FloatConverter;
import org.watto.io.converter.HexConverter;
import org.watto.io.converter.IntConverter;
import org.watto.io.converter.LongConverter;
import org.watto.io.converter.ShortConverter;
import org.watto.xml.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_Analysis extends WSPanelPlugin implements SidePanelClone,
                                                                    WSEnterableInterface,
                                                                    WSKeyableInterface {


  
  private static final long serialVersionUID = 1L;
  
  WSSidePanelHolder holder;
  WSTable table;

  WSTextField offsetStart;
  WSTextField filename;
  WSTextField fileSize;
  WSTextField numColumns;
  WSTextField offsetRelative;
  WSTextField offsetAbsolute;
  
  WSTextField valueByteLittle;
  WSTextField valueShortLittle;
  WSTextField valueIntLittle;
  WSTextField valueLongLittle;
  WSTextField valueFloatLittle;
  WSTextField valueDoubleLittle;
  WSTextField valueBinaryLittle;
  WSTextField valueHexLittle;
  
  WSTextField valueByteBig;
  WSTextField valueShortBig;
  WSTextField valueIntBig;
  WSTextField valueLongBig;
  WSTextField valueFloatBig;
  WSTextField valueDoubleBig;
  WSTextField valueBinaryBig;
  WSTextField valueHexBig;

  WSTextArea notesArea;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_Analysis(){
    super(new XMLNode());
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
@param caller the object that contains this component, created this component, or more formally,
              the object that receives events from this component.
**********************************************************************************************
**/
  public SidePanel_Analysis(XMLNode node){
    super(node);
    }



///////////////
//
// Configurable
//
///////////////


/**
**********************************************************************************************
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void toComponent(XMLNode node){
    super.toComponent(node);

    setLayout(new BorderLayout());

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File("interface" + File.separator + "SidePanel_Analysis.xml"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.toComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    ComponentRepository.add(this);


    offsetStart    = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Offset"));
    filename       = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Filename"));
    fileSize       = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_FileSize"));
    numColumns     = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Columns"));
    offsetRelative = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Relative"));
    offsetAbsolute = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Absolute"));
    
    
    valueByteLittle      = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Byte_Little"));
    valueShortLittle     = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Short_Little"));
    valueIntLittle       = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Int_Little"));
    valueLongLittle      = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Long_Little"));
    valueFloatLittle     = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Float_Little"));
    valueDoubleLittle    = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Double_Little"));
    valueBinaryLittle    = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Binary_Little"));
    valueHexLittle       = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Hex_Little"));
    
    valueByteBig      = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Byte_Big"));
    valueShortBig     = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Short_Big"));
    valueIntBig       = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Int_Big"));
    valueLongBig      = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Long_Big"));
    valueFloatBig     = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Float_Big"));
    valueDoubleBig    = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Double_Big"));
    valueBinaryBig    = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Binary_Big"));
    valueHexBig       = ((WSTextField)ComponentRepository.get("SidePanel_Analysis_Hex_Big"));

    notesArea      = ((WSTextArea)ComponentRepository.get("SidePanel_NotesArea"));
    }





/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSPanelPlugin clone(){
    return new SidePanel_Analysis(toXML());
    }



///////////////
//
// Class-Specific Methods
//
///////////////


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    return false;
    }


/**
**********************************************************************************************
Performs any functionality that needs to happen when the panel is to be closed. This method
does nothing by default, but can be overwritten to do anything else needed before the panel is
closed, such as garbage collecting and closing pointers to temporary objects.
**********************************************************************************************
**/
  public void onCloseRequest(){
    }


/**
**********************************************************************************************
What to do when the data has changed. Could be changed because the user has selected another
byte, or  because the file has changed.
@param cell the cell number that is now selected
**********************************************************************************************
**/
  public void onDataChange() {
    ByteDataTableModel model = (ByteDataTableModel)table.getModel();

    int cell = table.getColumnCount()*table.getSelectedRow() + table.getSelectedColumn();
    //byte[] bytes = model.getBytes(8,cell);
    byte[] bytes = model.getDisplayedBytes(8,cell);


    // filename
    String name = model.getFilename();
    if (! name.equals(filename.getText())){
      filename.setText(name);
      filename.setCaretPosition(name.length());
      }
    
    // file size
    File file = model.getFile();
    if (file == null) {
      return; // early exit - no file is loaded
    }
    
    if (file.exists()){
      String length = ""+file.length();
      fileSize.setText(length);
      fileSize.setCaretPosition(length.length());
      }
    else {
      fileSize.setText("");
    }

    // numberOfColumns
    numColumns.setText(""+model.getColumnCount());

    // offset
    offsetStart.setText(""+model.getOffset());

    // relative offset
    offsetRelative.setText(""+cell);

    // absolute offset
    offsetAbsolute.setText("" + (model.getOffset() + cell));



    // byte
    int byteValueLittle = bytes[0];
    int byteValueBig = bytes[0];
    if (byteValueLittle < 0){
      byteValueLittle = 256 + byteValueLittle;
      }
    valueByteLittle.setText(byteValueLittle + "");
    valueByteBig.setText(byteValueBig + "");

    // short
    valueShortLittle.setText(ShortConverter.convertLittle(new byte[]{bytes[0],bytes[1]}) + "");
    valueShortBig.setText(ShortConverter.convertBig(new byte[]{bytes[0],bytes[1]}) + "");

    // int
    valueIntLittle.setText(IntConverter.convertLittle(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3]}) + "");
    valueIntBig.setText(IntConverter.convertBig(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3]}) + "");

    // long
    valueLongLittle.setText(LongConverter.convertLittle(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");
    valueLongBig.setText(LongConverter.convertBig(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");

    // float
    valueFloatLittle.setText(FloatConverter.convertLittle(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3]}) + "");
    valueFloatBig.setText(FloatConverter.convertBig(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3]}) + "");

    // double
    valueDoubleLittle.setText(DoubleConverter.convertLittle(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");
    valueDoubleBig.setText(DoubleConverter.convertBig(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");

    
    // binary
    boolean[] bits = BooleanArrayConverter.convertLittle((byte)bytes[0]);
    String binary = "";

    for (int i=0;i<bits.length;i++){
      if (bits[i]){
        binary += "1";
        }
      else {
        binary += "0";
        }
      }

    valueBinaryLittle.setText(binary);
    valueBinaryBig.setText(binary);
    
    // hex
    valueHexLittle.setText(HexConverter.convertLittle(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");
    valueHexBig.setText(HexConverter.convertBig(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");

    }


/**
**********************************************************************************************
The event that is triggered from a WSDoubleClickableListener when a double click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDoubleClick(JComponent c, MouseEvent e){
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSKeyableListener when a key press occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onEnter(JComponent c, KeyEvent e){
    if (c == offsetStart){
      try {
        ((ByteDataTableModel)table.getModel()).setOffset(Long.parseLong(offsetStart.getText()));
        }
      catch (Throwable t){
        // Not a number
        }
      table.revalidate();
      table.repaint();
      }
    else if (c == numColumns){
      ByteDataTableModel model = (ByteDataTableModel)table.getModel();
      try {
        model.setColumnCount(Integer.parseInt(numColumns.getText()));
        }
      catch (Throwable t){
        // Not a number
        }
      //table.setModel(new ByteDataTableModel(model.getName()));
      table.setModel(model.clone());

      TableColumnModel columnModel = table.getColumnModel();
      for (int i=0;i<columnModel.getColumnCount();i++){
        columnModel.getColumn(i).setMinWidth(0);
        }

      table.revalidate();
      table.repaint();
      }
    else {
      return false;
      }
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSKeyableListener when a key press occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e){
    if (c == notesArea){
      ByteDataTableModel model = (ByteDataTableModel)table.getModel();
      String name = model.getName();

      TemporarySettings.set(name + "_Notes",notesArea.getText());
      return true;
      }
    return false;
    }


/**
**********************************************************************************************
Performs any functionality that needs to happen when the panel is to be opened. By default,
it just calls checkLoaded(), but can be overwritten to do anything else needed before the
panel is displayed, such as resetting or refreshing values.
**********************************************************************************************
**/
  public void onOpenRequest(){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setTable(WSTable table){
    this.table = table;

    ByteDataTableModel model = (ByteDataTableModel)table.getModel();
    String name = model.getName();

    notesArea.setText(TemporarySettings.get(name + "_Notes"));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setHolder(WSSidePanelHolder holder){
    this.holder = holder;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTable getTable(){
    return table;
    }


///////////////
//
// Default Implementations
//
///////////////


/**
**********************************************************************************************
Gets the plugin description
**********************************************************************************************
**/
  public String getDescription(){
    String description = toString() + "\n\n" + Language.get("Description_SidePanel");

    if (! isEnabled()){
      description += "\n\n" + Language.get("Description_PluginDisabled");
      }
    else {
      description += "\n\n" + Language.get("Description_PluginEnabled");
      }

    return description;
    }


/**
**********************************************************************************************
Gets the plugin name
**********************************************************************************************
**/
  public String getText(){
    return super.getText();
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when the mouse moves over an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHover(JComponent c, MouseEvent e){
    return super.onHover(c,e);
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when the mouse moves out of an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHoverOut(JComponent c, MouseEvent e){
    return super.onHoverOut(c,e);
    }


/**
**********************************************************************************************
Sets the description of the plugin
@param description the description
**********************************************************************************************
**/
  public void setDescription(String description){
    super.setDescription(description);
    }



  }
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
import org.watto.component.*;
import org.watto.manipulator.DataConverter;
import org.watto.event.*;
import org.watto.xml.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_AlternateFormats extends WSPanelPlugin implements SidePanelClone,
                                                                         WSEnterableInterface,
                                                                         WSKeyableInterface {


  WSSidePanelHolder holder;
  WSTable table;

  WSTextField offsetStart;
  WSTextField readSize;
  WSTextField filename;
  WSTextField numColumns;
  WSTextField offsetRelative;
  WSTextField offsetAbsolute;
  WSTextField valueFloat;
  WSTextField valueDouble;
  WSTextField valueHex;
  WSTextField valueChar;
  WSTextField valueUnicode;
  WSTextField valueTimestamp;

  WSTextArea notesArea;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_AlternateFormats(){
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
  public SidePanel_AlternateFormats(XMLNode node){
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
  public void buildObject(XMLNode node){
    super.buildObject(node);

    setLayout(new BorderLayout());

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_AlternateFormats.wsd"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    WSRepository.add(this);


    offsetStart    = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Offset"));
    readSize       = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_ReadSize"));
    filename       = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Filename"));
    numColumns     = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Columns"));
    offsetRelative = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Relative"));
    offsetAbsolute = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Absolute"));
    valueFloat     = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Float"));
    valueDouble    = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Double"));
    valueHex       = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Hex"));
    valueChar      = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Char"));
    valueUnicode   = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Unicode"));
    valueTimestamp = ((WSTextField)WSRepository.get("SidePanel_AlternateFormats_Timestamp"));

    notesArea      = ((WSTextArea)WSRepository.get("SidePanel_NotesArea"));
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    return super.buildXML();
    }


/**
**********************************************************************************************
Registers the events that this component generates
**********************************************************************************************
**/
  public void registerEvents(){
    super.registerEvents();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSPanelPlugin clone(){
    return new SidePanel_AlternateFormats(buildXML());
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

    boolean littleEndian = model.isLittleEndian();

    int cell = table.getColumnCount()*table.getSelectedRow() + table.getSelectedColumn();
    //byte[] bytes = model.getBytes(8,cell);
    byte[] bytes = model.getDisplayedBytes(8,cell);


    // filename
    String name = model.getFilename();
    if (! name.equals(filename.getText())){
      filename.setText(name);
      filename.setCaretPosition(name.length());
      }

    // numberOfColumns
    numColumns.setText(""+model.getColumnCount());

    // readSize
    readSize.setText(""+model.getReadLength());

    // offset
    offsetStart.setText(""+model.getOffset());

    // relative offset
    offsetRelative.setText(""+cell);

    // absolute offset
    offsetAbsolute.setText("" + (model.getOffset() + cell));

    // float
    if (littleEndian){
      valueFloat.setText(DataConverter.toFloatL(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3]}) + "");
      }
    else {
      valueFloat.setText(DataConverter.toFloatB(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3]}) + "");
      }

    // double
    if (littleEndian){
      valueDouble.setText(DataConverter.toDoubleL(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");
      }
    else {
      valueDouble.setText(DataConverter.toDoubleB(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}) + "");
      }

    // hex
    valueHex.setText(DataConverter.toHexL(bytes).toString());

    // char
    valueChar.setText(""+(char)bytes[0]);

    // unicode
    if (littleEndian){
      valueUnicode.setText(""+DataConverter.toCharL(new byte[]{bytes[0],bytes[1]}));
      }
    else {
      valueUnicode.setText(""+DataConverter.toCharB(new byte[]{bytes[0],bytes[1]}));
      }

    // timestamp
    Calendar cal = Calendar.getInstance();
    if (littleEndian){
      cal.setTimeInMillis(DataConverter.toLongL(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}));
      }
    else {
      cal.setTimeInMillis(DataConverter.toLongB(new byte[]{bytes[0],bytes[1],bytes[2],bytes[3],bytes[4],bytes[5],bytes[6],bytes[7]}));
      }
    valueTimestamp.setText(cal.getTime().toString());

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
    else if (c == readSize){
      try {
        ((ByteDataTableModel)table.getModel()).setReadLength(Integer.parseInt(readSize.getText()));
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
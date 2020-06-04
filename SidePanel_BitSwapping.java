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
import javax.swing.*;
import javax.swing.table.TableColumnModel;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_BitSwapping extends WSPanelPlugin implements SidePanelClone,
                                                                    WSEnterableInterface,
                                                                    WSKeyableInterface {


  WSSidePanelHolder holder;
  WSTable table;

  WSRadioButton swapXOR;
  WSRadioButton swapAND;
  WSRadioButton swapOR;
  WSRadioButton swapNAND;
  WSRadioButton swapNOR;
  WSRadioButton swapNOT;
  WSRadioButton swapNONE;

  WSRadioButton shiftLeft;
  WSRadioButton shiftRight;
  WSRadioButton shiftRightWrap;

  WSTextField swapValue;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public SidePanel_BitSwapping(){
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
  public SidePanel_BitSwapping(XMLNode node){
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
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_BitSwapping.wsd"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    WSRepository.add(this);


    swapValue = ((WSTextField)WSRepository.get("SidePanel_BitSwapping_SwapValue"));
    swapXOR   = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_SwapXOR"));
    swapAND   = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_SwapAND"));
    swapOR    = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_SwapOR"));
    swapNAND  = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_SwapNAND"));
    swapNOR   = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_SwapNOR"));
    swapNOT   = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_SwapNOT"));
    swapNONE  = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_SwapNONE"));

    shiftLeft      = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_ShiftLeft"));
    shiftRight     = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_ShiftRight"));
    shiftRightWrap = ((WSRadioButton)WSRepository.get("SidePanel_BitSwapping_ShiftRightWrap"));

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
    return new SidePanel_BitSwapping(buildXML());
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
    ByteDataTableModel model = (ByteDataTableModel)table.getModel();
    if (c == swapXOR){
      model.setSwapType(ByteDataTableModel.SWAP_XOR);
      }
    else if (c == swapOR){
      model.setSwapType(ByteDataTableModel.SWAP_OR);
      }
    else if (c == swapAND){
      model.setSwapType(ByteDataTableModel.SWAP_AND);
      }
    else if (c == swapNOR){
      model.setSwapType(ByteDataTableModel.SWAP_NOR);
      }
    else if (c == swapNAND){
      model.setSwapType(ByteDataTableModel.SWAP_NAND);
      }
    else if (c == swapNOT){
      model.setSwapType(ByteDataTableModel.SWAP_NOT);
      }
    else if (c == swapNONE){
      model.setSwapType(ByteDataTableModel.SWAP_NONE);
      }
    else if (c == shiftLeft){
      model.setSwapType(ByteDataTableModel.SHIFT_LEFT);
      }
    else if (c == shiftRight){
      model.setSwapType(ByteDataTableModel.SHIFT_RIGHT);
      }
    else if (c == shiftRightWrap){
      model.setSwapType(ByteDataTableModel.SHIFT_RIGHT_WRAP);
      }
    else {
      return false;
      }

    ((ByteDataTableModel)table.getModel()).setSwapValue(Integer.parseInt(swapValue.getText()));
    table.revalidate();
    table.repaint();
    return true;
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
    if (c == swapValue){
      ((ByteDataTableModel)table.getModel()).setSwapValue(Integer.parseInt(swapValue.getText()));
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

    swapValue.setText(""+model.getSwapValue());

    int swapType = model.getSwapType();

    if (swapType == ByteDataTableModel.SWAP_NONE){
      swapNONE.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SWAP_XOR){
      swapXOR.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SWAP_OR){
      swapOR.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SWAP_NOR){
      swapNOR.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SWAP_AND){
      swapAND.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SWAP_NAND){
      swapNAND.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SWAP_NOT){
      swapNOT.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SHIFT_LEFT){
      shiftLeft.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SHIFT_RIGHT){
      shiftRight.setSelected(true);
      }
    else if (swapType == ByteDataTableModel.SHIFT_RIGHT_WRAP){
      shiftRightWrap.setSelected(true);
      }

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
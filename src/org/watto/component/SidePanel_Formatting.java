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
import org.watto.event.WSEnterableInterface;
import org.watto.event.WSKeyableInterface;
import org.watto.xml.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;


/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public class SidePanel_Formatting extends WSPanelPlugin implements SidePanelClone, WSEnterableInterface,
WSKeyableInterface {


  
  private static final long serialVersionUID = 1L;
  
  WSSidePanelHolder holder;
  WSTable table;

  WSRadioButton showByte;
  WSRadioButton showHex;
  WSRadioButton showChar;
  
  WSRadioButton shadingNone;
  WSRadioButton shadingGrayscale;
  WSRadioButton shadingHalf;

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
  public SidePanel_Formatting(){
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
  public SidePanel_Formatting(XMLNode node){
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
    XMLNode srcNode = XMLReader.read(new File("interface" + File.separator + "SidePanel_Formatting.xml"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.toComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    ComponentRepository.add(this);


    showByte         = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShowByte"));
    showHex          = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShowHex"));
    showChar         = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShowChar"));

    shadingNone      = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShadingNone"));
    shadingGrayscale = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShadingGrayscale"));
    shadingHalf      = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShadingHalf"));
    
    swapValue = ((WSTextField)ComponentRepository.get("SidePanel_Formatting_SwapValue"));
    swapXOR   = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_SwapXOR"));
    swapAND   = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_SwapAND"));
    swapOR    = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_SwapOR"));
    swapNAND  = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_SwapNAND"));
    swapNOR   = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_SwapNOR"));
    swapNOT   = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_SwapNOT"));
    swapNONE  = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_SwapNONE"));

    shiftLeft      = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShiftLeft"));
    shiftRight     = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShiftRight"));
    shiftRightWrap = ((WSRadioButton)ComponentRepository.get("SidePanel_Formatting_ShiftRightWrap"));
    }





/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSPanelPlugin clone(){
    return new SidePanel_Formatting(toXML());
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
    if (c == showByte){
      model.setDisplayType(ByteData.DISPLAY_BYTE);
      }
    else if (c == showHex){
      model.setDisplayType(ByteData.DISPLAY_HEX);
      }
    else if (c == showChar){
      model.setDisplayType(ByteData.DISPLAY_CHAR);
      }
    else if (c == shadingNone){
      model.setShadingType(ByteData.SHADING_NONE);
      }
    else if (c == shadingGrayscale){
      model.setShadingType(ByteData.SHADING_GRAYSCALE);
      }
    else if (c == shadingHalf){
      model.setShadingType(ByteData.SHADING_HALF);
      }
    else if (c == swapXOR){
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

    String swapText = swapValue.getText().trim();
    if (swapText.indexOf(",") > 0 || swapText.indexOf(" ") > 0) {
      ((ByteDataTableModel)table.getModel()).setSwapArray(swapText);
    }
    else {
    try {
    ((ByteDataTableModel)table.getModel()).setSwapValue(Integer.parseInt(swapText));
    }
    catch (Throwable t) {
      // NaN or something
    }
    }
    
    
    
    

    table.revalidate();
    table.repaint();

    return true;
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

        String swapText = swapValue.getText().trim();
        if (swapText.indexOf(",") > 0 || swapText.indexOf(" ") > 0) {
          ((ByteDataTableModel)table.getModel()).setSwapArray(swapText);
        }
        else {
        try {
        ((ByteDataTableModel)table.getModel()).setSwapValue(Integer.parseInt(swapText));
        }
        catch (Throwable t) {
          // NaN or something
        }
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

    // tick the appropriate checkboxes, etc.
    ByteDataTableModel model = (ByteDataTableModel)table.getModel();

    int displayType = model.getDisplayType();
    
    if (displayType == ByteData.DISPLAY_BYTE) {
      showByte.setSelected(true);
    }
    else if (displayType == ByteData.DISPLAY_HEX) {
    showHex.setSelected(true);
    }
    else if (displayType == ByteData.DISPLAY_CHAR) {
    showChar.setSelected(true);
    }



    int shadingType = model.getShadingType();

    if (shadingType == ByteData.SHADING_NONE){
      shadingNone.setSelected(true);
      }
    else if (shadingType == ByteData.SHADING_GRAYSCALE){
      shadingGrayscale.setSelected(true);
      }
    else if (shadingType == ByteData.SHADING_HALF){
      shadingHalf.setSelected(true);
      }

    
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
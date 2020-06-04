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
public class SidePanel_Formatting extends WSPanelPlugin implements SidePanelClone {


  WSSidePanelHolder holder;
  WSTable table;

  WSCheckBox showByte;
  WSCheckBox showHex;
  WSCheckBox showChar;
  //WSCheckBox showUnicode;
  WSRadioButton littleEndian;
  WSRadioButton bigEndian;
  WSRadioButton useNegatives;
  WSRadioButton noNegatives;
  WSRadioButton shadingNone;
  WSRadioButton shadingGrayscale;
  WSRadioButton shadingHalf;
  WSRadioButton shadingBit;


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
  public void buildObject(XMLNode node){
    super.buildObject(node);

    setLayout(new BorderLayout());

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File("settings" + File.separator + "interface_SidePanel_Formatting.wsd"));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository (overwrite the base panel with this object)
    setCode(((WSComponent)component).getCode());
    WSRepository.add(this);


    showByte         = ((WSCheckBox)WSRepository.get("SidePanel_Formatting_ShowByte"));
    showHex          = ((WSCheckBox)WSRepository.get("SidePanel_Formatting_ShowHex"));
    showChar         = ((WSCheckBox)WSRepository.get("SidePanel_Formatting_ShowChar"));
    //showUnicode      = ((WSCheckBox)WSRepository.get("SidePanel_Formatting_ShowUnicode"));
    littleEndian     = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_LittleEndian"));
    bigEndian        = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_BigEndian"));
    useNegatives     = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_UseNegatives"));
    noNegatives      = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_NoNegatives"));
    shadingNone      = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_ShadingNone"));
    shadingGrayscale = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_ShadingGrayscale"));
    shadingHalf      = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_ShadingHalf"));
    shadingBit       = ((WSRadioButton)WSRepository.get("SidePanel_Formatting_ShadingBit"));
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
    return new SidePanel_Formatting(buildXML());
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
      model.setShowByte(showByte.isSelected());
      }
    else if (c == showHex){
      model.setShowHex(showHex.isSelected());
      }
    else if (c == showChar){
      model.setShowChar(showChar.isSelected());
      }
    //else if (c == showUnicode){
    //  model.setShowUnicode(showUnicode.isSelected());
    //  }
    else if (c == littleEndian){
      model.setLittleEndian();
      }
    else if (c == bigEndian){
      model.setBigEndian();
      }
    else if (c == useNegatives){
      model.setUseNegatives(useNegatives.isSelected());
      }
    else if (c == noNegatives){
      model.setUseNegatives(!noNegatives.isSelected());
      }
    else if (c == shadingNone){
      model.setShadingType(ByteDataTableModel.SHADING_NONE);
      }
    else if (c == shadingGrayscale){
      model.setShadingType(ByteDataTableModel.SHADING_GRAYSCALE);
      }
    else if (c == shadingHalf){
      model.setShadingType(ByteDataTableModel.SHADING_HALF);
      }
    else if (c == shadingBit){
      model.setShadingType(ByteDataTableModel.SHADING_BIT);
      }
    else {
      return false;
      }

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

    showByte.setSelected(model.isShowingByte());
    showHex.setSelected(model.isShowingHex());
    showChar.setSelected(model.isShowingChar());
    //showUnicode.setSelected(model.isShowingUnicode());

    if (model.isLittleEndian()){
      littleEndian.setSelected(true);
      }
    else {
      bigEndian.setSelected(true);
      }

    if (model.getUseNegatives()){
      useNegatives.setSelected(true);
      }
    else {
      noNegatives.setSelected(true);
      }


    int shadingType = model.getShadingType();

    if (shadingType == ByteDataTableModel.SHADING_NONE){
      shadingNone.setSelected(true);
      }
    else if (shadingType == ByteDataTableModel.SHADING_GRAYSCALE){
      shadingGrayscale.setSelected(true);
      }
    else if (shadingType == ByteDataTableModel.SHADING_HALF){
      shadingHalf.setSelected(true);
      }
    else if (shadingType == ByteDataTableModel.SHADING_BIT){
      shadingBit.setSelected(true);
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
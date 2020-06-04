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
import org.watto.xml.*;

import java.awt.BorderLayout;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSSidePanelHolder extends WSPanel {

  WSPanel currentPanel = new WSPanel(XMLReader.readString("<WSPanel />"));

/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSSidePanelHolder(XMLNode node){
    super(node);
    }


///////////////
//
// Configurable
//
///////////////


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    return WSHelper.buildXML(this);
    }



///////////////
//
// Class-Specific Methods
//
///////////////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSPanel getCurrentPanel(){
    return currentPanel;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getCurrentPanelCode(){
    return currentPanel.getCode();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void onCloseRequest(){
    if (currentPanel != null && currentPanel instanceof WSPanelPlugin){
      ((WSPanelPlugin)currentPanel).onCloseRequest();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void onOpenRequest(){
    if (currentPanel != null && currentPanel instanceof WSPanelPlugin){
      ((WSPanelPlugin)currentPanel).onOpenRequest();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  private void loadPanel(String code){
    if (currentPanel instanceof SidePanelClone){
      WSTable table = ((SidePanelClone)currentPanel).getTable();
      loadPanel(code,true,table);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadPanel(String code, WSTable table){
    loadPanel(code,true,table);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  private void loadPanel(String code, boolean changeSetting, WSTable table){
    WSPanelPlugin newPanel = (WSPanelPlugin)WSPluginManager.group("SidePanel").getPlugin(code);
    if (newPanel instanceof SidePanelClone){
      newPanel = ((SidePanelClone)newPanel).clone();
      ((SidePanelClone)newPanel).setTable(table);
      ((SidePanelClone)newPanel).setHolder(this);
      }
    currentPanel = newPanel;


//    System.out.println("F-->" + currentPanel.getCode());
    //System.out.println("Found " + code + " as " + currentPanel);
    if (currentPanel == null){
      currentPanel = new WSPanel(XMLReader.readString("<WSPanel />"));
      }
    else {
      if (changeSetting){
        Settings.set(((ByteDataTableModel)table.getModel()).getName() + "_CurrentSidePanel",code);
        }
      }

    removeAll();
    add(currentPanel,BorderLayout.CENTER);
    //DirectoryListHolder holder = DirectoryListHolder.getInstance();
    //holder.loadPanel("List");
    //add(holder,BorderLayout.CENTER);

    reload();
//    System.out.println("E-->" + code);
//    try {
//      throw new Exception();
//      }
//    catch (Throwable t){
//      t.printStackTrace();
//      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void onDataChange() {
    if (currentPanel instanceof SidePanelClone){
      ((SidePanelClone)currentPanel).onDataChange();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload(){
    revalidate();
    repaint();
    currentPanel.requestFocus();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadPanel(){
    try {
      loadPanel(currentPanel.getCode());
      }
    catch (Throwable t){
      }
    }


/**
**********************************************************************************************
Rebuilds the panels from their XML
**********************************************************************************************
**/
  public void rebuild(){
    WSPlugin[] plugins = WSPluginManager.group("SidePanel").getPlugins();
    for (int i=0;i<plugins.length;i++){
      ((WSPanelPlugin)plugins[i]).buildObject(new XMLNode());
      }

    //reload();
    loadPanel(currentPanel.getCode());
    }


  }
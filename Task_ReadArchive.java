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
import org.watto.component.WSPopup;
import org.watto.component.WSTable;
import org.watto.component.WSRepository;
import org.watto.component.WSProgressDialog;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Task_ReadArchive implements UndoTask {

  /** The direction to perform in the thread **/
  int direction = 1;

  File path;
  WSTable table;
  WSSidePanelHolder holder;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_ReadArchive(File path, WSTable table, WSSidePanelHolder holder){
    this.path = path;
    this.table = table;
    this.holder = holder;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void redo(){
    if (path == null || !path.exists()){
      WSPopup.showError("ReadArchive_FilenameMissing",true);
      return;
      }

    if (path.isDirectory()){
      WSPopup.showError("ReadArchive_FileNotAnArchive",true);
      return;
      }


    //String oldCurrentArchive = Settings.getString("CurrentArchive");
    Settings.set("CurrentArchive",path.getAbsolutePath());

    ((ByteDataTableModel)table.getModel()).loadFile(path);
    table.revalidate();
    table.repaint();

    table.changeSelection(0,0,false,false);
    holder.onDataChange();

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void run(){
    if (direction == -1){
      undo();
      }
    else if (direction == 1){
      redo();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setDirection(int direction){
    this.direction = direction;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String toString(){
    Class cl = getClass();
    String name = cl.getName();
    Package pack = cl.getPackage();

    if (pack != null){
      name = name.substring(pack.getName().length()+1);
      }

    return Language.get(name);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void undo(){
    }


  }


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

package org.watto.task;

import org.watto.*;
import org.watto.component.ByteDataTableModel;
import org.watto.component.WSPopup;
import org.watto.component.WSSidePanelHolder;
import org.watto.component.WSTable;
import org.watto.task.AbstractTask;
import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Task_ReadArchive extends AbstractTask {


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
    if (!TaskProgressManager.canDoTask()) {
      return;
    }
    
    if (path == null || !path.exists()) {
      WSPopup.showError("ReadArchive_FilenameMissing", true);
      TaskProgressManager.stopTask();
      return;
    }

    if (path.isDirectory()) {
      WSPopup.showError("ReadArchive_FileNotAnArchive", true);
      TaskProgressManager.stopTask();
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
  @Override
  @SuppressWarnings("rawtypes")
  public String toString() {
    Class cl = getClass();
    String name = cl.getName();
    Package pack = cl.getPackage();

    if (pack != null) {
      name = name.substring(pack.getName().length() + 1);
    }

    return Language.get(name);
  }

  /**
  **********************************************************************************************
  
  **********************************************************************************************
  **/
  @Override
  public void undo() {
    if (!TaskProgressManager.canDoTask()) {
      return;
    }
  }


  }


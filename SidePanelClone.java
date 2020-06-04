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

import org.watto.component.*;

import java.awt.BorderLayout;


/**
**********************************************************************************************

**********************************************************************************************
**/
public interface SidePanelClone {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSPanelPlugin clone();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTable getTable();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void onDataChange();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setTable(WSTable table);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setHolder(WSSidePanelHolder holder);


  }
////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                       WATTO STUDIOS                                        //
//                             Java Code, Programs, and Software                              //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2004-2022  WATTO Studios                           //
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

package org.watto.event;

import javax.swing.event.ChangeEvent;

/***********************************************************************************************
A class that reacts to cell editing events (eg in a table or tree)
@see org.watto.event.listener.WSCellEditableListener
***********************************************************************************************/
public interface WSCellEditableInterface {

  
  /***********************************************************************************************

  ***********************************************************************************************/
  public void editingStopped(ChangeEvent e);

  /***********************************************************************************************

  ***********************************************************************************************/
  public void editingCanceled(ChangeEvent e);
}
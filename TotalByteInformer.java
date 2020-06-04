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

/***************************************** TODO LIST *****************************************\

////////////////////////////////////////////////////////////////////////////////////////////////
//                                  VERSION RELEASE FEATURES                                  //
////////////////////////////////////////////////////////////////////////////////////////////////

// TESTING

// BUGS

// REQUIRED
- Icons *.ico
- AutoRunner *.exe (including putting the *.ico icon in here)
- Upload to www.watto.org

// PREFERRED

// OPTIONAL

// OTHER

// THINGS TO REMEMBER
- All SidePanels need to implement SidePanelClone() so they can be used on both sides!!!
- Source code headers should say TotalByteInformer
- In the language, change all... (including upper/lower case!)
  - "Game Translator" to "Total Byte Informer"


////////////////////////////////////////////////////////////////////////////////////////////////
//                              RELEASE AND PACKAGING INFORMATION                             //
////////////////////////////////////////////////////////////////////////////////////////////////

- Make sure to add any new files into the install maker script! (NOT NEW CLASSES! Just others)
  - Help / help images
  - Languages
- Remember to package up any changed language files too!

*** *** ***
- Include all classpath items in the release zip (org* for all, com* and net* for Full Version only)
*** *** ***

\*********************************************************************************************/

import org.watto.*;
import org.watto.component.*;
import org.watto.event.*;
import org.watto.manipulator.FileExtensionFilter;
import org.watto.xml.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.*;
import java.awt.event.KeyEvent;

/**
**********************************************************************************************
The Game Extractor program. This class contains the main interface, loads major components
such as the <code>PluginManager</code>s, and handles toolbar/menubar events.
**********************************************************************************************
**/
public class TotalByteInformer extends WSProgram implements WSClickableInterface,
                                                            WSResizableInterface,
                                                            WSWindowFocusableInterface,
                                                            WSKeyableInterface {


  /** A singleton holder for the TotalByteInformer program, so other classes can directly access
      the same instance **/
  static TotalByteInformer instance = new TotalByteInformer();

  WSSidePanelHolder panelHolderLeft;
  WSSidePanelHolder panelHolderRight;
  WSTable tableLeft;
  WSTable tableRight;


/**
**********************************************************************************************
Not to be used - use "TotalByteInformer.getInstance()" instead of "new TotalByteInformer()"
**********************************************************************************************
**/
  public TotalByteInformer(){
    // DONT PUT THIS LINE HERE, CAUSE IT IS DONE AUTOMATICALLY BY super()
    // EVEN THOUGH super() ISNT CALLED, IT IS RUN BECAUSE THIS CONSTRUCTOR EXTENDS WSProgram
    // AND THUS MUST RUN super() BEFORE THIS CLASS CAN BE BUILT.
    // HAVING THIS LINE CAUSES THE PROCESSES TO BE RUN TWICE, ENDING UP WITH 2 OF
    // EACH PLUGIN, AND STUPID THINGS LIKE THAT.
    //buildProgram(this);


    // add the window focus listener, so it wil reload the dirpanel when focus has regained
    addWindowFocusListener(new WSWindowFocusableListener(this));

    setIconImage(new ImageIcon(getClass().getResource("images/WSFrame/icon.gif")).getImage());

    ((WSStatusBar)WSRepository.get("StatusBar")).setText(Language.get("Welcome"));

    setTitle(Language.get("ProgramName") + " " + Settings.get("Version"));

    // close the splash screen
    splash.dispose();


    pack();
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    panelHolderLeft.setMinimumSize(new Dimension(0,0));
    panelHolderRight.setMinimumSize(new Dimension(0,0));

    WSSplitPane mainSplit = (WSSplitPane)WSRepository.get("MainSplit");
    mainSplit.setDividerSize(5);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    setVisible(true);

    }


/**
**********************************************************************************************
Returns the singleton instance of the program. This allows other classes to all address the
same instance of the interface, rather than separate instances.
@return the singleton <i>instance</i> of TotalByteInformer
**********************************************************************************************
**/
  public static TotalByteInformer getInstance(){
    return instance;
    }


/**
**********************************************************************************************
Builds the interface of the program. Can be overwritten if you want to do additional things
when the interface is being constructed, or if you dont want to load the interface from an
XML file.
**********************************************************************************************
**/
  public void constructInterface(){
    super.constructInterface();


    tableLeft = (WSTable)WSRepository.get("DataTableLeft");
    tableLeft.setModel(new ByteDataTableModel("Left"));
    tableLeft.setDefaultRenderer(ByteData.class,new ByteDataTableCellRenderer());
    tableLeft.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tableLeft.setRowSelectionAllowed(true);
    tableLeft.setColumnSelectionAllowed(true);

    TableColumnModel columnModel = tableLeft.getColumnModel();
    for (int i=0;i<columnModel.getColumnCount();i++){
      columnModel.getColumn(i).setMinWidth(0);
      }


    tableRight = (WSTable)WSRepository.get("DataTableRight");
    tableRight.setModel(new ByteDataTableModel("Right"));
    tableRight.setDefaultRenderer(ByteData.class,new ByteDataTableCellRenderer());
    tableRight.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tableRight.setRowSelectionAllowed(true);
    tableRight.setColumnSelectionAllowed(true);

    columnModel = tableRight.getColumnModel();
    for (int i=0;i<columnModel.getColumnCount();i++){
      columnModel.getColumn(i).setMinWidth(0);
      }


    panelHolderLeft = (WSSidePanelHolder)WSRepository.get("PanelHolderLeft");
    panelHolderLeft.loadPanel(Settings.get("Left_CurrentSidePanel"),tableLeft);

    panelHolderRight = (WSSidePanelHolder)WSRepository.get("PanelHolderRight");
    panelHolderRight.loadPanel(Settings.get("Right_CurrentSidePanel"),tableRight);


    }


/**
**********************************************************************************************
Deletes all the temporary files from the <i>directory</i>.
@param directory the directory that contains the temporary files.
**********************************************************************************************
**/
  public void deleteTempFiles(File directory) {
    try {

      File[] tempFiles = directory.listFiles();

      if (tempFiles == null){
        return;
        }

      for (int i=0;i<tempFiles.length;i++){
        if (tempFiles[i].isDirectory()){
          deleteTempFiles(tempFiles[i]);
          }
        tempFiles[i].delete();
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean isFullVersion(){
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSButtonableListener when a button click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (! (c instanceof WSComponent)){
      return false;
      }

    String code = ((WSComponent)c).getCode();

    System.out.println("TBI: " + code);

    if (code.equals("DataTableLeft")){
      reloadPanelHolder(panelHolderLeft);
      }
    else if (code.equals("DataTableRight")){
      reloadPanelHolder(panelHolderRight);
      }
    else if (c instanceof WSMenuItem || c instanceof WSButton){
      if (code.equals("ReadArchiveLeft")){
        readArchive(tableLeft,panelHolderLeft);
        }
      else if (code.equals("MainFormatsLeft")){
        setSidePanel("MainFormats",panelHolderLeft,tableLeft);
        }
      else if (code.equals("AlternateFormatsLeft")){
        setSidePanel("AlternateFormats",panelHolderLeft,tableLeft);
        }
      else if (code.equals("FormattingLeft")){
        setSidePanel("Formatting",panelHolderLeft,tableLeft);
        }
      else if (code.equals("ShadingLeft")){
        setSidePanel("Shading",panelHolderLeft,tableLeft);
        }
      else if (code.equals("BitSwappingLeft")){
        setSidePanel("BitSwapping",panelHolderLeft,tableLeft);
        }
      else if (code.equals("ReadArchiveRight")){
        readArchive(tableRight,panelHolderRight);
        }
      else if (code.equals("MainFormatsRight")){
        setSidePanel("MainFormats",panelHolderRight,tableRight);
        }
      else if (code.equals("AlternateFormatsRight")){
        setSidePanel("AlternateFormats",panelHolderRight,tableRight);
        }
      else if (code.equals("FormattingRight")){
        setSidePanel("Formatting",panelHolderRight,tableRight);
        }
      else if (code.equals("ShadingRight")){
        setSidePanel("Shading",panelHolderRight,tableRight);
        }
      else if (code.equals("BitSwappingRight")){
        setSidePanel("BitSwapping",panelHolderRight,tableRight);
        }
      else if (code.equals("CloseProgram")){
        onClose();
        }
      else {
        return false;
        }
      return true;
      }

    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSClosableListener when a component is closed
**********************************************************************************************
**/
  public boolean onClose(){

    deleteTempFiles(new File("temp"));


    /*
    // Remember the location of the main split divider
    WSSplitPane mainSplit = (WSSplitPane)WSRepository.get("MainSplit");
    double splitLocationOld = Settings.getDouble("DividerLocation");
    double splitLocationNew = (double)(mainSplit.getDividerLocation()) / (double)(mainSplit.getWidth());
    double diff = splitLocationOld - splitLocationNew;
    if (diff > 0.01 || diff < -0.01){
      // only set if the change is large.
      // this gets around the problem with the split slowly moving left over each load
      Settings.set("DividerLocation",splitLocationNew);
      }
    */

    // want to always reset the divider to the middle again, for the next load of the program
    Settings.set("DividerLocation",0.5);

    // Save settings files
    //XMLWriter.write(new File(Settings.getString("ToolbarFile")),toolbar.constructXMLNode());
    //XMLWriter.write(new File(Settings.getString("MenuBarFile")),menubar.constructXMLNode());
    Settings.saveSettings();

    //System.out.println(((WSComponent)getContentPane().getComponent(0)).getCode());
    //XMLWriter.write(new File("temp/test.wsd"),WSRepository.get("MainWindowFrame").buildXML());

    // Saves the interface to XML, in case there were changes made by the program, such as
    // the adding/removal of buttons, or repositioning of elements
    saveInterface();


    ErrorLogger.closeLog();

    System.exit(0);

    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when a component is hovered over
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHover(JComponent c, java.awt.event.MouseEvent e){
    //statusbar.setText(((JComponent)c).getToolTipText());
    ((WSStatusBar)WSRepository.get("StatusBar")).setText(((JComponent)c).getToolTipText());
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when a component is no longer hovered
over (ie loses its hover)
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHoverOut(JComponent c, java.awt.event.MouseEvent e){
    //statusbar.revertText();
    ((WSStatusBar)WSRepository.get("StatusBar")).revertText();
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e){
    if (c instanceof WSTable){
      int keyCode = e.getKeyCode();

      if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT){
        String code = ((WSComponent)c).getCode();

        if (code.equals("DataTableLeft")){
          reloadPanelHolder(panelHolderLeft);
          }
        else if (code.equals("DataTableRight")){
          reloadPanelHolder(panelHolderRight);
          }
        else {
          return false;
          }
        return true;
        }
      }
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSResizableListener when a component is resized
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onResize(JComponent c, java.awt.event.ComponentEvent e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();
      if (code.equals("MainSplit")){
        // reposition the splitpane divider when the splitpane changes sizes
        double splitPos = Settings.getDouble("DividerLocation");
        if (splitPos < 0 || splitPos > 1){
          splitPos = 0.7;
          }

        //System.out.println("Before: " + splitPos);
        ((WSSplitPane)c).setDividerLocation(splitPos);
        //System.out.println("After: " + ((double)((WSSplitPane)c).getDividerLocation() / ((WSSplitPane)c).getWidth()));
        }
      }
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSWindowFocusableListener when a component gains focus
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onWindowFocus(java.awt.event.WindowEvent e){
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean promptToSave(){
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void readArchive(WSTable table, WSSidePanelHolder holder){
    JFileChooser fc = new JFileChooser(Settings.get("CurrentDirectory"));
    if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION){
      return;
      }

    File file = fc.getSelectedFile();
    if (file == null){
      return;
      }
    Settings.set("CurrentDirectory",file.getParent());

    Task_ReadArchive task = new Task_ReadArchive(file,table,holder);
    task.setDirection(UndoTask.DIRECTION_REDO);
    new Thread(task).start();
    }


/**
**********************************************************************************************
  Does a soft reload, after options changes
**********************************************************************************************
**/
  public void reload(){
    //menubar.reload();
    //toolbar.reload();

    //FileListPanelManager.reloadCurrentPanel();

    //SidePanelManager.reloadPanels();
    //panelHolderLeft.revalidate();
    //panelHolderLeft.repaint();
    //panelHolderRight.revalidate();
    //panelHolderRight.repaint();
    panelHolderLeft.reload();
    panelHolderRight.reload();

    //repositionToolbar();
    //repositionSidePanel();

    ((WSStatusBar)WSRepository.get("StatusBar")).setText(Language.get("Welcome"));

    //SwingUtilities.updateComponentTreeUI(this);
    validate();
    repaint();

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadPanelHolder(WSSidePanelHolder panelHolder){
    // reload - the selection was changed
    //panelHolder.loadPanel("SidePanel_" + name)
    //panelHolder.loadPanel("SidePanel_MainFormats");
    panelHolder.onDataChange();
    }


/**
**********************************************************************************************
  Does a hard reload (rebuilds the entire interface after language/font/interface change)
**********************************************************************************************
**/
  public void rebuild(){

    WSPlugin[] plugins = WSPluginManager.group("Options").getPlugins();
    for (int i=0;i<plugins.length;i++){
      ((WSPanelPlugin)plugins[i]).buildObject(new XMLNode());
      }

    constructInterface();

    //panelHolderLeft.revalidate();
    //panelHolderLeft.repaint();
    //panelHolderRight.revalidate();
    //panelHolderRight.repaint();
    panelHolderLeft.rebuild();
    panelHolderRight.rebuild();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSidePanel(String name, WSSidePanelHolder holder, WSTable table){
    holder.loadPanel("SidePanel_" + name,table);
    holder.onDataChange();
    }



/**
**********************************************************************************************
The main method that starts the program.
@param args the arguments passed in from the commandline.
**********************************************************************************************
**/
  public static void main(String[] args){
    TotalByteInformer tbi = TotalByteInformer.getInstance();
    }

  }

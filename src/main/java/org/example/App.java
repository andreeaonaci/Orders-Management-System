package org.example;

import Presentation.ControllerMain;
import Presentation.ViewMenu;

/** @Author Andreea Onaci
 *  Start the application from here!
 */
public class App
{
    public static void main( String[] args )
    {
        ViewMenu viewMenu = new ViewMenu();
        new ControllerMain(viewMenu);
        viewMenu.setVisible(true);
    }
}

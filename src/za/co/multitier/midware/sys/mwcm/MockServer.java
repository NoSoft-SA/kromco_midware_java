/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.mwcm;


import za.co.multitier.midware.sys.appservices.DeviceCommands;
import za.co.multitier.midware.sys.appservices.DeviceCommands.ROBOT_KEYPAD;
import za.co.multitier.midware.sys.mwpt.PalletizingAction;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author hans
 */
public class MockServer {

    private static DeviceCommands.TRM_KEYPAD getButton(int button) {

        switch (button) {
            case 1:
                return DeviceCommands.TRM_KEYPAD.F3;

            case 2:
                return DeviceCommands.TRM_KEYPAD.F4;


            default:
                return DeviceCommands.TRM_KEYPAD.none;

        }
    }

    public static void main(String[] args) {

        String bintip_ip = "172.16.35.4";
        String lotNumber = null;//BinTippingScan.IS_NON_PRESORT;
        String bin_scanned = "";


        Scanner in = new Scanner(System.in);

        boolean stop_server = false;

        while (!stop_server) {
            long button = 0;


            String result = "";

            System.out.println("-------------------------------------------");
            System.out.println("Please enter button number or bin_number");
            System.out.println("-------------------------------------------");

            long input = in.nextLong();

            if (input == -1) {
                stop_server = true;
            } else if (input > 1000) {
                bin_scanned = String.valueOf(input);

            }
            else if (input > 5 && input < 1000)
              lotNumber = String.valueOf(input);
            else {
                button = input;
            }

           try
           {
            result = BinTippingScan.scanBin(bintip_ip,bin_scanned,getButton((int) button),lotNumber );


           }
           catch(Exception e)
           {
                 System.out.println("BINTIP EXCEPTION: " );
                 System.out.println(e.getMessage());
                  e.printStackTrace();
           }

            finally
           {
               try {
                   za.co.multitier.midware.sys.datasource.DataSource.getSqlMapInstance().endTransaction();

               } catch (SQLException e) {
                    System.out.println("TRANSACTION COULD NOT BE ENDED: ");
                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
               }
           }

            System.out.println("RESULT: ");
            System.out.println(result);


        }


    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.multitier.midware.sys.mwpt;


import java.sql.SQLException;
import java.util.Scanner;

import za.co.multitier.midware.sys.appservices.DeviceCommands.ROBOT_KEYPAD;
import za.co.multitier.midware.sys.datasource.DataSource;
import za.co.multitier.midware.sys.datasource.ProductLabelingDAO;
import za.co.multitier.midware.sys.datasource.Rebin;

/**
 * @author hans
 */
public class MockServer {

    private static ROBOT_KEYPAD getButton(int bayButton) {

        switch (bayButton) {
            case 1:
                return ROBOT_KEYPAD.btn1;

            case 2:
                return ROBOT_KEYPAD.btn2;

            case 3:
                return ROBOT_KEYPAD.btn3;

            case 4:
                return ROBOT_KEYPAD.btn4;

            case 5:
                return ROBOT_KEYPAD.btn5;
            default:
                return ROBOT_KEYPAD.none;

        }
    }

    public static void main(String[] args) {

        String skip_ip = "172.16.35.112";
        String bay_num = "5";


//        try{
//          Rebin rebin = ProductLabelingDAO.getRebin("3BFF34","123454");
//           String dt = DataSource.toFriendlyDate(rebin.getCreated_on());
//           System.out.println(dt);
//            return;
//
//
//        }
//        catch(Exception e)
//            {
//
//            }




        Scanner in = new Scanner(System.in);

        boolean stop_server = false;

        while (!stop_server) {
            long button = 0;
            String carton_scanned = "";
            PalletizingAction action = null;
            String result = "";

            System.out.println("-------------------------------------------");
            System.out.println("Please enter button number or carton_number");
            System.out.println("-------------------------------------------");

            long input = in.nextLong();

            if (input == -1) {
                stop_server = true;
            } else if (input > 100) {
                carton_scanned = String.valueOf(input);
            } else {
                button = input;
            }

           try
           {
            result = new PalletizingAction().process_action(skip_ip, bay_num, getButton((int) button), carton_scanned);
           }
           catch(Exception e)
           {
                 System.out.println("PT EXCEPTION: " );
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

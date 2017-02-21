package za.co.multitier.midware.sys;

/**
 * Created with IntelliJ IDEA.
 * User: hans
 * Date: 2014/11/18
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class test {

          public static void main(String[] args)
          {

              String val = "FE,EU,FdE";
              String[] tms = val.split(",");

              System.out.println(tm_in_list("FdEg",tms));


          }


    private static boolean tm_in_list(String tm,String[] tm_list)
    {
        for(int i=0; i< tm_list.length; i++){
            if(tm_list[i].equals(tm))
                return true;
        }

        return false;
    }


          public static boolean isNumeric(String num)
          {
              try
              {
                  Integer.valueOf(num);
              }
              catch (NumberFormatException e)
              {
                     return false;
              }

              return true;

          }


}

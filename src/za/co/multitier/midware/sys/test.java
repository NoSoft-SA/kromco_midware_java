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
              String pack_point_end_chars = "44DP44".substring("44DP44".length() - 2);
              System.out.println(pack_point_end_chars);

              if(test.isNumeric(pack_point_end_chars))
              {
                 int i = Integer.valueOf(pack_point_end_chars);
              }


              String tm = "NI_NOT INSPECT";
              String s = tm.substring(0,2);
              System.out.println(s);

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

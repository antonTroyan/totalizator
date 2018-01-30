package by.troyan.web.support;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * MessageLocalizer class. Used to provide to users information about exceptions
 * in appropriate language.
 */

public class MessageLocalizer {

    /**Used to work concat strings to work with bundle. First part.*/
    private final static String LOCALE_BUNDLE_NAME = "locale_";

    /**
     * Using special algorithm calculate special code
     *
     * @param  msgName, req
     *        1 msgName, key message for bundle
     *        2 req, request to get attribute
     * @return string of localized message
     */
    public static String getLocalizedForCurrentLocaleMessage(String msgName, HttpServletRequest req){
        String pureMessage = getPureMessageFromException(msgName);
        ResourceBundle bundle = ResourceBundle.getBundle(
                LOCALE_BUNDLE_NAME.concat((String)req.getSession().getAttribute("locale")));

        return bundle.getString(pureMessage);
    }

    /**
     * Filter message from excess information. And create String with only the
     * message.
     * @param  exceptionMessage
     *
     * @return string without excess information
     */
    private static String getPureMessageFromException (String exceptionMessage){
        String result = exceptionMessage;

        Scanner scanner = new Scanner(exceptionMessage);
        if(scanner.findInLine(":") != null){
            String[] splittedArray = exceptionMessage.split(":");
            result = splittedArray[1].trim();

        }
        return result;
    }

    /**
     * Overloaded method. Can take in param List of Strings
     *
     * @param  msgNameList, req
     *        1 msgName, key message for bundle
     *        2 req, request to get attribute
     * @return string of localized message
     */
    public static String getLocalizedForCurrentLocaleMessage(List<String> msgNameList, HttpServletRequest req){
        ResourceBundle bundle = ResourceBundle.getBundle(
                LOCALE_BUNDLE_NAME.concat((String)req.getSession().getAttribute("locale")));
        StringBuilder stringBuilder = new StringBuilder();
        for(String msgName : msgNameList){
            stringBuilder.append(bundle.getString(msgName));
            stringBuilder.append("<br>");
        }
        return stringBuilder.toString();
    }
}

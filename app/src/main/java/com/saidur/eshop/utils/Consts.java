package com.saidur.eshop.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Consts {
    public static boolean Is_what =false;
    public static boolean IS_LOGIN_SHOW = true;
    public static boolean IS_ADD_TO_CART_ACTIVE = true;
    public static String APP_COLOR = "primary_color";
    public static String SECOND_COLOR = "secondary_color";
    public static String HEADER_COLOR = "header_color";
    public static String HEAD_COLOR = "#f3f8fc";
    public static final String PRIMARY_COLOR = "#f3f8fc";
    public static String ADDRESS_LINE1 = "address_line1";
    public static String ADDRESS_LINE2 = "address_line2";
    public static String PHONE = "phone_number";
    public static String WHATSAPP = "whatsapp_number";
    public static String WHATSAPPENABLE = "whatsappFloatingButton";
    public static boolean IS_CATALOG_MODE_OPTION = false;
    public static final String MyPREFERENCES = "com.saidur.eshop"; // Add your package name
    public static boolean IS_WPML_ACTIVE = false;

    public static String THOUSANDSSEPRETER = ",";
    public static String DECIMALSEPRETER = ".";
    public static String CURRENCYSYMBOL = "৳";
    public static int Decimal = 2;
    //Colors
    public static String SECONDARY_COLOR = "#1d345f";

    public static String setDecimalTwo(Double digit) {
        return new DecimalFormat("##.##").format(digit);
    }

    public static String setDecimalOne(Double digit) {
        return new DecimalFormat("##.#").format(digit);
    }

    public static String setDecimal(Double digit) {

        StringBuilder decimal = new StringBuilder("#,##0.");

        if (Consts.Decimal == 0) {
            decimal = new StringBuilder("#,##0");
        }
        if ((digit == Math.floor(digit)) && !Double.isInfinite(digit)) {
            // integer type
            for (int i = 0; i < Consts.Decimal; i++) {
                decimal.append("0");
            }
        } else {
            for (int i = 0; i < Consts.Decimal; i++) {
                decimal.append("#");
            }
        }

        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.US);
        if (Consts.Decimal != 0 && !Consts.THOUSANDSSEPRETER.equals("")) {
            unusualSymbols.setDecimalSeparator(Consts.DECIMALSEPRETER.charAt(0));
        }

        if (!Consts.THOUSANDSSEPRETER.equals("")) {
            unusualSymbols.setGroupingSeparator(Consts.THOUSANDSSEPRETER.charAt(0));
        }

//        String strange = "#,##0.000";
        DecimalFormat weirdFormatter = new DecimalFormat(decimal.toString(), unusualSymbols);
        weirdFormatter.setGroupingSize(3);
        String data = weirdFormatter.format(digit);
        Log.e("data is ", data + "");
        return data + "";
    }
}

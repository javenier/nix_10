package ua.com.alevel.util;

import java.math.BigDecimal;

public final class MoneyConverterUtil {

    private MoneyConverterUtil() { }

    public static long stringToPenny(String src) {
        BigDecimal amount = new BigDecimal(src).setScale(2);
        return amount.multiply(new BigDecimal("100")).longValue();
    }

    public static String pennyToString(Long penny) {
        double amount = penny / 100.0;
        BigDecimal result = new BigDecimal(String.valueOf(amount)).setScale(2);
        return result.toPlainString();
    }
}

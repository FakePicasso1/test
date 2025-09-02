/**
 * Utility class for validating date and datetime strings used in the
 * Tachi Flight Booking System.
 * <p>
 * Supported formats:
 * <ul>
 *   <li>Date: {@code YYYY-MM-DD}</li>
 *   <li>DateTime: {@code YYYY-MM-DDTHH:MM:SS}</li>
 * </ul>
 * <p>
 * Validation rules:
 * <ul>
 *   <li>Dates must follow the Gregorian calendar.</li>
 *   <li>Leap years are explicitly handled.</li>
 *   <li>Values must not represent a date earlier than today's system date.</li>
 *   <li>A testing hook allows overriding today's date for unit testing.</li>
 * </ul>
 *
 * @see java.time.LocalDate
 * @see java.time.LocalDateTime
 */
public class DateTimeChecker {

    // --- Testing hook for deterministic "today" in unit tests ---
    private static String todayOverride = null;

    /**
     * Sets a fixed "today" value for testing purposes.
     *
     * @param ymd a date string in {@code YYYY-MM-DD} format
     */
    public static void setTodayForTesting(String ymd) { todayOverride = ymd; }

    /**
     * Clears the testing override so the system date is used again.
     */
    public static void clearTodayForTesting() { todayOverride = null; }

    /**
     * Returns the current system date as a string in {@code YYYY-MM-DD} format.
     * <p>
     * If a test override has been set, that value is returned instead of
     * the real system date.
     *
     * @return the current date string in {@code YYYY-MM-DD} format
     */
    private static String currentTodayYmd() {
        if (todayOverride != null) return todayOverride;
        java.time.LocalDate now = java.time.LocalDate.now(java.time.Clock.systemUTC());
        return String.format("%04d-%02d-%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    /**
    * Validates whether a given date string is in the correct format (YYYY-MM-DD)
    * and represents a valid Gregorian calendar date that is not earlier than today. 
    * <p>
    * The validation steps are:
    * <ul>
    *   <li>Check syntax (length = 10, correct dash positions, numeric parts).</li>
    *   <li>Parse year, month, and day as integers.</li>
    *   <li>Verify valid Gregorian date (month/day ranges, leap years).</li>
    *   <li>Ensure the date is not earlier than today's date.</li>
    * </ul>
    *
    * @param  dateString the date string to validate, e.g. {@code "2025-09-01"}
    * @return {@code true} if the string is a valid date, {@code false} otherwise
    * @see    java.time.LocalDate
    */
    public static boolean isValidDate(String dateString) {
        if (!hasDateSyntax(dateString)) return false;

        int year  = toInt(dateString, 0, 4);
        int month = toInt(dateString, 5, 7);
        int day   = toInt(dateString, 8, 10);

        if (!isValidGregorianDate(year, month, day)) return false;

        return dateString.compareTo(currentTodayYmd()) >= 0;
    }

    /**
    * Validates whether a given datetime string is in the correct format 
    * (YYYY-MM-DDTHH:MM:SS) and represents a valid Gregorian date/time not 
    * earlier than today.
    * <p>
    * The validation steps are:
    * <ul>
    *   <li>Check syntax (length = 19, correct separators, numeric parts).</li>
    *   <li>Validate the date part (must be valid and not earlier than today).</li>
    *   <li>Validate the time part (hours 0–23, minutes/seconds 0–59).</li>
    * </ul>
    *
    * @param  dateTimeString the datetime string to validate, e.g. {@code "2025-09-01T12:30:45"}
    * @return {@code true} if the string is a valid datetime, {@code false} otherwise
    * @see    java.time.LocalDateTime
    */
    public static boolean isValidDateTime(String dateTimeString) {
        if (!hasDateTimeSyntax(dateTimeString)) return false;

        // Validate date part
        String datePart = dateTimeString.substring(0, 10);
        int year  = toInt(datePart, 0, 4);
        int month = toInt(datePart, 5, 7);
        int day   = toInt(datePart, 8, 10);

        if (!isValidGregorianDate(year, month, day)) return false;
        if (datePart.compareTo(currentTodayYmd()) < 0) return false;

        // Validate time part
        int hour = toInt(dateTimeString, 11, 13);
        int min  = toInt(dateTimeString, 14, 16);
        int sec  = toInt(dateTimeString, 17, 19);

        return isValidTime(hour, min, sec);
    }

    // --- Helpers ---

    
    /**
     * Converts a substring of digits into an integer.
     * <p>
     * Assumes that all characters in the substring are digits.
     *
     * @param  s the source string
     * @param  start the start index (inclusive)
     * @param  end the end index (exclusive)
     * @return the integer value represented by the substring
     */
    private static int toInt(String s, int start, int end) {
        int val = 0;
        for (int i = start; i < end; i++) {
            val = val * 10 + (s.charAt(i) - '0');
        }
        return val;
    }

    /**
     * Checks whether a substring consists only of digits.
     *
     * @param  s the string to check
     * @param  start the start index
     * @param  n the number of characters to check
     * @return {@code true} if all characters are digits, {@code false} otherwise
     */
    private static boolean isDigits(String s, int start, int n) {
        int end = start + n;
        if (start < 0 || end > s.length()) return false;
        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    /**
     * Checks whether a string has valid date syntax ({@code YYYY-MM-DD}).
     *
     * @param  s the string to check
     * @return {@code true} if the string matches the syntax, {@code false} otherwise
     */
    private static boolean hasDateSyntax(String s) {
        if (s == null || s.length() != 10) return false;
        return isDigits(s, 0, 4)
                && s.charAt(4) == '-'
                && isDigits(s, 5, 2)
                && s.charAt(7) == '-'
                && isDigits(s, 8, 2);
    }

    /**
     * Validates whether the given year, month, and day represent a valid
     * Gregorian calendar date.
     * <p>
     * Leap years are explicitly handled.
     *
     * @param  year  the year
     * @param  month the month (1–12)
     * @param  day   the day of the month
     * @return {@code true} if the date is valid, {@code false} otherwise
     */
    private static boolean hasDateTimeSyntax(String s) {
        if (s == null || s.length() != 19) return false;
        // Date part
        if (!hasDateSyntax(s.substring(0, 10))) return false;
        if (s.charAt(10) != 'T') return false;
        // Time part: HH:MM:SS
        return isDigits(s, 11, 2) && s.charAt(13) == ':' &&
               isDigits(s, 14, 2) && s.charAt(16) == ':' &&
               isDigits(s, 17, 2);
    }

    /**
     * Validates whether the given year, month, and day represent a valid
     * Gregorian calendar date.
     * <p>
     * Leap years are explicitly handled.
     *
     * @param  year  the year
     * @param  month the month (1–12)
     * @param  day   the day of the month
     * @return {@code true} if the date is valid, {@code false} otherwise
     */
    private static boolean isValidGregorianDate(int year, int month, int day) {
        if (year < 1) return false;
        if (month < 1 || month > 12) return false;
        int dim = daysInMonth(year, month);
        return day >= 1 && day <= dim;
    }

    /**
     * Determines whether a given year is a leap year.
     * <p>
     * Rule: divisible by 400, or divisible by 4 but not 100.
     *
     * @param  y the year
     * @return {@code true} if the year is a leap year, {@code false} otherwise
     */
    private static boolean isLeapYear(int y) {
        return (y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0));
    }

    /**
     * Returns the number of days in a given month of a given year.
     * <p>
     * February is adjusted for leap years.
     *
     * @param  y the year
     * @param  m the month (1–12)
     * @return the number of days in the month, or {@code 0} if the month is invalid
     */
    private static int daysInMonth(int y, int m) {
        switch (m) {
            case 1: return 31;
            case 2: return isLeapYear(y) ? 29 : 28;
            case 3: return 31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
            default: return 0;
        }
    }

    /**
     * Checks whether the given time values form a valid 24-hour clock time.
     *
     * @param  hour   the hour (0–23)
     * @param  minute the minute (0–59)
     * @param  second the second (0–59)
     * @return {@code true} if the time is valid, {@code false} otherwise
     */
    private static boolean isValidTime(int hour, int minute, int second) {
        return hour >= 0 && hour <= 23 &&
               minute >= 0 && minute <= 59 &&
               second >= 0 && second <= 59;
    }
}

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link DateTimeChecker}.
 * <p>
 * The tests use a fixed "today" date by calling 
 * {@link DateTimeChecker#setTodayForTesting(String)} in {@link #setUp()},
 * and clear it afterwards in {@link #tearDown()}. This ensures deterministic
 * results regardless of the actual system date.
 * <p>
 * Test groups:
 * <ul>
 *   <li><b>isValidDate</b>: syntax validation, ranges, leap year rules,
 *       and past/future constraints.</li>
 *   <li><b>isValidDateTime</b>: strict format checking 
 *       ({@code YYYY-MM-DDTHH:MM:SS}), time range validation, and comparison
 *       against "today".</li>
 * </ul>
 */
public class DateTimeCheckerTest {

    /**
     * Sets up the test by overriding today's date to {@code 1800-09-01}.
     */
    @BeforeEach
    void setUp() {
        DateTimeChecker.setTodayForTesting("1800-09-01");
    }

    /**
     * Cleans up after each test by clearing the date override.
     */
    @AfterEach
    void tearDown() {
        DateTimeChecker.clearTodayForTesting();
    }

    // -------- Tests for isValidDate --------

    /**
     * Verifies that today's date is valid.
     */
    @Test
    void date_today_is_valid() {
        assertTrue(DateTimeChecker.isValidDate("1800-09-01"));
    }

    /**
     * Verifies that a date in the future is valid.
     */
    @Test
    void date_future_is_valid() {
        assertTrue(DateTimeChecker.isValidDate("2025-12-31"));
    }

    /**
     * Verifies that a past date is rejected.
     */
    @Test
    void date_past_is_invalid() {
        assertFalse(DateTimeChecker.isValidDate("1700-08-31"));
    }

    /**
     * Verifies that invalid syntactic formats are rejected.
     */
    @Test
    void date_bad_syntax_is_invalid() {
        assertFalse(DateTimeChecker.isValidDate("20250901"));     // no dashes
        assertFalse(DateTimeChecker.isValidDate("2025/09/01"));   // slashes
        assertFalse(DateTimeChecker.isValidDate("2025-9-01"));    // missing leading zero
        assertFalse(DateTimeChecker.isValidDate("20a5-09-01"));   // non-digit
        assertFalse(DateTimeChecker.isValidDate("2025-09-011"));  // wrong length
    }

    /**
     * Verifies correct handling of month and day ranges, including edge cases.
     */
    @Test
    void date_month_and_day_ranges() {
        assertFalse(DateTimeChecker.isValidDate("2025-00-10")); // month 0
        assertFalse(DateTimeChecker.isValidDate("2025-13-10")); // month 13
        assertFalse(DateTimeChecker.isValidDate("2025-04-31")); // April has 30
        assertFalse(DateTimeChecker.isValidDate("2025-02-30")); // Feb never 30
        assertFalse(DateTimeChecker.isValidDate("2025-02-00")); // day 0
        assertTrue(DateTimeChecker.isValidDate("2025-01-31"));  // valid edge
    }

    /**
     * Verifies leap year rules:
     * <ul>
     *   <li>Divisible by 4 but not 100 is a leap year.</li>
     *   <li>Divisible by 400 is a leap year.</li>
     *   <li>Divisible by 100 but not 400 is not a leap year.</li>
     * </ul>
     */
    @Test
    void date_leap_year_rules() {
        assertTrue(DateTimeChecker.isValidDate("2024-02-29"));   // leap year valid
        assertFalse(DateTimeChecker.isValidDate("2025-02-29"));  // not leap
        assertTrue(DateTimeChecker.isValidDate("2000-02-29"));   // divisible by 400
        assertFalse(DateTimeChecker.isValidDate("1900-02-29"));  // century not /400
    }

    // -------- Tests for isValidDateTime (strict HH:MM:SS) --------

    /**
     * Verifies that valid edge times (midnight and the last second of the day) are accepted.
     */
    @Test
    void datetime_valid_edges_with_seconds() {
        assertTrue(DateTimeChecker.isValidDateTime("2025-09-01T00:00:00"));
        assertTrue(DateTimeChecker.isValidDateTime("2025-09-01T23:59:59"));
    }

    /**
     * Verifies that datetime strings must include a {@code T} separator and seconds.
     */
    @Test
    void datetime_requires_seconds_and_T() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-01T12:00"));    // missing seconds
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-01 12:00:00")); // space instead of 'T'
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-01T120000"));   // no colons
    }

    /**
     * Verifies that invalid time ranges are rejected.
     */
    @Test
    void datetime_invalid_time_ranges() {
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-01T24:00:00")); // hour 24
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-01T12:60:00")); // minute 60
        assertFalse(DateTimeChecker.isValidDateTime("2025-09-01T12:00:60")); // second 60
    }

    /**
     * Verifies that a datetime with a past date is rejected,
     * even if the time is valid.
     */
    @Test
    void datetime_past_date_is_invalid() {
        assertFalse(DateTimeChecker.isValidDateTime("1700-08-31T10:00:00"));
    }

    /**
     * Verifies that a future datetime is valid.
     */
    @Test
    void datetime_future_date_is_valid() {
        assertTrue(DateTimeChecker.isValidDateTime("2025-09-02T10:00:00"));
    }
}

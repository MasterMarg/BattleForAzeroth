package sample.BackEnd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    private final Calendar calendar;
    protected final Date startDate;

    public DateHelper() {
        this.calendar = new GregorianCalendar();
        this.calendar.add(Calendar.YEAR, -1500);
        this.startDate = calendar.getTime();
    }

    public String getFormattedStartDate() {
        DateFormat dateFormat = new SimpleDateFormat("d MMMM y HH:mm");
        return (dateFormat.format(this.startDate));
    }

    public void skipTime() {
        this.calendar.add(Calendar.MINUTE, 1);
    }

    public String getFormattedDiff() {
        String string = "";
        Date endDate = calendar.getTime();
        Duration duration = Duration.between(startDate.toInstant(), endDate.toInstant());
        int days = (int) duration.toDays();
        int hours = (int) duration.minusDays(days).toHours();
        int minutes = (int) duration.minusDays(days).minusHours(hours).toMinutes();
        if (days != 0) {
            if (days > 20 || days < 10) {
                switch (days % 10) {
                    case 1: {string = string + days + " день "; break;}
                    case 2:
                    case 3:
                    case 4: {string = string + days + " дня "; break;}
                    default: {string = string + days + " дней "; break;}
                }
            } else string = string + days + " дней ";
        }
        if (hours != 0) {
            if (hours > 20 || hours < 10) {
                switch (hours % 10) {
                    case 1: {string = string + hours + " час "; break;}
                    case 2:
                    case 3:
                    case 4: {string = string + hours + " часа "; break;}
                    default: {string = string + hours + " часов "; break;}
                }
            } else string = string + hours + " часов ";
        }
        if (minutes != 0) {
            if (minutes < 10 || minutes > 20) {
                switch (minutes % 10) {
                    case 1: {string = string + minutes + " минуту."; break;}
                    case 2:
                    case 3:
                    case 4: {string = string + minutes + " минуты."; break;}
                    default: {string = string + minutes + " минут."; break;}
                }
            } else string = string + minutes + " минут.";
        }
        return string;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("d MMMM y HH:mm");
        return (dateFormat.format(calendar.getTime()));
    }
}

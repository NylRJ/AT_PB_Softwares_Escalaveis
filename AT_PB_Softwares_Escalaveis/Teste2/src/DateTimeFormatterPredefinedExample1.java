import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeFormatterPredefinedExample1 {

    static void print(String format, String result) {
        System.out.printf("%s: %s\n", format, result);
    }

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println("*** LocalDate ***");
        print("FULL", DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(localDate));
        print("LONG", DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(localDate));
        print("MEDIUM", DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(localDate));
        print("SHORT", DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(localDate));

        LocalTime localTime = LocalTime.now();
        System.out.println("\n*** LocalTime ***");
        print("MEDIUM", DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(localTime));
        print("SHORT", DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(localTime));

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("\n*** LocalDateTime ***");
        print("MEDIUM", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(localDateTime));
        print("SHORT", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(localDateTime));

        String data = "2021-06-05T00:40:01.099245700";


        System.out.println("\n*** My data LocalDateTime ***");
        print("MEDIUM", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(LocalDateTime.parse(data)));
        print("SHORT", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.parse(data)));

    }
}

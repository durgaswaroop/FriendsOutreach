import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class DaysDifferenceCalc {
	
	public long daysDiffCalc(LocalDate date){
		LocalDate today = LocalDate.now();
		long days = ChronoUnit.DAYS.between(date, today);
		return days;
	}

}

package com.abhimanyu.apiratelimiter.util;

import com.abhimanyu.apiratelimiter.exception.InvalidRateLimitTimeUnitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class RateLimitUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(RateLimitUtil.class);

	public static int getTimeDelta(RateLimitTimeUnit timeUnit, LocalDateTime previousTime){
		switch (timeUnit){
			case SECONDS:
				return diffInSeconds(previousTime,LocalDateTime.now());
			case MINUTES:
				return  diffInMinutes(previousTime,LocalDateTime.now());
			case HOURS:
				return  diffInHours(previousTime,LocalDateTime.now());
			default:
				throw new InvalidRateLimitTimeUnitException(" The time unit "+timeUnit+" is incorrect. SECONDS, MINUTES and HOURS are valid Time units");

		}
	}

	public static LocalDateTime getCurrentTimeNormalizedToMinute() {
		LocalDateTime now = LocalDateTime.now().withSecond(0);
		LOGGER.info("minute normalized current time = {}", now);
		return now;

	}

	public static int diffInMinutes(LocalDateTime from, LocalDateTime to) {
		LocalDateTime fromTemp = LocalDateTime.from(from);

		Long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
		fromTemp = fromTemp.plusMinutes(minutes);
		return minutes.intValue();

	}

	public static int diffInHours(LocalDateTime from, LocalDateTime to) {
		LocalDateTime fromTemp = LocalDateTime.from(from);

		Long hours = fromTemp.until(to, ChronoUnit.HOURS);
		fromTemp = fromTemp.plusMinutes(hours);
		return hours.intValue();

	}
	public static int diffInSeconds(LocalDateTime from, LocalDateTime to) {
		LocalDateTime fromTemp = LocalDateTime.from(from);

		Long secondsDelta = fromTemp.until(to, ChronoUnit.SECONDS);
		fromTemp = fromTemp.plusMinutes(secondsDelta);
		return secondsDelta.intValue();

	}
}

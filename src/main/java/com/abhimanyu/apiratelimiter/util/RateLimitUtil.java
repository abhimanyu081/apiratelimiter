package com.abhimanyu.apiratelimiter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RateLimitUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(RateLimitUtil.class);

	public static LocalDateTime getCurrentTimeNormalizedtoMinute() {
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

	public static int diffInSeconds(LocalDateTime from, LocalDateTime to) {
		LocalDateTime fromTemp = LocalDateTime.from(from);

		Long secondsDelta = fromTemp.until(to, ChronoUnit.SECONDS);
		fromTemp = fromTemp.plusMinutes(secondsDelta);
		return secondsDelta.intValue();

	}
}

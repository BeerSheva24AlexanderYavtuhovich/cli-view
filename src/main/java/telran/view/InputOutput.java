package telran.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public interface InputOutput {
	String readString(String prompt);

	void writeString(String str);

	default void writeLine(Object obj) {
		writeString(obj.toString() + "\n");
	}

	default <T> T readObject(String prompt, String errorPrompt, Function<String, T> mapper) {
		boolean running;
		T res = null;
		do {
			running = false;
			try {
				String strRes = readString(prompt);
				res = mapper.apply(strRes);
			} catch (Exception e) {
				writeLine(errorPrompt + ": " + e.getMessage());
				running = true;
			}

		} while (running);
		return res;
	}

	/**
	 * 
	 * @param prompt
	 * @param errorPrompt
	 * @return Integer number
	 */
	default Integer readInt(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Integer::parseInt);
	}

	default Long readLong(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Long::parseLong);
	}

	default Double readDouble(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, Double::parseDouble);
	}

	default Double readNumberRange(String prompt, String errorPrompt, double min, double max) {
		return readObject(prompt, errorPrompt, str -> {
			Double resDouble = Double.valueOf(str);
			if (resDouble < min || resDouble > max) {
				throw new RuntimeException("Number is out of bounds");
			}
			return resDouble;
		});
	}

	default String readStringPredicate(String prompt, String errorPrompt,
			Predicate<String> predicate) {
		return readObject(prompt, errorPrompt, input -> {
			if (!predicate.test(input)) {
				throw new IllegalArgumentException("Input does not satisfy the predicate.");
			}
			return input;
		});
	}

	default String readStringOptions(String prompt, String errorPrompt,
			HashSet<String> options) {
		return readObject(prompt, errorPrompt, input -> {
			if (!options.contains(input)) {
				throw new IllegalArgumentException("Input does not contain valid options.");
			}
			return input;
		});
	}

	default LocalDate readIsoDate(String prompt, String errorPrompt) {
		return readObject(prompt, errorPrompt, LocalDate::parse);
	}

	default LocalDate readIsoDateRange(String prompt, String errorPrompt, LocalDate from,
			LocalDate to) {
		return readObject(prompt, errorPrompt, input -> {
			LocalDate date = LocalDate.parse(input);
			if (date.isBefore(from) || date.isAfter(to)) {
				throw new IllegalArgumentException("Date is out of range.");
			}
			return date;
		});
	}

}
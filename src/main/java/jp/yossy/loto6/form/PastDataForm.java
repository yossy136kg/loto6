package jp.yossy.loto6.form;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PastDataForm implements Serializable {

	private static final long serialVersionUID = 1330043957072942382L;

	public static interface DateSearch {
    };

	public static interface TimesSearch {
    };

    @NotNull(groups = DateSearch.class)
	@Min(1)
	@Max(2)
	private Integer searchType;

	@NotNull(groups = DateSearch.class, message="検索年を入力してください")
	@Min(groups = DateSearch.class, value=1900, message="正しく検索年を入力してください")
	@Max(groups = DateSearch.class, value=9999, message="正しく検索年を入力してください")
	private Integer searchYear;

	@NotNull(groups = DateSearch.class, message="検索月を入力してください")
	@Min(groups = DateSearch.class, value=1, message="正しく検索月を入力してください")
	@Max(groups = DateSearch.class, value=12, message="正しく検索月を入力してください")
	private Integer searchMonth;

	@NotNull(groups = DateSearch.class, message="検索日を入力してください")
	@Min(groups = DateSearch.class, value=1, message="正しく検索日を入力してください")
	@Max(groups = DateSearch.class, value=31, message="正しく検索日を入力してください")
	private Integer searchDay;

	@NotNull(groups = TimesSearch.class, message="回号数を入力してください")
	@Min(groups = TimesSearch.class, value=1, message="正しく回号数を入力してください")
	@Max(groups = TimesSearch.class, value=99999, message="正しく回号数を入力してください")
	private Integer searchTimes;

	@NotNull
	private Integer pages;

	@AssertTrue(groups = DateSearch.class, message="検索日時の入力が不正です")
	public boolean isDateValid() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("uuuu/M/d").withResolverStyle(ResolverStyle.STRICT);
		String dt = this.getSearchYear() + "/" + this.getSearchMonth() + "/" + this.getSearchDay();

		try {
			LocalDate.parse(dt, df);
		} catch (DateTimeParseException e) {
			return false;
		}

		return true;
	}

}

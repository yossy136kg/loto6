package jp.yossy.loto6.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.yossy.loto6.form.PastDataForm;
import jp.yossy.loto6.form.PastDataForm.DateSearch;
import jp.yossy.loto6.form.PastDataForm.TimesSearch;
import jp.yossy.loto6.repository.Loto6Data;
import jp.yossy.loto6.repository.Loto6DataRepository;

@Controller
@RequestMapping("loto6")
public class Loto6Controller {
	static final Logger logger = LoggerFactory.getLogger(Loto6Controller.class);

	static final int INIT_PAGES = 10;

	static final Map<String, String> PAGES_ITEMS = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
		{
			put("10", "10");
			put("50", "50");
			put("100", "100");
		}
	});

	@Autowired
	Loto6DataRepository loto6DataRepository;

	@Autowired
	MessageSource msg;

	/**
	 * ロト6(LOTO6)過去の抽せん結果取得
	 */
	private Page<Loto6Data> getResults(PastDataForm form) {

		Page<Loto6Data> results = null;
		if (form.getSearchType() == 1) {
			LocalDateTime dt = LocalDateTime.of(form.getSearchYear(), form.getSearchMonth(), form.getSearchDay(), 0, 0,
					0);

			Instant instant = ZonedDateTime.of(dt, ZoneId.systemDefault()).toInstant();
			results = loto6DataRepository.findByLotteryDateBeforeOrderByLotteryDateDesc(Date.from(instant),
					PageRequest.of(0, form.getPages(), Sort.by(Sort.Direction.DESC, "lotteryDate")));
		} else if (form.getSearchType() == 2) {
			results = loto6DataRepository.findByIdLessThanEqualOrderByLotteryDateDesc(form.getSearchTimes(),
					PageRequest.of(0, form.getPages(), Sort.by(Sort.Direction.DESC, "lotteryDate")));
		}

		return results;
	}

	/**
	 * for debug.
	 */
	@SuppressWarnings("unchecked")
	private void modelDump(Model model, String m) {
		logger.debug(" ");
		logger.debug("Model:{}", m);
		Map<String, Object> mm = model.asMap();
		for (Entry<String, Object> entry : mm.entrySet()) {
			logger.debug("key:{}", entry.getKey());
			if (StringUtils.equals(entry.getKey(), "list")) {
				for (Loto6Data data : (List<Loto6Data>) entry.getValue()) {
					logger.debug(
							ToStringBuilder.reflectionToString(data, ToStringStyle.DEFAULT_STYLE));
				}
			}
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * ロト6(LOTO6)過去の抽せん結果取得
	 */
	@RequestMapping(value = "past", method = RequestMethod.GET)
	public String init(PastDataForm form, Model model) {

		LocalDateTime now = LocalDateTime.now();
		form.setSearchType(1);
		form.setSearchYear(now.getYear());
		form.setSearchMonth(now.getMonthValue());
		form.setSearchDay(now.getDayOfMonth());
		form.setPages(INIT_PAGES);

		model.addAttribute("pagesItems", PAGES_ITEMS);

		Instant instant = ZonedDateTime.of(now.truncatedTo(ChronoUnit.DAYS), ZoneId.systemDefault()).toInstant();
		Page<Loto6Data> results = loto6DataRepository.findByLotteryDateBeforeOrderByLotteryDateDesc(Date.from(instant),
				PageRequest.of(0, INIT_PAGES, Sort.by(Sort.Direction.DESC, "lotteryDate")));

		if (results.getSize() == 0) {
			String message = msg.getMessage("loto6.list.empty", null, Locale.JAPAN);
			model.addAttribute("emptyMessage", message);

			return "pastdata";
		}

		model.addAttribute("results", results.getContent());
		modelDump(model, "init");

		return "pastdata";
	}

	/**
	 * ロト6(LOTO6)過去の抽せん結果取得
	 */
	@RequestMapping(value = "past/dateSearch", method = RequestMethod.POST)
	public String searchDate(@Validated(DateSearch.class) @ModelAttribute PastDataForm form, BindingResult result, Model model) {
		model.addAttribute("pagesItems", PAGES_ITEMS);
		if (result.hasErrors()) {
			return "pastdata";
		}

		Page<Loto6Data> results = getResults(form);
		if (results.getSize() == 0) {
			String message = msg.getMessage("loto6.list.empty", null, Locale.JAPAN);
			model.addAttribute("emptyMessage", message);
		} else {
			model.addAttribute("results", results.getContent());
		}

		modelDump(model, "searchDate");

		return "pastdata";
	}

	/**
	 * ロト6(LOTO6)過去の抽せん結果取得
	 */
	@RequestMapping(value = "past/timesSearch", method = RequestMethod.POST)
	public String searchTimes(@Validated(TimesSearch.class) @ModelAttribute PastDataForm form, BindingResult result, Model model) {
		model.addAttribute("pagesItems", PAGES_ITEMS);
		if (result.hasErrors()) {
			return "pastdata";
		}

		Page<Loto6Data> results = getResults(form);
		if (results.getSize() == 0) {
			String message = msg.getMessage("loto6.list.empty", null, Locale.JAPAN);
			model.addAttribute("emptyMessage", message);
		} else {
			model.addAttribute("results", results.getContent());
		}

		modelDump(model, "searchTimes");

		return "pastdata";
	}

}

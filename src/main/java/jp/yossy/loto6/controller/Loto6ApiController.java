package jp.yossy.loto6.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.yossy.loto6.form.PastDataForm;
import jp.yossy.loto6.form.PastDataForm.DateSearch;
import jp.yossy.loto6.repository.Loto6Data;
import jp.yossy.loto6.repository.Loto6DataRepository;

@RestController
public class Loto6ApiController {
	static final Logger logger = LoggerFactory.getLogger(Loto6Controller.class);

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

	@RequestMapping(value = "/result",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getResult(@Validated(DateSearch.class) @ModelAttribute PastDataForm form, BindingResult result) {
		if (result.hasErrors()) {
			return result.getAllErrors();
		}
		if (form.getPages() == null) {
			form.setPages(Loto6Controller.INIT_PAGES);
		}
		Page<Loto6Data> results = getResults(form);

		if (results.getContent().size() == 0) {
			String message = msg.getMessage("loto6.list.empty", null, Locale.JAPAN);
			List<String> rt = new ArrayList<String>();
			rt.add(message);
			return rt;
		}
		return results.getContent();
	}
}

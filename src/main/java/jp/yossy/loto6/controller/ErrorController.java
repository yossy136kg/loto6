package jp.yossy.loto6.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
  final static Logger logger = LoggerFactory.getLogger(ErrorController.class);

  @RequestMapping("/error/404")
  public String error404() {
    return "Error/404";
  }

  @RequestMapping("/error/500")
  public String error500() {
    return "Error/500";
  }
}

package com.dkaedv.glghproxy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.util.AntPathMatcher;

@Controller
public class RedirectsController {

	@Value("${gitlabUrl}")
	private String gitlabUrl;

	@RequestMapping("/{namespace}")
	public String namespace(
			@PathVariable String namespace
			) {
		
		return "redirect:" + gitlabUrl + "/" + namespace;
	}

	@RequestMapping("/{namespace}/{repo}")
	public String repoHomepage(
			@PathVariable String namespace,
			@PathVariable String repo
			) {
		
		return "redirect:" + gitlabUrl + "/" + namespace + "/" + repo;
	}
	
	@RequestMapping("/{namespace}/{repo}/commit/{sha}")
	public String commit(
			@PathVariable String namespace,
			@PathVariable String repo,
			@PathVariable String sha
			) {
		
		return "redirect:" + gitlabUrl + "/" + namespace + "/" + repo + "/commit/" + sha;
	}

	@RequestMapping("/{namespace}/{repo}/tree/**")
	public String repoTree(
			@PathVariable String namespace,
			@PathVariable String repo,
			HttpServletRequest request
			//@PathVariable String branch
			) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		AntPathMatcher apm = new AntPathMatcher();
		String branch = apm.extractPathWithinPattern(bestMatchPattern, path);
		branch = branch.replaceAll("%2F", "/");

		return "redirect:" + gitlabUrl + "/" + namespace + "/" + repo + "/tree/" + branch;
	}
}

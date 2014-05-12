package com.nhaowan.mobile.base.utils;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.text.TextUtils;

public class HtmlParseUtils {
	public static ArrayList<ArticleBean> parseHtml(String parseHtml, String baseUrl) {
		ArrayList<ArticleBean> articles = new ArrayList<ArticleBean>();
		Document document = Jsoup.parseBodyFragment(parseHtml);
		// Elements elements = document.getElementsByTag("p");
		Elements bodyAllElement = document.getElementsByTag("body");
		if (bodyAllElement != null && bodyAllElement.size() > 0) {
			Elements childElement = bodyAllElement.get(0).children();
			for (int index = 0; childElement != null && index < childElement.size(); index++) {
				ArticleBean article = new ArticleBean();
				Element element = childElement.get(index);
				if (element.hasText()) {
					article.setContentFragment(element.html());
				}

				Elements imgElements = element.getElementsByTag("img");
				if (imgElements != null) {
					if (imgElements.hasAttr("src")) {
						article.setUrl(imgElements.attr("src"));
					}
					if (imgElements.hasAttr("alt")) {
						article.setAlt(imgElements.attr("alt"));
					}
				}
				if ("h2".equals(element.tagName())) {
					article.setTextStyle(ArticleBean.TEXT_STYLE_BOLD);
				}
				if (TextUtils.isEmpty(article.getUrl()) && TextUtils.isEmpty(article.getContentFragment())) {
					continue;
				}
				articles.add(article);
			}
		} else {
			if (articles.size() == 0 && !TextUtils.isEmpty(parseHtml)) {
				ArticleBean article = new ArticleBean();
				article.setContentFragment(parseHtml);
				articles.add(article);
			}
		}

		return articles;
	}
}

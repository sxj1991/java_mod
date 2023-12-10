package com.xjsun.spider.http;

import cn.hutool.http.HtmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * java 爬虫框架webMagic demo
 */
@Slf4j
public class HttpPageProcessor implements PageProcessor {

    @Override
    public void process(Page page) {
        //css 提取器爬去数据
        Document document = page.getHtml().getDocument();

        String content = HtmlUtil.cleanHtmlTag(document.html());

        //放入数据
        log.info("网页内容获取:{}", content);
    }

    @Override
    public Site getSite() {
        return Site.me()
                .addCookie(".it235.com","Hm_lpvt_e312f85a409131e18146064e62b19798","1702194549")
                .addCookie(".it235.com","Hm_lvt_e312f85a409131e18146064e62b19798","1702194549")
                .setCharset("UTF-8")
                .addHeader("Connection","keep-alive")
                .setUseGzip(true)
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
    }

    public static void start(){
        Spider.create(new HttpPageProcessor())
                .addUrl("https://www.it235.com/")
                .thread(5)
                .run();
    }
}

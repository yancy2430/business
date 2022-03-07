package com.tdeado.business;

import org.apache.tomcat.util.security.MD5Encoder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import org.apache.commons.io.IOUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@SpringBootApplication
public class BusinessApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
        System.setProperty("mail.mime.splitlongparameters", "false");
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(factory);
        return template;
    }
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {

        Document document = Jsoup.connect("http://www.shiyebian.net/hunan/").get();
        for (Element element : document.select(".main .listbox .listlie li a")) {
            sendMail(element.attr("href"));
            Thread.sleep(1000*30);
        }
    }
    @Autowired
    JavaMailSender mailSender;
    public void sendMail(String url) throws IOException, MessagingException {
        Object val = redisTemplate.opsForValue().get(MD5Encoder.encode(url.getBytes(StandardCharsets.UTF_8)));
        if (null!=val){
            return;
        }
        Document html = Jsoup.connect(url).execute().parse();
        Elements content = html.select(".zhengwen");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom("yancy@tdeado.com");
        messageHelper.setTo("1310297087@qq.com");
        messageHelper.setSubject(html.select(".mleft .content h1").text());
        messageHelper.setText(content.html() + " <br/> :"+ url, true);
        for (Element a : content.select("a")) {
            if (a.attr("href").contains(".doc") || a
                    .attr("href").contains(".DOC") || a
                    .attr("href").contains(".docx") || a
                    .attr("href").contains(".DOCX") || a
                    .attr("href").contains(".csx") || a
                    .attr("href").contains(".xlsx") || a
                    .attr("href").contains(".xls") || a
                    .attr("href").contains(".XLSX") || a
                    .attr("href").contains(".XLS") || a
                    .attr("href").contains(".CSX")) {
                String name = a.text()+a.attr("href").substring(a.attr("href").lastIndexOf("."));
                Connection.Response file = Jsoup.connect(a.attr("href")).ignoreContentType(true).header("Referer", url).header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36").followRedirects(true).execute();
                messageHelper.addAttachment(name, new ByteArrayResource(IOUtils.toByteArray(file.bodyStream())));
            }
        }
        redisTemplate.opsForValue().set(MD5Encoder.encode(url.getBytes(StandardCharsets.UTF_8)),url);
        mailSender.send(mimeMessage);
        System.err.println("" + html.select(".mleft .content h1").text() + " " + LocalDateTime.now());
    }
}

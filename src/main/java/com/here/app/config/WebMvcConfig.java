package com.here.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import java.time.format.DateTimeFormatter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	private static final String dateFormat = "yyyy-MM-dd";
	private static final String timeFormat = "HH:mm:ss";
	private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	
	/*@Autowired
	AccsUUIDcheckInterceptor accsUUIDcheckInterceptor;*/
	/*@Autowired
    LogincheckInterceptor logincheckInterceptor;
	@Autowired
    QrcodecheckInterceptor qrcodecheckInterceptor;
	@Autowired
	DomainCheckInterceptor domainCheckInterceptor;*/
//	
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.add(escapingConverter());
//	}
	
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		/*registry.addResourceHandler("/web/**").addResourceLocations("/web/")
				.setCacheControl(CacheControl.maxAge(10, TimeUnit.DAYS)).resourceChain(true)
//				.setCacheControl(CacheControl.noStore()).resourceChain(true)
				.addResolver(new VersionResourceResolver()
	                    .addContentVersionStrategy("/**"))
	                .addTransformer(new AppCacheManifestTransformer());*/
		
		// html 가능 하게 설정
		/*registry.addResourceHandler("/**").addResourceLocations("/web/hcws/html/");*/
	}
	
	@Bean
	public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
		return new ResourceUrlEncodingFilter();
	}
	
	 @Override
	 public void addCorsMappings(CorsRegistry registry) {
		 //임시로 크로스오리진 전체를 품
		 registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
		 
	   
	 }
	 
//
//	@Bean
//	public HttpMessageConverter escapingConverter() {
//		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//		builder.simpleDateFormat(dateTimeFormat);
//		builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(timeFormat)));
//		builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
//		builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
//
//		builder.deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern(timeFormat)));
//		builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)));
//		builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
//		builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//		builder.featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
//		builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//		ObjectMapper objectMapper = builder.build();
////		objectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
//		MappingJackson2HttpMessageConverter escapingConverter = new MappingJackson2HttpMessageConverter();
//		escapingConverter.setObjectMapper(objectMapper);
//
//		return escapingConverter;
//	}
	
	@Override
    public void addFormatters(FormatterRegistry registry) {
		// json
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();

        registrar.setDateFormatter(DateTimeFormatter.ofPattern(dateFormat));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(dateTimeFormat));
        registrar.registerFormatters(registry);

        /* ISO 타입.
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
        */
    }
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//접근고유ID 체크 Interceptor
		/*registry.addInterceptor(accsUUIDcheckInterceptor)
				.addPathPatterns("/sendbox/")
				.addPathPatterns("/sendbox/index");*/

//		//로그인 체크 Interceptor
/*		registry.addInterceptor(logincheckInterceptor)
				.addPathPatterns("/front/pass/**")
				.excludePathPatterns("/front/pass/check"); //추후 삭제
//				.excludePathPatterns("/front/login/certifyhp") //추후 삭제
//				.excludePathPatterns("/front/login/index");
//
//		//QRCODE 체크 Interceptor
		registry.addInterceptor(qrcodecheckInterceptor)
//				.addPathPatterns("/front/login/**")
//				.addPathPatterns("/front/join/**")
				.addPathPatterns("/front/pass/**")
//				.addPathPatterns("/front/prreg/**")//추후 추가
//				.excludePathPatterns("/")
//				.excludePathPatterns("/front/login/certifyhp") //추후 삭제
				.excludePathPatterns("/front/pass/list"); //추후 삭제
//				.excludePathPatterns("/front/pass/check"); //추후 삭제
//				.excludePathPatterns("/front/login/cookieDelete");
		
		registry.addInterceptor(domainCheckInterceptor)
				.addPathPatterns("/","/**")
				.excludePathPatterns("/web/**")
				.excludePathPatterns("/rest/**");
		*/

	}
}

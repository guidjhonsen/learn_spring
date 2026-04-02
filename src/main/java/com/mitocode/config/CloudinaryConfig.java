package com.mitocode.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name","dxlynjork",
                        "api_key", "835217679215838",
                        "api_secret", "MEMFZoFeTpKPclwhwn6TFFaG76U"
                )
        );
    }

}

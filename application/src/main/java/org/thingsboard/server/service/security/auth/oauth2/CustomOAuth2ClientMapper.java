/**
 * Copyright © 2020 SOLTEKNO.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.service.security.auth.oauth2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.thingsboard.server.common.data.oauth2.OAuth2ClientRegistrationInfo;
import org.thingsboard.server.common.data.oauth2.OAuth2CustomMapperConfig;
import org.thingsboard.server.common.data.oauth2.OAuth2MapperConfig;
import org.thingsboard.server.dao.oauth2.OAuth2User;
import org.thingsboard.server.service.security.model.SecurityUser;

@Service(value = "customOAuth2ClientMapper")
@Slf4j
public class CustomOAuth2ClientMapper extends AbstractOAuth2ClientMapper implements OAuth2ClientMapper {
    private static final String PROVIDER_ACCESS_TOKEN = "provider-access-token";

    private static final ObjectMapper json = new ObjectMapper();

    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    @Override
    public SecurityUser getOrCreateUserByClientPrincipal(OAuth2AuthenticationToken token, String providerAccessToken, OAuth2ClientRegistrationInfo clientRegistration) {
        OAuth2MapperConfig config = clientRegistration.getMapperConfig();
        OAuth2User oauth2User = getOAuth2User(token, providerAccessToken, config.getCustom());
        return getOrCreateSecurityUserFromOAuth2User(oauth2User, clientRegistration);
    }

    private synchronized OAuth2User getOAuth2User(OAuth2AuthenticationToken token, String providerAccessToken, OAuth2CustomMapperConfig custom) {
        if (!StringUtils.isEmpty(custom.getUsername()) && !StringUtils.isEmpty(custom.getPassword())) {
            restTemplateBuilder = restTemplateBuilder.basicAuthentication(custom.getUsername(), custom.getPassword());
        }
        if (custom.isSendToken() && !StringUtils.isEmpty(providerAccessToken)) {
            restTemplateBuilder = restTemplateBuilder.defaultHeader(PROVIDER_ACCESS_TOKEN, providerAccessToken);
        }

        RestTemplate restTemplate = restTemplateBuilder.build();
        String request;
        try {
            request = json.writeValueAsString(token.getPrincipal());
        } catch (JsonProcessingException e) {
            log.error("Can't convert principal to JSON string", e);
            throw new RuntimeException("Can't convert principal to JSON string", e);
        }
        try {
            return restTemplate.postForEntity(custom.getUrl(), request, OAuth2User.class).getBody();
        } catch (Exception e) {
            log.error("There was an error during connection to custom mapper endpoint", e);
            throw new RuntimeException("Unable to login. Please contact your Administrator!");
        }
    }
}

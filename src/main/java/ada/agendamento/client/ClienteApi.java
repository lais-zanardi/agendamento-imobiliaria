package ada.agendamento.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Component
public class ClienteApi {
    private final static String ENDPOINT = "http://{host}:{port}/cliente/{id}";
    @Autowired
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;

    public boolean clienteExiste(Long id) {
        InstanceInfo info = eurekaClient
                .getApplication("agendamento-imobiliaria")
                .getInstances()
                .stream()
                .findAny()
                .orElseThrow(()-> new RemoteAccessException("API Cliente indisponível"));

        return restTemplate.getForEntity(ENDPOINT, String.class, info.getHostName(), info.getPort(), id)
                .getStatusCode()
                .is2xxSuccessful();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

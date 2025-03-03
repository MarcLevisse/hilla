package dev.hilla;

import static org.mockito.Mockito.mock;

import jakarta.servlet.ServletContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hilla.auth.CsrfChecker;
import dev.hilla.auth.EndpointAccessChecker;

import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

/**
 * A helper class to build a mocked EndpointController.
 */
public class EndpointControllerMockBuilder {
    private ApplicationContext applicationContext;
    private ObjectMapper objectMapper;
    private EndpointNameChecker endpointNameChecker = mock(
            EndpointNameChecker.class);
    private ExplicitNullableTypeChecker explicitNullableTypeChecker = mock(
            ExplicitNullableTypeChecker.class);

    public EndpointControllerMockBuilder withApplicationContext(
            ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        return this;
    }

    public EndpointControllerMockBuilder withObjectMapper(
            ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        return this;
    }

    public EndpointControllerMockBuilder withEndpointNameChecker(
            EndpointNameChecker endpointNameChecker) {
        this.endpointNameChecker = endpointNameChecker;
        return this;
    }

    public EndpointControllerMockBuilder withExplicitNullableTypeChecker(
            ExplicitNullableTypeChecker explicitNullableTypeChecker) {
        this.explicitNullableTypeChecker = explicitNullableTypeChecker;
        return this;
    }

    public EndpointController build() {
        EndpointRegistry registry = new EndpointRegistry(endpointNameChecker);
        CsrfChecker csrfChecker = Mockito.mock(CsrfChecker.class);
        ServletContext servletContext = Mockito.mock(ServletContext.class);
        Mockito.when(csrfChecker.validateCsrfTokenInRequest(Mockito.any()))
                .thenReturn(true);
        EndpointInvoker invoker = Mockito
                .spy(new EndpointInvoker(applicationContext, objectMapper,
                        explicitNullableTypeChecker, servletContext, registry));
        EndpointController controller = Mockito.spy(new EndpointController(
                applicationContext, registry, invoker, csrfChecker));
        Mockito.doReturn(mock(EndpointAccessChecker.class)).when(invoker)
                .getAccessChecker();
        return controller;
    }
}

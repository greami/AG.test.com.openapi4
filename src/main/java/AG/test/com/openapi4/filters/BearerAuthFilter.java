package AG.test.com.openapi4.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/*@Component
@Order(1)*/
public class BearerAuthFilter implements jakarta.servlet.Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String auth = request.getAttribute("Authorization").toString();
    }

    @Override
    public void destroy()
    {
        Filter.super.destroy();
    }
}

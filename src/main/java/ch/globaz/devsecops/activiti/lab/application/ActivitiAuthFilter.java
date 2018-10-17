package ch.globaz.devsecops.activiti.lab.application;


import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.identity.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
@Component
public class ActivitiAuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("Activiti identity filter");

        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if(authToken != null){
            User authUser = (User) authToken.getPrincipal();

            Authentication.setAuthenticatedUserId(authUser.getUsername());

            log.info("Identity set for activiti: {}", authUser.getUsername());
        }


        filterChain.doFilter(servletRequest,servletResponse);
    }
}

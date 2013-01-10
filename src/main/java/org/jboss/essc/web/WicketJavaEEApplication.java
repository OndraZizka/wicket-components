package org.jboss.essc.web;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.ftlines.wicket.cdi.CdiConfiguration;
import net.ftlines.wicket.cdi.ConversationPropagation;
import org.apache.wicket.*;
import org.apache.wicket.protocol.http.WebApplication;
import org.jboss.essc.web.test.EditableTestPage;


/**
 * Wicket application class.
 * 
 * @author Ondrej Zizka
 */
public class WicketJavaEEApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return EditableTestPage.class;
    }

    
    @Override
    protected void init() {
        super.init();

        // Enable CDI
        BeanManager bm;
        try {
            bm = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            throw new IllegalStateException("Unable to obtain CDI BeanManager", e);
        }

        // Configure CDI, disabling Conversations as we aren't using them
        new CdiConfiguration(bm).setPropagation(ConversationPropagation.NONE).configure(this);

        // This would prevent Ajax components throwing an exception after session expiration.
        //this.getPageSettings().setRecreateMountedPagesAfterExpiry(false);
        
        //this.getApplicationSettings().setPageExpiredErrorPage(ErrorPage.class);
        this.getMarkupSettings().setStripWicketTags(true);
        //this.getResourceSettings().setThrowExceptionOnMissingResource( false ); // Fix: http://localhost:8080/essc-portal/release/EAP/HomePage.html?0
        //this.getPageSettings().setVersionPagesByDefault(false);
        
        
        // Mounts
        mountPage("/test",  EditableTestPage.class);
        
        
    }// init()
    
    
    
    
}// class

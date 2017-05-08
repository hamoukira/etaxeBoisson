import javax.servlet.ServletContext;
import org.ocpsoft.logging.Logger.Level;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Log;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

@RewriteConfiguration
public class ApplicationConfigurationProvider extends HttpConfigurationProvider
{
   @Override
   public Configuration getConfiguration(ServletContext context)
   {
      return ConfigurationBuilder.begin()
         .addRule()
         .perform(Log.message(Level.INFO, "Rewrite is active."))
         .addRule(Join.path("/login").to("/faces/Login.xhtml"))
         .addRule(Join.path("/home").to("/faces/secured/home/accueil.xhtml"))
         .addRule(Join.path("/redevableEtLocale").to("/faces/secured/redevable/CreateNewRedevable.xhtml"))
         .addRule(Join.path("/redevables").to("/faces/secured/redevable/List.xhtml"))
         .addRule(Join.path("/locales").to("/faces/secured/locale/List.xhtml"))
         .addRule(Join.path("/statistiques").to("/faces/secured/statistics/Statistique.xhtml"))
         .addRule(Join.path("/adressage").to("/faces/secured/commune/List.xhtml"))
         .addRule(Join.path("/admin/users").to("/faces/secured/user/List.xhtml"))
         .addRule(Join.path("/taxe/annuel").to("/faces/secured/taxeAnnuelBoisson/List.xhtml"))
         .addRule(Join.path("/map").to("/faces/secured/locale/LocalMap.xhtml"))
         .addRule(Join.path("/admin/usersLog").to("/faces/secured/history/List.xhtml"))
         .addRule(Join.path("/admin/activiteLog").to("/faces/secured/journal/List.xhtml"))
         .addRule(Join.path("/activites").to("/faces/secured/activite/List.xhtml"))
         .addRule(Join.path("/activite/cree").to("/faces/secured/activite/CreateActivite.xhtml"))
         .addRule(Join.path("/taxe/trimestre").to("/faces/secured/taxeTrimBoisson/List.xhtml"))
         .addRule(Join.path("/reset").to("/faces/ResetePassword.xhtml"))
      ;
   }

   @Override
   public int priority()
   {
      return 0;
   }
}
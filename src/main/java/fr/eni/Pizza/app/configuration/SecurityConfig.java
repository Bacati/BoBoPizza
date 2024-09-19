package fr.eni.Pizza.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        String sql = "SELECT email, mot_de_passe, 1 FROM utilisateur WHERE email= ?;";
        jdbcUserDetailsManager.setUsersByUsernameQuery(sql);
        sql = "SELECT utilisateur.email, role.libelle \n" +
                "FROM utilisateur \n" +
                "INNER JOIN role_utilisateur ON utilisateur.id_utilisateur = role_utilisateur.UTILISATEUR_id_utilisateur \n" +
                "INNER JOIN role ON role.id_role = role_utilisateur.ROLE_id_role \n" +
                "WHERE email = ?";
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(sql);
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorize) -> authorize
                                //hasAuthority() permet de gérer un seul et unique ROLE de l'user connecté
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/laCarte").permitAll()
                                .requestMatchers(HttpMethod.GET, "/produit").hasAuthority("GERANT")
                                .requestMatchers(HttpMethod.POST, "/produit").hasAuthority("GERANT")
                                .requestMatchers("/allCommande").hasAnyAuthority("PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers(HttpMethod.POST, "/delete").hasAuthority("GERANT")
                                .requestMatchers("/commande").hasAnyAuthority("CLIENT", "PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers("/detailCommande").hasAnyAuthority("PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers("/panier").hasAnyAuthority("CLIENT", "PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers(HttpMethod.POST, "/updateQuantite").hasAnyAuthority("CLIENT", "PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers(HttpMethod.POST, "/deleteProductInBasket").hasAnyAuthority("CLIENT", "PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers(HttpMethod.POST, "/deleteCommande").hasAnyAuthority("CLIENT", "PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers(HttpMethod.POST, "/commander").hasAnyAuthority("CLIENT", "PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers(HttpMethod.POST, "/updateEtat").hasAnyAuthority("PIZZAIOLO", "LIVREUR", "GERANT")
                                .requestMatchers(HttpMethod.POST, "/creerPanier").authenticated()
                                .requestMatchers("/subscribe").permitAll()
                                .requestMatchers("/profil").authenticated()
                                .requestMatchers(HttpMethod.GET, "/user").hasAuthority("GERANT")
                                .requestMatchers(HttpMethod.POST, "/user").hasAuthority("GERANT")
                                //.requestMatchers("/user").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/user-connected").authenticated()
                                .requestMatchers("/logout").authenticated()
                                //original
                                /*.requestMatchers("/logout").permitAll()*/
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/javascript/**").permitAll()
                                .requestMatchers("/style/**").permitAll()
                                .requestMatchers("/vendor/**").permitAll()
                                //Pour toutes les autres requêtes qui ne sont pas explicitement déclarées ci-dessus,
                                //  alors on peut soit tout autoriser après authentification via .anyRequest().authenticated()
                                // ou alors plutôt les rejettées par défaut via:
                                //ATTENTION: cette dernière requête anyRequest() doit être la dernière instruction!
                                .anyRequest().denyAll()


                        // remarque : hasRole("ADMIN") est utilisable en lieu et place de hasAuthority("ROLE_ADMIN")
                        //idem avec hasAnyRole("EMPLOYE", "FORMATEUR", "ADMIN")) à la place de hasAnyAuthority("ROLE_EMPLOYE", "ROLE_FORMATEUR", "ROLE_ADMIN")
                );

        //Permet de configurer la page avec l'url "/login" pour qu'elle renvoie sur la page login fournie par défaut par Spring Security
        //et non notre page personnalisée
        //http.formLogin(Customizer.withDefaults());

        //ATTENTION: quand on customize une COnnexion personnalisée, la page de DECOnnexion par défaut devra également etre obligatoirement personalisée
        http.formLogin(form -> form.loginPage("/login")
                // permet le retour vers la page url  "" en cas de succès de connexion
                .defaultSuccessUrl("/user-connected"));

        HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.ALL));

        //Permet ici d'instaurer que l'appel de l'url "/logout" provoquera une redirection et un nettoyage total de tous les éléments de cache mémoire , cookies etc
        //déposes sur le client
        http.logout((logout) ->
                logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        // permet le retour vers la page url  "/login" en cas de succès de déconnexion
                        .logoutSuccessUrl("/login?logout")
                        .addLogoutHandler(clearSiteData)
        );

        return http.build();
    }
}

package org.rlnieto.pruebas;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.rlnieto.pruebas.utils.CacheLoader;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Creamos y cargamos la cache para hacer las pruebas
     * @param cacheLoader
     * @param context
     * @return
     */
    @Bean
    public ApplicationRunner configure(
            CacheLoader cacheLoader,
            ConfigurableApplicationContext context){
        return env ->
        {
            int loadedBooks = cacheLoader.load();
            System.out.println(String.format("%d libros cargados", loadedBooks));

            // Si queremos salir de la aplicación:
            //SpringApplication.exit(context);
        };
    }
}

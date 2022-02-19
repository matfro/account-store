package pl.matfro.test.support

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent
import org.springframework.test.context.support.TestPropertySourceUtils

class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  void initialize(ConfigurableApplicationContext applicationContext) {
    WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort())
    wireMockServer.start()

    applicationContext
            .getBeanFactory()
            .registerSingleton('wireMockServer', wireMockServer)

    applicationContext.addApplicationListener(applicationEvent -> {
      if (applicationEvent instanceof ContextClosedEvent) {
        wireMockServer.stop()
      }
    })

    TestPropertySourceUtils
            .addInlinedPropertiesToEnvironment(applicationContext,
                    "pl.matfro.account.store.exchange.rates.fetcher.base-url=http://localhost:${wireMockServer.port()}")
  }
}
